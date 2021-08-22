import java.io.Serializable;
import GraphPackage.*;
import HashedDictionaryPackage.*;
import java.util.Iterator;

public class DataBase implements Serializable {
    /* Use graph data structure to manage the database.
    Each vertex represents a profile, while
    each edge represents the friendship between the 2 profiles that it connect.
    Since an edge only represents friendship, it is undirectional and unweighted */
    private UndirectedGraph<Profile> graphProfiles;

    // Use hash dictionary along with the graph profiles to help searching faster

    /* Because an account name must be unique, we create a hashed dictionary
    that maps an account name (key) to a single profile (value) */
    private HashedDictionary<String, Profile> profiles;  // account name ---> profile

    /* But we look for someone on the network, we don't use their account name, but
    their real name. So we create another hash table to map new fullname(key) to 
    the account name(value). And from the account name, we can retreive the profile
    via the profiles dictionary. The reason why we don't directly a profile as value
    is that it will take unnecessary spaces*/
    private HashedDictionary<String, String> users;  // fullname ---> account name



    public DataBase() {
        profiles = new HashedDictionary<>();
        graphProfiles = new UndirectedGraph<>();
        users = new HashedDictionary<>();
    }



    /**
     * add a profile to the database
     * 
     * @param accountName the account name of the profile
     * @param name the fullname of the profile
     * @param profile the profile
     */
    public void add(String accountName, String name, Profile profile) {
        profiles.add(accountName, profile);
        users.add(name, accountName);
        graphProfiles.addVertex(profile);
        FileIO.save(this);
    }


    

    /**
     * remove a profile from the data base
     * 
     * @param accountName the account name of the profile to be removed
     */
    public void remove(String accountName) {
        Profile profileToRemove = profiles.remove(accountName);
        graphProfiles.removeVertex(profileToRemove);
        profiles.remove(accountName);
        users.remove(profileToRemove.getName());
        FileIO.save(this);
    }




    /**
     * create a friendship between 2 profiles.
     * 
     * @param left,right the profiles to create a friendship between
     */
    public void createFriendship(Profile left, Profile right) {
        // can only create friendship if they are not friends
        if (!areFriends(left, right) && !left.equals(right)) {
            // add an edge to indicate the connection between the 2
            graphProfiles.addEdge(left, right);
            Util.clearScreen();
            String message = left.getFirstName() + " is now friend with " + right.getFirstName();
            Util.println(message, Util.YELLOW_BOLD_BRIGHT);
        }
        FileIO.save(this);
    }




    /**
     * end the friendship between 2 profiles, if they are friends
     * 
     * @param left,right the profiles to end friendships
     */
    public void endFriendship(Profile left, Profile right) {
        // can only end friendship if they are already friends
        if (areFriends(left, right)) {
            // remove the friend connection between them in the interface
            graphProfiles.removeEdge(left, right);
            Util.clearScreen();
            String message = "\n" + left.getFirstName() + " and " + right.getFirstName() + " are no longer friends";
            Util.println(message, Util.RED_BOLD_BRIGHT);
        }
        FileIO.save(this);
    }




    /**
     * check if two people are friends with each other
     * 
     * @param left,right the profiles to check friendship
     * @return whether the profiles are friends with each other
     */
    public boolean areFriends(Profile left, Profile right) {
        return graphProfiles.hasEdge(left, right) || graphProfiles.hasEdge(right, left);
    }



    // Find a friend in the friend list of a profile
    public Profile findFriend(Profile thisProfile, String lookingFor) {
        Profile otherProfile = getProfile(lookingFor);
        if (otherProfile != null) {
            if (graphProfiles.hasEdge(thisProfile, otherProfile))
                return otherProfile;
        }
        Util.println("The friend list does not contain " + lookingFor, Util.RED_BRIGHT);
        return null;
    }


    

    /**
     * count the number of mutual friends between 2 profiles
     * 
     * @param left,right the profiles to find mutual friends
     * @return the number of mutual friends between the input profiles
     */
    public int findMutualFriends(Profile left, Profile right) {
        int count = 0;
        VertexInterface<Profile> vertex = graphProfiles.getVertex(left);

        if (vertex.hasNeighbor()) {
            Iterator<VertexInterface<Profile>> friends = vertex.getNeighborIterator();
            while (friends.hasNext()) {
                if (areFriends(friends.next().getLabel(), right))
                    count++;
            }
        }
        return count;
    }




    /**
     * get a list of mutual friends between 2 profiles
     * 
     * @param left,right the profiles to get the mutual friends
     * @return a queue containing all the mutual friends between 2 input profiles
     */
    public LinkedQueue<Profile> getMutualFriends(Profile left, Profile right) {
        LinkedQueue<Profile> queue = new LinkedQueue<>();
        VertexInterface<Profile> vertex = graphProfiles.getVertex(left);

        if (vertex.hasNeighbor()) {
            Iterator<VertexInterface<Profile>> friends = vertex.getNeighborIterator();
            while (friends.hasNext()) {
                Profile mutualFriend = friends.next().getLabel();
                if (areFriends(mutualFriend, right))
                    queue.enqueue(mutualFriend);
            }
        }
        return queue;
    }




    /**
     * get a list of "people you may know"
     * these are the friends of friends of the input profile
     * 
     * @param p a profile to get recommended friends for
     * @return a queue containing the recommended friends for the input profile
     */
    public LinkedQueue<Profile> getFriendsOfFriends(Profile p)
    {
        LinkedQueue<Profile> friendsOfFriends = new LinkedQueue<>();
        LinkedStack<Profile> path = new LinkedStack<>();
        QueueInterface<Profile> potential = graphProfiles.getBreadthFirstTraversal(p);
        VertexInterface<Profile> vertex = graphProfiles.getVertex(p);
        int size = 0;

        for (int i = 0; i <= vertex.getNumberOfNeighbors(); i++)
            // removes themselves and their friends, who are all at the beginning of the queue
            // because the list of recommended friends cannot include yourself and your friends.
            potential.dequeue();

        while(!potential.isEmpty())//if the path to the individual is
        {
            graphProfiles.getShortestPath(p, potential.getFront(), path);
            while(!path.isEmpty()) {
                path.pop();
                size++;
            }
            if(size == 3)
                // When we go away from the main profile, 
                // we will encounter the profile itself, the friend, and then friend of friend.
                // And the friend of the friend is someone we want to look for.
                // Therefore, the third vertex is what we are looking for
                friendsOfFriends.enqueue(potential.dequeue());
            else
                return friendsOfFriends;
            size = 0;
            path.clear();
        }
        return friendsOfFriends;
    }




    /**
     * get a list of all the friends of a profile
     * 
     * @param profile the profile to get the friends list
     * @return a queue containing all the friends in the friend list of the input profile
     */
    public LinkedQueue<Profile> getFriendListOf(Profile profile) {
        VertexInterface<Profile> vertex = graphProfiles.getVertex(profile);
        LinkedQueue<Profile> myQueue = new LinkedQueue<>();

        if (vertex.hasNeighbor()) {
            Iterator<VertexInterface<Profile>> friends = vertex.getNeighborIterator();
            myQueue = new LinkedQueue<>();
            while (friends.hasNext()) {
                myQueue.enqueue(friends.next().getLabel());
            }
        }
        return myQueue;
    }




    /**
     * find a profile with the peron's full name
     * 
     * @param name the fullname of the person we want to find
     * @return the profile found. Return null if no such profile found
     */
    public Profile getProfile(String name) {
        String accountName = users.getValue(name);
        if (accountName == null)
            return null;
        return profiles.getValue(accountName);
    }




    /**
     * find a profile using the person's account name
     * 
     * @param accountName the account name of the person we're finding
     * @return the profile found. Return null if no such profile found
     */
    public Profile logIn(String accountName) {
        return profiles.getValue(accountName);
    }




    /**
     * update the information of a profile
     * 
     * @param oldProfile the old profile we want to update
     * @param newProfile the new profile that we update to
     */
    public void updateProfile(Profile oldProfile, Profile newProfile) {
        graphProfiles.replaceVertex(oldProfile, newProfile);
        profiles.remove(oldProfile.getAccountName());
        profiles.add(newProfile.getAccountName(), newProfile);
        FileIO.save(this);
    }
}
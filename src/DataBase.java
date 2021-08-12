import GraphPackage.*;
import HashedDictionaryPackage.*;
import java.util.Iterator;

public class DataBase {
    private HashedDictionary<String, Profile> listOfProfiles;
    private UndirectedGraph<Profile> graphProfiles;


    public DataBase() {
        listOfProfiles = new HashedDictionary<>();
        graphProfiles = new UndirectedGraph<>();
    }


    // add a profile to the data base
    public void add(String accountName, Profile account) {
        listOfProfiles.add(accountName, account);
        graphProfiles.addVertex(account);
    }



    // remove a profile from the data base
    public void remove(String accountName) {
        Profile profileToRemove = listOfProfiles.remove(accountName);
        graphProfiles.removeVertex(profileToRemove);
        listOfProfiles.remove(accountName);
    }




    // create the friend ship between 2 people
    public void createFriendship(Profile left, Profile right) {
        // can only create friendship if they are not friends
        if (!areFriends(left, right) && !left.equals(right)) {
            // add an edge to indicate the connection between the 2
            graphProfiles.addEdge(left, right);
            System.out.println(left.getFirstName() + " is now friend with " + right.getFirstName());
        }
    }



    // end the friendship between 2 friends
    public void endFriendship(Profile left, Profile right) {
        // can only end friendship if they are already friends
        if (areFriends(left, right)) {

            // remove the friend connection between them in the interface
            graphProfiles.removeEdge(left, right);
            System.out.println("\n" + left.getFirstName() + " and " + right.getFirstName() + " are no longer friends");
        }
    }



    // check if the 2 profiles are friends or not
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
        System.out.println("The friend list does not contain " + lookingFor);
        return null;
    }




    // Find the number of mutual friends between 2 profiles
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



    // Return the queue of the mutual friends between the 2 profiles
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




    // Get the recommended friends ("People you may know" feature)
    public LinkedQueue<Profile> getFriendsOfFriends(Profile p)
    {
        LinkedQueue<Profile> friendsOfFriends = new LinkedQueue<>();
        LinkedStack<Profile> path = new LinkedStack<>();
        int size = 0;
        QueueInterface<Profile> potential = graphProfiles.getBreadthFirstTraversal(p);

        VertexInterface<Profile> vertex = graphProfiles.getVertex(p);

        for(int i = 0; i <= vertex.getNumberOfNeighbors(); i++)
            //removes themselves and their friends, who are all at the beginning of the queue
            potential.dequeue();

        while(!potential.isEmpty())//if the path to the individual is
        {
            graphProfiles.getShortestPath(p, potential.getFront(), path);
            while(!path.isEmpty()) {
                path.pop();
                size++;
            }
            if(size == 3)
                //The three nodes in path should be the starting person, the friend, and then the friend of the friend
                friendsOfFriends.enqueue(potential.dequeue());
            else
                return friendsOfFriends;
            size = 0;
            path.clear();
        }
        return friendsOfFriends;
    }





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




    public Profile getProfile(String accountName) {
        return listOfProfiles.getValue(accountName);
    }
}
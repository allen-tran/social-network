# Social-Network-on-Terminal


A mock Java Facebook CRUD Program (create, read, update, delete)


The program uses a graph data structure to simulate a social media on the terminal, where the users can
create accounts, log in, find other users, make friends, post something, etc.

Each vertex in the graph will represent a profile/user, and each edge that connects 2 vertices will
represent the friendship between the users.


The program also uses hashed dictionaries to help the graph data structure when it comes to searching.

The first dictionary will map an account name, which is unique, to a profile. This helps the users log in
and make sure that two people with the same name can still use the program without any problem.

But when people search for each other, they don't use account names. Instead, they use each other's fullnames.
Therefore, the second dictionary is responsible for mapping the user's fullname to the user's account name. And from
the account name, we can get the profile via the first dictionary. Mapping the fullname to the account name will save
much more storage than mapping the fullname directly to the profile, which takes up more space. And since the searching
time for hashed dictionary is only O(1), there's no decrease in performance at all.


## Demo Run:
Main screen<br>

![demo1](https://user-images.githubusercontent.com/83048295/131197358-afb27c25-3d38-4836-8f81-21cb64d0f2c6.png)

<br>Home screen (after loggin in)<br>
![demo2](https://user-images.githubusercontent.com/83048295/131197382-8ef77808-ac36-42b2-b22d-0d7d7c40644e.png)

<br>The News feed, where users post something<br>
![demo3](https://user-images.githubusercontent.com/83048295/131197502-34135d2e-b6f6-4569-b5e2-332e92fcc5b8.png)

<br>View friends in the friend list<br>
![demo4](https://user-images.githubusercontent.com/83048295/131197523-66ebe931-0603-42f1-8039-ec9a2ceaa1a4.png)

<br>View a specific profile of a user<br>
![demo5](https://user-images.githubusercontent.com/83048295/131197532-dbce6ab3-a0af-46c6-a75a-8e99c11a60c9.png)



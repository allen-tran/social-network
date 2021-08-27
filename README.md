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

![Main Screen](https://drive.google.com/file/d/17HomhMAYSdzASECQoIPudBReCP1W592H/view)


![Home Screen (after logging in)](https://drive.google.com/file/d/1Rq0tdcntuamk3d2_ulnQKNmZkOqx1yrg/view)


![View News Feeds](https://drive.google.com/file/d/1LLWj-0cpFXhE-8HYTvSSir-Y1-ogrPyn/view)


![See Friends in Friends List](https://drive.google.com/file/d/1947FbAVXoyM7NDimVLonrzhVyclhGdbD/view)


![View a Specific Profile](https://drive.google.com/file/d/14CI9CePlqNvvjZPsKqNuSDZ0gCPF7xRg/view)
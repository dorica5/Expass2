## Expass 3 Report

The exercise this week was regarding creating the front end of the voting app we started with the previous week  using our framework of choice and connecting that to the database. The whole goal was to make everything more user-friendly by adding a UI that we can effortlessly use without needing to use other tools.

##### Tech Stack
The framework i personally chose was the React framework, specifically React using TypeScript. I chose this since i have some experience with it as well as React Native, which is for apps, from before. The reason i went with TypeScript instead of JavaScript was because i have from previous experience found that it's beneficial to have type- safety. Although it could be frustrating when writing the code, the time it saves makes it worth it in the end. Like previous experience, it made me aware of many errors before running the app, saving me some time.

##### Front end Process
The front end side of things went relatively smoothly. I wanted to actually develop something i liked, so i started by creating a very basic framework in Figma.  Even while using Figma, i found myself having to add things i didn't remember to have in the beginning, like the fact that we actually need to Sign Up and Login to actually vote and make polls. Designing and coding these went quickly since the Login and Sign up page are almost identical. All in all, i enjoyed this part of the process.

##### Challenges Faced
One of the main challenges that i faced was implementing the login logic. I knew from before that for the Sign Up logic, we only need to fetch the `user` path using `POST`, however i did not have a login logic since that was not something we needed from last week. Seeing that i did not have a login logic, i naturally also did not have a log out logic, so if not addressed, we would not be able to have multiple users.

After taking some time to research this, i decided to go with a simple solution for now, by modifying the code from last week and adding a `verifyUser` method that takes in the username and email, then searches the HashMap in `PollManager` to check if that user exists. This method then returns the user, and i chose to save this information localStorage, so that if a user i logged in, we would remember that they are logged in and automatically they will be redirected to the Home page.

The reason this was needed was because i thought it made more sense to protect some routes like the `/` and `/create` route for people who are logged in, so those not logged in are automatically sent to the Sign Up page, and there they can either Sign Up, or click on Log In to be redirected to the Login page.

Another challenge that i faced was the polls not being loaded when a user has voted. With some help from the Internet, i found out that i needed to change some methods from last week, this includes the `getPolls` method from last week. Seeing as i did not want to erase it completely i commented it out instead, and replaced it with the new one. Some other changes i made include adding a new method in the `Polls` class that instead of giving me a HashMap of `VoteOptions`, i instead get a List. I also encountered a problem where there was a circular reference between the `VoteOption` class and the `Vote` class  from not annotating correctly last week.  This was fixed by annotating `@JsonManagedReference` in `VoteOption.java` and `@JsonBackReference` in `Vote.java`

The last challenged i encountered was the Application not running after combining the front-end and the backend. When i tried running the Application, i got an error saying that the class was not found. The solution to this was to edit the configuration, and just selecting `jdk 21` once again, even though it showed that it was `jdk 21` from before.

##### Unresolved Issues

One issue that still remains is regarding the backend. Since the backend is a file on our computer and not a server that is always on, it means that when we restart the backend Application, everything is deleted and we start with empty HashMaps. This conflicts with one of the features of the front end, namely storing the information of whether a user is logged in or not in localStorage. This makes it so that when we restart the app, we will still be on the home page since our localStorage has that information, but we won't see any polls since they are empty, and also we can't create polls since that user doesn't exist in the backend. So we manually have to Sign Out, then Sign Up as a new user once again. The only way to resolve this is to let the frontend know when the backend restarts, however i couldn't think of a simple way to do this.

##### Next Steps

Moving forward i hope to improve the authentication flow by replacing localStorage with a secure session management system when we implement a proper database. I will also explore ways to handle backend resets, something i know will be much easier to handle with a proper database.

##### GitHub Repository Links

Expass 1: [GitHub - Expass 1](https://github.com/dorica5/dat250_demo)
Expass 2: [GitHub - Expass 2](https://github.com/dorica5/Expass2)

I'm using the same folder i used last week for Expass2, so to view the work from last week without the changes i made, refer to the last commit for last week.

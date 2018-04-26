# UMD Alive v 1.0

A five-person project, the Bagel Bois branch and application of UMD Alive is an off-shoot of the main branch version that functions similarly to the original, but works logistically different. UMD Alive meant for the use on the University of Minnesota-Duluth campus, and is designed specifically in-mind to work on bringing club information to the forefront, and make it easy to join, create, and find clubs. It is a platform for all of the on-campus clubs and students so that everyone can find a fun group with similar interests to join.

The team for this project consisted of five computer science students from CS 4531: Devin Cheek, Dominic Cheek, Ross Schultz, Josh Senst, and Paul Sipper. Each member had their own responsibilities, but would help each other if needed. 

Devin Cheek's primary focus was on the main activity and transitioning everything into fragments.

Dominic Cheek was solely responsible for managing the server and all of its databases.

Ross Schultz worked on the user profile side of things.

Josh Senst added all functionality for the club and event pages, allowing the ability to join and create clubs/events.

Paul Sipper handled the page that displays all of the clubs as well the majority of the documentation.



# Files and Functionality

This project utilizes a main activity, fragments, singletons, and volley calls to a noSQL database to present club, event, and profile information in a logical manner. Google OAuth is used to handle the login process, which also adds a name and profile image for each user.

The app is separated as follows:

 - Main
	 - Main Activity
		 - This file is the framework for all of the fragments. It includes the basic navigation, handles the Google Sign-in, and loads all fragments.
	 - RestSingleton
		 - Synchronizes all requests across the app.
	 - UserSingleton
		 - Synchronizes the user, allowing for their data to be stored and accessed by other users of the app.
 - Base
	 - AllClubsFrag
		 - Generates a scrolling list that shows all of the club names stored in the database. Clicking on a name will bring the user to the club's page.
	 - ClubFrag
		 - Shows a view of relevant information about a club, such as its name, description, members, admin, and events. Clicking on a member or event will change the page to the relevant one. If a member is an admin, the ability to edit the club page appears, as well as the ability to add an event, and clicking the edit club brings the user to the edit club page, while clicking add event, brings the user to the edit event page.
	 - EventFrag
		 - A page that displays the title, date, and time of an event.
	 - ProfileFrag
		 - Displays after sign-in, it shows the users Google Account image and name, as well as an about me section, which includes their major and minor. There is a button that allows the ability to edit the about me section.
 - Edit
	 - EditClubFrag
		 - Page only accessible to admins, allows the editing of the club page.
	 - EditEventFrag
		 - Page only accessible to admins, allows editing of events.
	 - EditProfileFrag
		 - Page accessible to all users, allows editing of that users profile.
 - Create
	 - CreateClubFrag
		 - Pages accessible to all users, allows creation of a club. The user that creates the club will then become the club's admin.
	 - CreateEventFrag
		 - Page accessible to admins, allows the creation of an event for a club.
	 - CreateUserFrag
		 - First-time login only, allows creation of the user profile, with ability to edit an about me section.
 

## Server and Database

The application's information is stored in a MongoDB hosted on UMD's own ukko server. Inside of the database, there are three separate collections: Users, Clubs, and Events. All collections store relevant data as JSONObjects, which are accessed by the application with asynchronous volley calls. 

Inside the database, there numerous queries: createClub, editClub, getClub, getAllClubs, joinClub, leaveClub, deleteClub, createUser, editUser, getUser, deleteUser, createEvent, editEvent, and getEvent. All are necessary to perform actions within the app itself.

## Getting Started
UMD Alive should be able to run on most Android devices, as long as they are running Android Ice Cream Sandwich 4.0.3.

**Google OAuth Steps**

Since the project utilizes Google OAuth, you must follow these steps to get the project working:

1. Head to this link: https://developers.google.com/identity/sign-in/android/start-integrating
2. Follows steps in the configuration of a Google API Console Project

## Prerequisites and Installation

**Software Information:**

 - Android Studio Version last tested to work: 3.1
 - Minimum Android SDK Version: 15+ (minimum Ice Cream Sandwich 4.0.3)
 - Target SDK Version: 27
 - Compile SDK Version: 27
 - Utilizes Google OAuth API for login
 
 To run this application, you must connect to an instance of the node server with the MongoDB. The url must be changed in the application if you are not running with the original server.

## License
GNU General Public License v3.0

Google Auth https://developers.google.com/android/guides/client-auth




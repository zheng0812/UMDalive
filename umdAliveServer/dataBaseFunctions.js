//loads the MongoDB package
var mongojs = require("mongojs");

var url = 'mongodb://ukko.d.umn.edu:32893/umdAliveDatabase';

//array of collections we will use
var collections = ['clubs', 'users', 'events'];

//clubs: name, pictures, description, events, members
//users: name, picture, major

/*
funtions required :
	clubs:
    createClub
		getAllClubs
		getClub
		deleteClub
	users:
    createUser
		getUser
	events:
    createEvent
    getEvent
		allEvents (by date)
*/

var assert = require('assert');

var DBRef = mongojs(url, collections);

//the following are anonymous functions that wil be used in index.js

//Club Calls
module.exports.createClub = function(clubData) {
    DBRef.collection('clubs').save(clubData, function(err, result){
        if(err || !result){
					 console.log("Club failed to save in database.");
				} else {
					console.log("Club inserted into clubs collection on umdAliveDatabase.");
				}
    });
};

module.exports.getClub = function(clubName, callback) {
	DBRef.collection('clubs').find({"name": clubName}).toArray(function(err, docs) {
		if(err){
			console.log("Search failed.")
		} else {
			console.log("Found the following records");
			console.log(docs);
			callback(docs);
		}
	});
};

module.exports.getAllClubs = function(callback) {
	DBRef.collection('clubs').find({}).toArray(function(err, docs) {
		if(err){
			console.log("Search failed.")
		} else {
			console.log("Found the following records");
			console.log(docs);
			callback(docs);
		}
	});
};

//User Calls
module.exports.createUser = function(userData){
  DBRef.collection('users').save(userData, function(err, result){
    if(err || !result){
       console.log("User failed to save in database.");
    } else {
      console.log("Club inserted into clubs collection on umdAliveDatabase.");
    }
  });
};

module.exports.getUser = function(userEmail, callback) {
	DBRef.collection('user').find({"email": userEmail}).toArray(function(err, docs) {
		if(err){
			console.log("Search failed.")
		} else {
			console.log("Found the following records");
			console.log(docs);
			callback(docs[0]);
		}
	});
};

//Event Calls
module.exports.createEvent = function(eventData){
  DBRef.collection('events').save(eventData, function(err, result){
    if(err || !result){
      console.log("Event failed to save in database.");
    } else {
      console.log("createEvent event called. Following event was added to the events collection:");
      console.log(eventData);
    }
  });
};

module.exports.getEvent = function(club, callback){
  DBref.collection('events').find().toArray(function(err, docs){
    if(err){
      console.log("Search failed");
    } else {
      console.log("getEvent called. returning the folling record:");
      console.log(docs[0]);
      callback(docs[0]);
    }
  });
};

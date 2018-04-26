//start server npm run bootServer
//check processes ps aux \ grep userName
//to whoever gets this code next, good luck.
//cheek010@d.umn.edu

var express = require('express');
var queryParser = require('body-parser');

// Initialize main instanced class
var app = express();

// Set the port
app.set("port", 32892);

// Support encoded bodies
app.use(queryParser.urlencoded({
    extended: true
}));

// Support JSON-encoded bodies
app.use(queryParser.json());

//loads the data base functions
var dataBase = require('./dataBaseFunctions.js');

//Club server Calls
app.put('/createClub', function (req, res) {
  if (!req.body){
    return res.sendStatus(400);
  }
  var clubData = {
    "name" : req.body.name,
    "description" : req.body.description,
    "members" : req.body.members,
    "events" : req.body.events,
    "profilePic" : req.body.profilePic
  };
  dataBase.createClub(clubData, function (doc){
    res.send(doc);
  });
});

app.put('/editClub', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  var clubData = {
    "name" : req.body.name,
    "description" : req.body.description,
    "members" : req.body.members,
    "events" : req.body.events,
    "profilePic" : req.body.profilePic
  };
  dataBase.editClub(req.body._id, clubData);
  res.send({});
});

app.get('/getClub/:clubID', function (req, res) {
  dataBase.getClub(req.params.clubID, function (docs) {
    res.send(docs);
  });
});

app.get('/getAllClubs', function (req, res) {
  dataBase.getAllClubs(function (docs){
    res.send(docs);
  });
});

app.put('/joinClub', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  dataBase.joinClub(req.body.userID, req.body.clubID);
  res.send({});

});

app.put('/leaveClub', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  dataBase.leaveClub(req.body.userID, req.body.clubID);
  res.send({});
});

app.delete('/deleteClub/:clubID', function (req, res){
  dataBase.deleteClub(req.params.clubID);
  res.send({});
});

//User server Calls
app.put('/createUser', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  console.log(req.body);
  var userData = {
    "name" : req.body.name,
    "email" : req.body.email,
    "major" : req.body.major,
    "description" : req.body.description,
    "clubs" : req.body.clubs,
    "userID" : req.body.userID,
    "profilePic" : req.body.profilePic
  };
  dataBase.createUser(userData);
  res.send({});
});

app.put('/editUser', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  var userData = {
    "name" : req.body.name,
    "email" : req.body.email,
    "major" : req.body.major,
    "description" : req.body.description,
    "clubs" : req.body.clubs,
    "userID" : req.body.userID,
    "profilePic" : req.body.profilePic
  };
  dataBase.editUser(req.body.userID, userData);
  res.send({});
});

app.get('/getUser/:userID', function (req, res){
    dataBase.getUser(req.params.userID, function (docs){
      res.send(docs);
    });
});

//Event server calls
app.put('/createEvent', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }
  var eventData = {
    "name" : req.body.name,
    "description" : req.body.description,
    "date" : req.body.date,
    "time" : req.body.time,
    "club" : req.body.club
  };
  dataBase.createEvent(eventData);
  res.send({});
});

app.put('/editEvent', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }

  var eventData = {
    "name" : req.body.name,
    "description" : req.body.description,
    "date" : req.body.date,
    "time" : req.body.time,
    "club" : req.body.club
  };

  dataBase.editEvent(req.body._id, eventData);
  res.send({});
});

app.get('/getEvent/:eventID', function (req, res) {
  dataBase.getEvent(req.params.eventID, function (docs) {
    res.send(docs);
  });
});

app.get('/getAllEvents', function (req, res){
  dataBase.getAllEvents(function (docs){
    res.send(docs);
  });
});

app.delete('/deleteEvent/:eventID', function (req, res){
  dataBase.deleteClub(req.params.eventID);
  res.send({});
});

//Listen
app.listen(app.get("port"), function () {
    console.log('umdAliveServer running on port ', app.get("port"));
});

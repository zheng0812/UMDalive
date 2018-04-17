//start server npm run script
//check processes ps aux \ grep userName

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
    "members" : req.body.memebers,
    "events" : req.body.events
  };

  dataBase.createClub(clubData);
});

app.get('/getClub/:clubID', function (req, res) {
  dataBase.getClub(req.params.clubID, function (docs) {
    res.send(docs);
  });
});

app.get('/getAllClubs', function (req, res) {
  if (!req.body){
    return res.sendStatus(400);
  }
  dataBase.getAllClubs(function (docs){
    res.send(docs);
  });
});

//User server Calls
app.put('/createUser', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }

  var userData = {
    "name" : req.body.name,
    "email" : req.body.email,
    "major" : req.body.major,
    "description" : req.body.description,
    "clubs" : req.body.clubs
  };

  dataBase.createUser(userData);
  console.log(req.body.name + " has been created");
});

app.get('/getUser/:userID', function (req, res){
    dataBase.getUser(req.params.userID, function (docs){
      res.send(docs);
    });
});

app.put('/createEvent', function (req, res){
  if (!req.body){
    return res.sendStatus(400);
  }

  var eventData = {
    "name" : req.body.name,
    "description" : req.body.description,
    "time" : req.body.time,
    "club" : req.body.club,
    "eventID" : req.body.eventID
  };

  dataBase.createEvent(eventData);
  console.log(req.body.name + " has been created");
});

app.get('/getEvent/:eventID', function (req, res){
  dataBase.getEvent(req.params.eventID, function(docs){
    res.send(docs);
  });
});

app.get('/getAllEvents', function (req, res){
  dataBase.getAllClubs(function (docs){
    res.send(docs);
  });
});

app.listen(app.get("port"), function () {
    console.log('umdAliveServer running on port ', app.get("port"));

});

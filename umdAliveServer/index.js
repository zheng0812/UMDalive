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
  console.log(req.body.name + " has been created.");
});

app.post('/getClub', function (req, res) {
  if (!req.body){
    return res.sendStatus(400);
  }
  dataBase.getClub(req.body.clubName, function (docs){
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
    "about" : req.body.about,
    "clubs" : req.body.clubs
  };

  dataBase.createUser(userData);
  console.log(req.body.name + " has been created");
});

app.get('/getUser', function (req, res){
    if (!req.body){
      return res.sendStatus(400);
    }
    dataBase.getUser(req.body.userEmail, function (docs){
      res.send(docs);
    });
});

app.listen(app.get("port"), function () {
    console.log('umdAliveServer running on port ', app.get("port"));

});

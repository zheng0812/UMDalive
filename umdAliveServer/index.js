var express = require('express');
var queryParser = require('body-parser');

// Initialize main instanced class
var app = express();

// Set the port
app.set("port", 65000);//REAL ONE
//app.set("port",60000);

// Support encoded bodies
app.use(queryParser.urlencoded({
    extended: true
}));

// Support JSON-encoded bodies
app.use(queryParser.json());
//loads the mongo functions in this file
var mongodb = require('./mongoDBFunctions.js');
console.log(mongodb);

var dummyUser1 = {
    name: "Meggie Jo",
    email: "umdAlive1@gmail.com",
    graduationDate: "2019",
    major: "computer science",
    userType: "club member",
    clubs: []

};

/*///////////////////////////
 *   End of dummy users/clubs
 *////////////////////////////

/*
 ***********************
 * DELETE ROUTE SECTION
 ***********************
 */

app.delete("/delete", function(req, res){
    if (!req.body)
        return res.sendStatus(400);

    console.log("GIMME DATA" + req.body.clubName);	
    mongodb.delete(req.body.clubName);

    
    console.log("Club has been deleted: " + req.body.clubName);

    res.sendStatus(200);
}
);

app.delete("/deleteUser", function(req, res){
	if (!req.body)
		return res.sendStatus(400);

	mongodb.deleteUser(req.body.userName);

	res.sendStatus(200);

	console.log("User has been deleted: " + req.body.userName);
}
);

app.delete("/deletePost", function(req, res){
	if (!req.body)
		return res.sendStatus(400);

	mongodb.deletePost(req.body.title);
	
	res.sendStatus(200);

	console.log("Post has been deleted: " + req.body.title);
	
}
);

/*
 ************************
 * PUT ROUTE SECTION
 ************************
 */
app.put('/clubs', function (req, res) {

    // If for some reason the JSON isn't parsed, return HTTP error 400
    if (!req.body)
        return res.sendStatus(400);
	
    //console.log(req.body);	
    // Takes data from request and makes a new object
    var clubData = {
        clubName: req.body.clubName,
        clubOwner: req.body.clubOwner,
        ownerEmail: req.body.email,
        keywords: req.body.keywords,
        description: req.body.description
    };

	//console.log(clubData);

    mongodb.insertClub(clubData);

    var jsonResponse = {
        id: '123', status: 'updated'
    };
    res.json(jsonResponse);

    console.log("New club has been created: " + req.body.clubName);
});

app.put('/posts', function (req, res) {
    // If for some reason the JSON isn't parsed, return HTTP error 400
    if (!req.body) return res.sendStatus(400);

    var postData = {
        clubName: req.body.clubName,
        title: req.body.title,
        time: req.body.time,
        date: req.body.date,
        location: req.body.location,
        description: req.body.description,
        image: req.body.image
    };

    mongodb.insertPost(postData);

    console.log("Club posting: " + req.body.clubName);
    console.log("Title of post: " + req.body.title);
    var jsonResponse = {
        id: '123', status: 'updated'
    };
    res.json(jsonResponse);
});

app.put('/userData', function (req, res) {
    // If for some reason the JSON isn't parsed, return HTTP error 400
    if (!req.body) return res.sendStatus(400);

    var userData = {
        name: req.body.name,
        major: req.body.major,
        email: req.body.email,
        graduation_date: req.body.graduationDate,
        userType: req.body.userType,
        users_clubs: [],
    };

    mongodb.insertUser(userData);
    console.log("Creating user" + req.body.name);

    var jsonResponse = {
        id: '123', status: 'updated'
    };
    res.json(jsonResponse);
    //res.json(userData);
});

/*
 ************************
 * GET ROUTE SECTION
 ************************
 */

app.get('/clubs', function (req, res) {
    //array to which each club will be stored
    var clubNames = {
        items: []
    };
    mongodb.getCollection('clubs', function(result){
            var clubsData = {
                jsonArray: []
            };

            result.forEach(function(clubs){
                clubsData.jsonArray.push(clubs);
            });

            for(var i = 0; i < clubsData.jsonArray.length; i++){
                var curClub = clubsData.jsonArray[i];
                clubNames.items[i] = curClub.clubData.clubName;
            }

            var stringArray = JSON.stringify(clubNames);
            console.log("clubs being sent to client: " + stringArray);
            res.send(stringArray);
    });

});

app.get('/clubs/:clubName', function (req,res) {
    var club;
    console.log("Looking for " + req.params.clubName);

    mongodb.findClub(req.params.clubName, function(result){
        var club = result[0];
        console.log("Found club.");
        res.query = JSON.stringify(club.clubData);
        res.send(res.query);
    });
});

app.get('/clubSearch/:keyword', function (req,res) {
    //array to which each club will be stored
    var clubNames = {
        items: []
    };

    mongodb.getCollection('clubs', function(result){
            var clubsData = {
                jsonArray: []
            };

            result.forEach(function(clubs){
                clubsData.jsonArray.push(clubs);
            });

            var x = 0;
            for(var i = 0; i < clubsData.jsonArray.length; i++){
                var curClub = clubsData.jsonArray[i];
                if(req.params.keyword === curClub.clubData.keywords) {
                    clubNames.items[x] = curClub.clubData.clubName;
                    x++;
                    }
            }

            var stringArray = JSON.stringify(clubNames);
            console.log("clubs being sent to client: " + stringArray);
            res.send(stringArray);
    });
});
//should return userEmail
//Only returns dummy
app.get('/users/:email', function (req, res) {
var user;
    console.log("Looking for " + req.params.email);

    mongodb.findUser(req.params.email, function(result){
        var user = result[0];
        console.log(user);
        console.log("Found user.");
        //console.log(res.body);
        res.query = JSON.stringify(user.userData);//was user.userData
        console.log("-------------------")
        res.send(res.query);
        
    });
//    res.send(JSON.stringify(dummyUser1));
});

//returns an array of all users
//Only returns dummy
app.get('/userData/', function (req, res) {
 //array to which each club will be stored
    var userEmails = {
        userItems: []
    };
    mongodb.getCollection('users', function(result){
            var usersData = {
                jsonArray: []
            };

            result.forEach(function(users){
                usersData.jsonArray.push(users);
            });


            for(var i = 0; i < usersData.jsonArray.length; i++){
                var curUser = usersData.jsonArray[i];

                userEmails.userItems[i] = curUser.userData.email;
            }

            var stringArray = JSON.stringify(userEmails);

            console.log("users being sent to client: " + stringArray);
            res.send(stringArray);

    });


});

app.get('/posts', function (req, res) {
    var mostRecentPosts = {
        items: []
    };

    mongodb.getCollection('posts', function(result){
                var postsData = {
                    jsonArray: []
                };

                result.forEach(function(posts){
                    postsData.jsonArray.push(posts);
                });

                for(var i = 0; i < postsData.jsonArray.length; i++){
                    var curPost = postsData.jsonArray[i];
                    mostRecentPosts.items[i] = curPost.postData;
                }

                var stringArray = JSON.stringify(mostRecentPosts);
                res.send(stringArray);
    });
});

function getClubKeyword(position) {
    return clubs.items[position].keywords;
}

app.listen(app.get("port"), function () {
    console.log('CS4531 UMDAlive app listening on port: ', app.get("port"));

});



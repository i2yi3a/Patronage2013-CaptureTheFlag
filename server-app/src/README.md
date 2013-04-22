Capture The Flag: Server Application
====================================

'Capture the Flag' is an open-source urban game project.

Requirements:
-------------

* Git 1.7.x (recommended)
* Java 1.6 (and greater)
* Maven 3.x (and greater)
* MongoDB 2.2.2 (recommended)

How to start your work?
-----------------------
> Checkout project from GitHub:

    git clone git://github.com/lahim/ctf-portal.git ctf.git

> Build project:

    cd ctf.git
    mvn clean install
    
> Run application:

If you want to run only a pure server module:

    cd ctf-server

otherwise, if you want to run server module with simple web API:

    cd ctf-web

run:

    mvn jetty:run
    
> Browser:

    http://localhost:8080/api/simple?all


Installation (how-to):
---------------

> How to install MongoDB?

Installation guide:
    http://docs.mongodb.org/manual/installation/

Authorization & Authentication (curl examples):
-----------------------------------------------

> How to get token?

    curl -H "Content-type: application/x-www-form-urlencoded" -X POST -d "client_id=mobile_android&client_secret=secret&grant_type=password&username=test&password=password123456" http://localhost:8080/oauth/token

> How to get resource using token?

    curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer 896c75b1-8f83-456b-8303-3e0d9f3c9e2a" -X GET http://localhost:8080/api/secured/simple/isAlive


> Set-up - add roles into the database:

    db.portalRole.insert({ "_id" : "PORTAL_USER", "_class" : "com.blstream.patronage.ctf.model.PortalRole" })
    db.portalRole.insert({ "_id" : "PORTAL_ADMIN", "_class" : "com.blstream.patronage.ctf.model.PortalRole" })
    db.portalRole.insert({ "_id" : "PORTAL_PLAYER", "_class" : "com.blstream.patronage.ctf.model.PortalRole" })

> How to create a new player?

    curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"username":"jacek", "password":"AlaMaKota"}' http://localhost:8080/api/players/add

> How to log in?

    curl -H "Content-type: application/x-www-form-urlencoded" -X POST -d "client_id=mobile_android&client_secret=secret&grant_type=password&username=jacek&password=AlaMaKota" http://localhost:8080/oauth/token

> How to create a new game?

    curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer e08c2abc-2bd2-43c7-9874-823e94b98fdb" -X POST -d '{ "name": "First fancy example CTF game", "description": "First open for all simple urban game in Szczecin", "time_start": "01-06-2013 10:13:00", "duration": 5400000, "points_max": 10, "players_max": 12, "localization": { "name": "Jasne Blonia, Szczecin, Poland", "latLng": { "lat": 53.44018, "lng": 14.540062 }, "radius": 2800 } }' http://localhost:8080/api/secured/games

> How to get all games?

    curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer e08c2abc-2bd2-43c7-9874-823e94b98fdb" -X GET http://localhost:8080/api/secured/games

> How to delete deleted game?

    curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer e08c2abc-2bd2-43c7-9874-823e94b98fdb" -X GET http://localhost:8080/api/secured/games/516eec220364e55bfa660566
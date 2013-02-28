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
    mvn clean package
    
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

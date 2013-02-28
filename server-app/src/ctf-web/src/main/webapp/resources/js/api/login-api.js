/**
 * Copyright 2013 BLStream
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * User: lahim
 * Date: 1/31/13
 */


/*
REQUEST:

How to get token?

    > curl -H "Content-type: application/x-www-form-urlencoded" -X POST -d "client_id=mobile_test&client_secret=secret&grant_type=password&username=michal.krawczak@blstream.com&password=FKA13#aqFR3" http://localhost:8080/oauth/token

    HEADERS: {
        Content-type: application/x-www-form-urlencoded
    }

    POST DATA: {
        client_id: "mobile_test",
        client_secret: "secret",
        grant_type: "password",
        username: "michal.krawczak@blstream.com",
        password: "FKA13#aqFR3"
    }
*/

/*
RESPONSE:

    authData = {
        "access_token": "ed756770-e752-4f54-a278-1eee91a47b4a",
        "token_type": "bearer",
        "expires_in": 4999,
        "scope": "read write"
    };
*/

var login_url = server_host + "/oauth/token";
var client_id = "web_www";
var client_secret = "secret";
var grant_type = "password";
var username = "michal.krawczak@blstream.com";
var password = "FKA13#aqFR3";

function loginExample(username, password, callback) {
    login(client_id, client_secret, grant_type, username, password, callback);
}

function login(client_id, client_secret, grant_type, username, password, callback) {
    $.ajax({
        type: "POST",
        beforeSend: function(request) {
            request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        },
        url: login_url,
        data: {
            client_id: client_id,
            client_secret: client_secret,
            grant_type: grant_type,
            username: username,
            password: password
        },
        success: function (authData) {
            callback(authData);
        },
        error: function (errorData) {
            var response = errorData.responseText;
            var error = $.parseJSON(response);
            var reason = error.error_description;
            alert("Error: " + reason);
        }
    });
}

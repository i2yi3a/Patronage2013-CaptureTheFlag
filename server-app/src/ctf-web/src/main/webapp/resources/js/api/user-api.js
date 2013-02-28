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

        How to load user list?

            > curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer ed756770-e752-4f54-a278-1eee91a47b4a" -X GET http://localhost:8080/api/secured/users

            HEADERS: {
                Accept: application/json,
                Content-type: application/json,
                Authorization: Bearer ed756770-e752-4f54-a278-1eee91a47b4a
            }
*/

/*
    RESPONSE:

        userListData = [
            {
                "id": "510ab51b0364e3c690f04a76",
                "username": "michal.krawczak@gmail.com",
                "password": "FKA13#aqFR3",
                "firstName": null,
                "lastName": null,
                "accountNonExpired": true,
                "accountNonLocked": true,
                "credentialsNonExpired": true,
                "enabled":true,
                "roles": [
                    {
                        "authority": "PORTAL_USER"
                    }
                ]
            },
            {
                "id": "510ab5360364e3c690f04a77",
                "username": "michal.krawczak@blstream.com",
                "password": "FKA13#aqFR3",
                "firstName": null,
                "lastName": null,
                "accountNonExpired": true,
                "accountNonLocked": true,
                "credentialsNonExpired": true,
                "enabled": true,
                "roles": [
                    {
                        "authority": "PORTAL_USER"
                    },
                    {
                        "authority": "PORTAL_ADMIN"
                    }
                ]
            }
        ];

        user = {
            "id": "510ab51b0364e3c690f04a76",
            "username": "michal.krawczak@gmail.com",
            "password": "FKA13#aqFR3",
            "firstName": null,
            "lastName": null,
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled":true,
            "roles": [
                {
                    "authority": "PORTAL_USER"
                }
            ]
        };

        role = {
            "authority": "PORTAL_USER"
        };
*/

var user_url = server_host + "/api/secured/users";

function getAllUsersExample(access_token, callback) {
    getAllUsers(access_token, callback);
}

function getAllUsers(access_token, callback) {
    if (access_token) {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Accept", "application/json");
                request.setRequestHeader("Content-type", "application/json");
                request.setRequestHeader("Authorization", "Bearer " + access_token);
            },
            url: user_url + "?all",
            success: function (userListData) {
                callback(userListData);
            },
            error: function (errorData) {
                var response = errorData.responseText;
                var error = $.parseJSON(response);
                var reason = error.error_description;
                alert("Error: " + reason);
            }
        });
    } else {
        alert("Access token is not defined!");
    }
}

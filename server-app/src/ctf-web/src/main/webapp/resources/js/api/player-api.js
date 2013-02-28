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

How to load player list?

    > curl -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer f1dc7fdb-a0ef-4208-9ed6-3be5f93870d4" -X GET http://localhost:8080/api/secured/players

    HEADERS: {
        Accept: application/json,
        Content-type: application/json,
        Authorization: Bearer ed756770-e752-4f54-a278-1eee91a47b4a
    }

curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"username":"michal.krawczak@gmail.com","password":"FKA13#aqFR3", "firstName":null,"lastName":null,"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true, "roles": ["PORTAL_USER"]}' http://localhost:8080/api/trusted/createUser

How to add new player?

    > curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"username":"krol.julian@blstream.com","password":"FKA13#aqFR3"}' http://localhost:8080/api/players/add

    HEADERS: {
        Accept: application/json,
        Content-type: application/json
    }

    POST DATA: {
        "type": "PRIVATE",
        "portalUser": {
            "username": "krol.julian@blstream.com",
            "password": "FKA13#aqFR3",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true
        }
    }
 */

/*
    RESPONSE:

        playerListData = [
            {
                "id": "510b8049da06ba6f9a3da1c4",
                "type": "PRIVATE",
                "portalUser":
                    {
                        "id": "510a6cc2da063f708d7af354",
                        "username": "michal.krawczak@blstream.com",
                        "password": "FKA13#aqFR3",
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
            },
            {
                "id": "510b8049da06ba6f9a3da1c4",
                "type": "PRIVATE",
                "portalUser":
                    {
                        "id": "510a6cc2da063f708d7af355",
                        "username": "michal.krawczak@gmail.com",
                        "password": "FKA13#aqFR3",
                        "accountNonExpired": true,
                        "accountNonLocked": true,
                        "credentialsNonExpired": true,
                        "enabled": true,
                        "roles": [
                            {
                                "authority": "PORTAL_USER"
                            },
                            {
                                "authority": "PORTAL_PLAYER"
                            }
                        ]
                    }
            }
        ];
*/

var player_url = server_host + "/api/secured/players";

function getAllPlayersExample(access_token, callback) {
    getAllPlayers(access_token, callback);
}

function getAllPlayers(access_token, callback) {
    if (access_token) {
        $.ajax({
            type: "GET",
            beforeSend: function(request) {
                request.setRequestHeader("Accept", "application/json");
                request.setRequestHeader("Content-type", "application/json");
                request.setRequestHeader("Authorization", "Bearer " + access_token);
            },
            url: player_url,
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

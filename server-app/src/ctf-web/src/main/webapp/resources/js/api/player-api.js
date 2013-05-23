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

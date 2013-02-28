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

//var server_host = "http://capturetheflag.blstream.com:18080/demo";
//var server_host = "https://capturetheflag.blstream.com:8080/demo";
var server_host = "http://localhost:8080/";
var access_token = null;

$(document).ready(function () {
    $("#btn_login").click(function () {
        var username = $("#j_username").val();
        var password = $("#j_password").val();

        loginExample(username, password, loginCallback);
    });

    $("#btn_getAllUsers").click(function () {
        getAllUsersExample(access_token, userListCallback);
    });

    $("#btn_getAllPlayers").click(function () {
        getAllPlayersExample(access_token, playerListCallback);
    });
});

function loginCallback(authData) {
    var token = authData.access_token;
    var expires_in = authData.expires_in;

    access_token = authData.access_token;

    alert('Your access token is:\n' + token + '\n\nExpires in: ' + expires_in + ' sek.');

    $("#txt_token").html(access_token);
}

function userListCallback(listData) {
    var html = "";
    for (var i=0; i<listData.length; i++) {
        var user = listData[i];

        html += "<li>" + "username: " + user.username + "</li>";
    }
    $("#txt_allUserList").html(html);
}

function playerListCallback(listData) {
    var html = "";
    for (var i=0; i<listData.length; i++) {
        var player = listData[i];
        var user = player.portalUser;

        html += "<li>" + "id: " + player.id + " | " + player.type + " | " + user.username + "</li>";
    }
    $("#txt_allPlayerList").html(html);
}

package com.blstream.patronage.ctf.common.errors;

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
 * User: mkr
 * Date: 2/19/13
 *
 * This class is a representation of enum type for error codes.
 * All error codes are stored in error-codes.properties file.
 */
public enum ErrorCodeType {
    SUCCESS(0, "OK"),
    BAD_REQUEST(1, "Bad request"),
    INTERNAL_ERROR(2, "Internal error"),

    RESOURCE_NOT_FOUND(3, "Resource not found"),
    RESOURCE_ALREADY_EXISTS(4, "Resource already exists"),
    RESOURCE_CANNOT_BE_DELETED(5, "Resource cannot be deleted"),

    CANNOT_CREATE_NEW_PLAYER(100, "Cannot create a new player"),
    PLAYER_ALREADY_EXISTS(101, "Player already exists"),
    PLAYER_CANNOT_BE_SIGN_IN(102, "Player cannot be sign in"),
    PLAYER_CANNOT_BE_SIGN_OUT(103, "Player cannot be sign out"),

    PLAYER_IS_ALREADY_SIGNED_IN(104, "Player is already signed in"),
    GAME_REACHED_MAXIMUM_OF_PLAYERS(105, "Game reached maximum of players"),
    ;

    private Integer code;
    private String message;

    private ErrorCodeType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

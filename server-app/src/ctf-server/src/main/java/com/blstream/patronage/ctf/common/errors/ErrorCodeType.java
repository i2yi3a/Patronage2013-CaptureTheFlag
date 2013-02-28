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
    SUCCESS(0),

    CANNOT_CREATE_NEW_PLAYER(1),
    PLAYER_ALREADY_EXISTS(2),

    BAD_REQUEST(3),
    FAILED(4)
    ;

    private Integer code;

    private ErrorCodeType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

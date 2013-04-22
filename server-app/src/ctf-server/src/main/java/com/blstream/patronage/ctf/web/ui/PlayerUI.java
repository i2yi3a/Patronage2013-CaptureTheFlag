package com.blstream.patronage.ctf.web.ui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

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
 * This class is a representation of player UI object.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerUI extends BaseUI<String> {

    private static final long serialVersionUID = -5141746381035307435L;

    private String username;
    private String password;


    public PlayerUI() {
    }

    public PlayerUI(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public String getId() {
        return username;
    }

    /**
     * Returns username.
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

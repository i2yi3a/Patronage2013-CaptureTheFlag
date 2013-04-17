package com.blstream.patronage.ctf.model;

import com.blstream.hooks.springframework.mongodb.mapping.DBRefCollectionCascade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
 * Date: 1/22/13
 *
 * This class is a representation of portal user object. This is a basic user model
 * with username, password and other security properties.
 *
 * TODO: make password encrypted using e.g. SHA-1!
 */
@Document
public class PortalUser implements BaseModel<String> {

    private static final long serialVersionUID = 5227112390633063751L;

    private @Id @Indexed String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @DBRef
    @DBRefCollectionCascade
    private List<PortalRole> roles;


    public PortalUser() {
    }

    public PortalUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    @Override
    public String getId() {
        return username;
    }

    /**
     * Returns a username.
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns a password.
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PortalRole> getRoles() {
        if (roles == null) {
            roles = new ArrayList<PortalRole>();
        }
        return roles;
    }

    public void setRoles(List<PortalRole> roles) {
        this.roles = roles;
    }
}

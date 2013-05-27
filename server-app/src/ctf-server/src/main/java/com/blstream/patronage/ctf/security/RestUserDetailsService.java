package com.blstream.patronage.ctf.security;

import com.blstream.patronage.ctf.model.PortalUser;
import com.blstream.patronage.ctf.service.PortalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;

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
 * Date: 1/29/13
 *
 * This class is a representation of REST authentication user details service which is using by Spring Security for users
 * authentication process.
 *
 * @see <a href="http://static.springsource.org/spring-security/site/docs/3.2.x/reference/springsecurity-single.html">Spring-Security 3.2 reference</a>
 */
@Named("restUserDetailsService")
public class RestUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(RestUserDetailsService.class);

    private PortalUserService portalUserService;


    @Inject
    @Named("portalUserService")
    public void setPortalUserService(PortalUserService portalUserService) {
        this.portalUserService = portalUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PortalUser portalUser;
        User user;

        if (logger.isDebugEnabled()) {
            logger.debug("---- loadUserByUsername");
        }

        if (logger.isInfoEnabled()) {
            logger.info(String.format("Finding user by username: %s", username));
        }

        portalUser = portalUserService.findByUsername(username);

        if (portalUser == null) {
            throw new UsernameNotFoundException(String.format("No such user: %s", username));
        }

        if (logger.isInfoEnabled()) {
            logger.info(
                    String.format(
                            "User: [username: %s] was found in database.",
                            portalUser.getUsername()
                    )
            );
        }

        user = prepareUser(portalUser);

        return user;
    }

    private User prepareUser(final PortalUser portalUser) {
        User user;

        if (logger.isDebugEnabled()) {
            logger.debug("---- prepareUser");
        }

        user = new User(
                portalUser.getUsername(),
                portalUser.getPassword(),
                portalUser.isEnabled(),
                portalUser.isAccountNonExpired(),
                portalUser.isCredentialsNonExpired(),
                portalUser.isAccountNonLocked(),
                portalUser.getRoles()
        );

        if (logger.isInfoEnabled()) {
            logger.info(
                    String.format(
                            "Security user was prepared: " +
                                    "[username: %s, password: *************, isEnabled: %s, isAccountNonExpired: %s, " +
                                    "isCredentialsNonExpired: %s, isAccountNonLocked: %s, roles: %s]",
                            user.getUsername(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonExpired(),
                            user.isAccountNonLocked(), user.getAuthorities()
                    )
            );
        }

        return user;
    }

}

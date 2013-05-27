package com.blstream.patronage.ctf.security;

import com.blstream.patronage.ctf.model.PortalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.User;

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
 * Date: 1/30/13
 *
 * This class is a representation of password encoder service. This service is able to encode password using SHA
 * password encoder.
 */
@Named("passwordEncoderService")
public class PasswordEncoderService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderService.class);

    private ShaPasswordEncoder shaPasswordEncoder;
    private ReflectionSaltSource saltSource;

    @Inject
    @Named("passwordEncoder")
    public void setShaPasswordEncoder(ShaPasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }

    @Inject
    @Named("saltSource")
    public void setSaltSource(ReflectionSaltSource saltSource) {
        this.saltSource = saltSource;
    }

    /**
     * Encodes user's password.
     * @param portalUser
     * @return String
     */
    public String encodePassword(final PortalUser portalUser) {
        User user = prepareUser(portalUser);
        Object salt = saltSource.getSalt(user);

        if (logger.isDebugEnabled()) {
            logger.debug(
                    String.format(
                            "User %s salt is: %s",
                            user.getUsername(), salt
                    )
            );
        }

        String encodedPassword = shaPasswordEncoder.encodePassword(portalUser.getPassword(), salt);

        if (logger.isDebugEnabled()) {
            logger.debug(
                    String.format(
                            "Encoded password for user %s is: %s",
                            portalUser.getUsername(), encodedPassword
                    )
            );
        }

        return encodedPassword;
    }

    /**
     * Prepares security user object based on portal user instance.
     * @param portalUser
     * @return User
     */
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

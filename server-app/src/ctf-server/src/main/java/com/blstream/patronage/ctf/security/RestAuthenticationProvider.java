package com.blstream.patronage.ctf.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
 * This class is a representation of REST authentication provider which is using by Spring Security for users
 * authentication process.
 *
 * @see <a href="http://static.springsource.org/spring-security/site/docs/3.2.x/reference/springsecurity-single.html">Spring-Security 3.2 reference</a>
 */
@Named("restAuthenticationProvider")
public class RestAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private RestUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    private SaltSource saltSource;


    @Inject
    @Named("restUserDetailsService")
    public void setUserDetailsService(RestUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Inject
    @Named("passwordEncoder")
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Inject
    @Named("saltSource")
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = saltSource.getSalt(userDetails);

        if (authentication.getCredentials() == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Authentication failed: no credentials provided");
            }
            throw new BadCredentialsException("Authentication failed: no credentials provided");
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (logger.isDebugEnabled()) {
            logger.debug(
                    String.format(
                            "User %s credentials provided: %s, userDetails credentials: %s, salt: %s",
                            userDetails.getUsername(), presentedPassword, userDetails.getPassword(), salt
                    )
            );
        }

        // TODO: make user's password encrypted!
        // if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
        if (!userDetails.getPassword().equals(presentedPassword)) {
            if (logger.isWarnEnabled()) {
                logger.warn("Authentication failed: password does not match stored value");
            }
            throw new BadCredentialsException("Authentication failed: password does not match stored value");
        }

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("User: %s authenticated successfully.", userDetails.getUsername()));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException notFoundException) {
            if (logger.isWarnEnabled()) {
                logger.warn(String.format("User: %s was not found.", username));
            }
            throw notFoundException;
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }

        if (userDetails == null) {
            throw new AuthenticationServiceException(
                    String.format(
                            "UserDetails for username: %s is null, which s an interface contract violation.",
                            username
                    )
            );
        }

        return userDetails;
    }
}

package com.blstream.patronage.ctf.service.impl;

import com.blstream.patronage.ctf.common.exception.AlreadyExistsException;
import com.blstream.patronage.ctf.common.service.CrudServiceImpl;
import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.model.PlayerType;
import com.blstream.patronage.ctf.model.PortalRole;
import com.blstream.patronage.ctf.model.PortalUser;
import com.blstream.patronage.ctf.repository.PlayerRepository;
import com.blstream.patronage.ctf.service.PlayerService;
import com.blstream.patronage.ctf.service.PortalRoleService;
import com.blstream.patronage.ctf.service.PortalUserService;
import com.blstream.patronage.ctf.web.ui.PlayerUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;
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
 * User: mkr
 * Date: 2/19/13
 *
 * This is a implementation of PlayerService interface.
 *
 * @see com.blstream.patronage.ctf.service.PlayerService
 */
@Named("playerService")
public class PlayerServiceImpl extends CrudServiceImpl<Player, String, PlayerRepository> implements PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private PortalUserService portalUserService;
    private PortalRoleService portalRoleService;


    /**
     * @see com.blstream.patronage.ctf.service.PlayerService#createNewPlayer(com.blstream.patronage.ctf.web.ui.PlayerUI)
     */
    @Transactional(readOnly = false, rollbackFor = { Exception.class, RuntimeException.class })
    @Override
    public Player createNewPlayer(final PlayerUI playerUI) throws AlreadyExistsException {
        if (logger.isDebugEnabled()) {
            logger.debug("---- createNewPlayer");
        }
        Player newPlayer;

        Assert.notNull(playerUI, "Player cannot be null");

        Assert.notNull(playerUI.getUsername(), "Username cannot be null");
        Assert.notNull(playerUI.getPassword(), "Password cannot be null");

        /*
            Searching for existing player...
            If it exist, new player cannot be created.
         */
        if (findByUsername(playerUI.getUsername()) != null) {
            if (logger.isWarnEnabled()) {
                logger.warn(String.format("Player '%s' is already exist!", playerUI.getUsername()));
            }
            throw new AlreadyExistsException(String.format("Player '%s' is already exist.", playerUI.getUsername()));
        }

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Portal user: %s is now creating...", playerUI.getUsername()));
        }

        PortalUser portalUser = new PortalUser(playerUI.getUsername(), playerUI.getPassword());
        portalUser.setAccountNonExpired(true);
        portalUser.setAccountNonLocked(true);
        portalUser.setCredentialsNonExpired(true);
        portalUser.setEnabled(true);
        portalUser.setRoles(loadDefaultRolesForPlayer());
        portalUser = portalUserService.create(portalUser);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Portal user: %s was created successfully", portalUser.getUsername()));
        }

        newPlayer = new Player(PlayerType.PRIVATE, portalUser);


        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Player %s is now creating...", newPlayer.getPortalUser().getUsername()));
        }

        newPlayer = super.create(newPlayer);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Player %s was created successfully", newPlayer.getPortalUser().getUsername()));
        }

        return newPlayer;
    }

    /**
     * @see com.blstream.patronage.ctf.service.PlayerService#findByUsername(String)
     */
    @Override
    public Player findByUsername(String username) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findByUsername");
        }

        if (logger.isInfoEnabled()) {
            logger.info(String.format("Searching player based on username: %s", username));
        }

        Player player = repository.findByUsername(username);

        if (player != null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Player: %s was found - id: %s", username, player.getId()));
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Player: %s was not found.", username));
            }
        }

        return player;
    }

    /**
     * This methods loads all defaults portal roles for player.
     * @return List
     */
    private List<PortalRole> loadDefaultRolesForPlayer() {
        List<PortalRole> roles = new ArrayList<PortalRole>();

        PortalRole portalUser = portalRoleService.findByAuthority("PORTAL_USER");
        Assert.notNull(portalUser, "Portal user role doesn't exist.");
        roles.add(portalUser);

        PortalRole portalPlayer = portalRoleService.findByAuthority("PORTAL_PLAYER");
        Assert.notNull(portalPlayer, "Portal player role doesn't exist.");
        roles.add(portalPlayer);

        return roles;
    }

    @Inject
    @Named("playerRepository")
    @Override
    public void setRepository(PlayerRepository repository) {
        super.repository = repository;
    }

    @Inject
    @Named("portalUserService")
    public void setPortalUserService(PortalUserService portalUserService) {
        this.portalUserService = portalUserService;
    }

    @Inject
    @Named("portalRoleService")
    public void setPortalRoleService(PortalRoleService portalRoleService) {
        this.portalRoleService = portalRoleService;
    }
}

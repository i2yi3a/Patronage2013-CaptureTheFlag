package com.blstream.patronage.ctf.service;

import com.blstream.patronage.ctf.common.exception.AlreadyExistsException;
import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.web.ui.PlayerUI;

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
 * This class is a representation of player service in CRUD logic model.
 *
 * @see com.blstream.patronage.ctf.common.service.CrudService
 */
public interface PlayerService extends CrudService<Player, String> {

    /**
     * This method creates a new player.
     *
     * @param player
     * @return Player
     * @exception AlreadyExistsException
     */
    Player createNewPlayer(PlayerUI player) throws AlreadyExistsException;

    /**
     * This method finds player based on username.
     * @param username
     * @return Player
     */
    Player findByUsername(String username);
}

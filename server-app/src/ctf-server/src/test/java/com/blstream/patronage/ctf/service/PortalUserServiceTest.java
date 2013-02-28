package com.blstream.patronage.ctf.service;

import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.model.PlayerType;
import com.blstream.patronage.ctf.model.PortalUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.inject.Inject;

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
 * Date: 1/23/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public class PortalUserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PortalUserServiceTest.class);

    @Inject
    private MongoOperations mongoOperations;

    @Before
    @After
    public void cleanUp() {
        logger.debug("---- cleanUp");
        mongoOperations.dropCollection(PortalUser.class);
        mongoOperations.dropCollection(Player.class);
    }

    @Test
    public void createTest() throws Exception {
        logger.debug("---- createTest");

        PortalUser portalUser = new PortalUser("michal.krawczak@blstream.com", "F234!CWD31@WQ#F5%");
        Player player = new Player(PlayerType.PRIVATE, portalUser);

        logger.debug(String.format("portalUser: %s", portalUser.getUsername()));
        logger.debug(String.format("player: %s", player.getType()));

        mongoOperations.save(player);

        Assert.notNull(portalUser.getUsername());
        Assert.notNull(player.getId());

        logger.debug("All objects were saved without errors.");
    }
}

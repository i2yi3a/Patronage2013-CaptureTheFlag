package com.blstream.patronage.ctf.web.controller.secured;

import com.blstream.patronage.ctf.common.web.controller.BaseRestController;
import com.blstream.patronage.ctf.model.PortalUser;
import com.blstream.patronage.ctf.service.PortalUserService;
import com.blstream.patronage.ctf.web.converter.BaseUIConverter;
import com.blstream.patronage.ctf.web.ui.PortalUserUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
 * Date: 1/23/13
 *
 * This is a representation of trusted (secured) controller where all portal users
 * can by managed using CRUD logic model.
 *
 * Controller context: /api/secured/users
 */
@Controller
@RequestMapping("/api/secured/users")
public class PortalUserController extends BaseRestController<PortalUserUI, PortalUser, String, PortalUserService> {

    private static final Logger logger = LoggerFactory.getLogger(PortalUserController.class);

    public PortalUserController() {
        super(PortalUserUI.class);
    }

    @Inject
    @Named("portalUserService")
    @Override
    public void setService(PortalUserService service) {
        super.service = service;
    }

    @Inject
    @Named("portalUserUIConverter")
    @Override
    protected void setConverter(BaseUIConverter<PortalUserUI, PortalUser, String> converter) {
        super.converter = converter;
    }
}

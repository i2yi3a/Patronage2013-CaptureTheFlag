package com.blstream.patronage.ctf.web.controller.secured;

import com.blstream.patronage.ctf.common.web.controller.BaseRestController;
import com.blstream.patronage.ctf.model.SimpleModel;
import com.blstream.patronage.ctf.service.SimpleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * User: mkr
 * Date: 1/16/13
 *
 * Example.
 *
 * TODO: remove this example class.
 */
@Controller
@RequestMapping("/api/secured/simple")
public class SimpleController extends BaseRestController<SimpleModel, String, SimpleService> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    @Inject
    @Named("simpleService")
    public void setService(SimpleService service) {
        super.service = service;
    }

    @RequestMapping(value = "findByName/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SimpleModel findByName(@PathVariable String name) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findByName method invoked ----");
        }

        return service.findByName(name);
    }
}

package com.blstream.patronage.ctf.common.web.controller;

import com.blstream.patronage.ctf.common.ResourceGenericFactory;
import com.blstream.patronage.ctf.common.errors.ErrorCodeType;
import com.blstream.patronage.ctf.common.exception.AlreadyExistsException;
import com.blstream.patronage.ctf.common.exception.NotFoundException;
import com.blstream.patronage.ctf.common.service.CrudService;
import com.blstream.patronage.ctf.model.BaseModel;
import com.blstream.patronage.ctf.web.converter.BaseUIConverter;
import com.blstream.patronage.ctf.web.ui.BaseUI;
import com.blstream.patronage.ctf.web.ui.MessageUI;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2013 BLStream
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * User: mkr
 * Date: 1/16/13
 * <p/>
 * This is a representation of base REST controller with CRUD logic model. It's an implementation
 * of RestController interface.
 *
 * @see com.blstream.patronage.ctf.common.web.controller.RestController
 * @see com.blstream.patronage.ctf.common.web.controller.AbstractRestController
 */
public abstract class BaseRestController<UI extends BaseUI<ID>, T extends BaseModel<ID>, ID extends Serializable, S extends CrudService<T, ID>> extends AbstractRestController implements RestController<UI, T, ID> {

    private static Logger logger = LoggerFactory.getLogger(BaseRestController.class);

    protected S service;

    protected BaseUIConverter<UI, T, ID> converter;

    private final Class<UI> uiClassType;

    protected BaseRestController(Class<UI> uiClassType) {
        this.uiClassType = uiClassType;
    }

    @Inject
    @Named(value = "validator")
    private Validator validator;


    boolean validate(MessageUI message, UI request) {
        List<ConstraintViolation> errors = validator.validate(request);
        if (errors.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < errors.size(); i++) {
                ConstraintViolation error = errors.get(i);

                buffer.append(error.getMessage());

                if (i < errors.size() - 1)
                    buffer.append(",");
            }
            message.setErrorCode(ErrorCodeType.VALIDATION_ERROR);
            message.setError(ErrorCodeType.VALIDATION_ERROR.getMessage());
            message.setErrorDescription(buffer.toString());
            return true;
        }
        return false;
    }

    /**
     * This is an abstract method where service is set.
     *
     * @param service
     */
    protected abstract void setService(S service);

    /**
     * This is an abstract method where UI converter is set.
     *
     * @param converter
     */
    protected abstract void setConverter(BaseUIConverter<UI, T, ID> converter);

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#create(com.blstream.patronage.ctf.web.ui.BaseUI)
     */
    @Override
    public UI create(@RequestBody UI request) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- create method invoked ----");
        }

        UI response;
        T resource = converter.convert(request);
        MessageUI message = new MessageUI();
        if (validate(message, request)) {
            return createResponseErrorMessage(message.getErrorCodeType(), message.getErrorDescription());
        }
        try {
            resource = service.create(resource);
            response = converter.convert(resource);
            response.setErrorCode(ErrorCodeType.SUCCESS);
        } catch (AlreadyExistsException e) {
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_ALREADY_EXISTS, e.getMessage());
        } catch (Exception e) {
            response = createResponseErrorMessage(ErrorCodeType.INTERNAL_ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#create(com.blstream.patronage.ctf.web.ui.BaseUI)
     */
    @Override
    public UI update(@PathVariable ID id, @RequestBody UI request) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- update method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");
        Assert.notNull(request, "Resources cannot be null");
        Assert.isTrue(id.equals(request.getId()), "Request ID and resource ID cannot be different from each other");

        UI response;
        T resource = converter.convert(request);
        MessageUI message = new MessageUI();
        if (validate(message, request)) {
            return createResponseErrorMessage(message.getErrorCodeType(), message.getErrorDescription());
        }
        try {
            resource = service.update(id, resource);
            response = converter.convert(resource);
            response.setErrorCode(ErrorCodeType.SUCCESS);
        } catch (NotFoundException e) {
            logger.error("Not found exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.INTERNAL_ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#findAll()
     */
    @Override
    public Iterable<UI> findAll() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findAll method invoked ----");
        }

        List<T> modelList = service.findAll();
        List<UI> uiList = converter.convertModelList(modelList);

        return uiList;
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#findById(java.io.Serializable)
     */
    @Override
    public UI findById(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- findById method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");

        T resource;
        UI response;

        try {
            resource = service.findById(id);
            response = converter.convert(resource);
        } catch (NotFoundException e) {
            logger.error("Not found exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.INTERNAL_ERROR, e.getMessage());
        }

        return response;
    }

    /**
     * @see com.blstream.patronage.ctf.common.web.controller.RestController#delete(java.io.Serializable)
     */
    @Override
    public UI delete(@PathVariable ID id) {
        if (logger.isDebugEnabled()) {
            logger.debug("---- delete method invoked ----");
            logger.debug(String.format("id: %s", id));
        }

        Assert.notNull(id, "ID cannot be null");

        UI response;

        try {
            service.delete(id);
            response = createResponseErrorMessage(ErrorCodeType.SUCCESS);
        } catch (NotFoundException e) {
            logger.error("Not found exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.RESOURCE_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.error("Exception occurred.", e);
            response = createResponseErrorMessage(ErrorCodeType.INTERNAL_ERROR, e.getMessage());
        }

        if (logger.isDebugEnabled())
            logger.debug(String.format("response error code: %s", response.getErrorCode()));

        return response;
    }

    /**
     * This is a test method which always returns simple It's alive! text.
     *
     * @return "It's alive!"
     */
    @RequestMapping("/isAlive")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
    public
    @ResponseBody
    String isAlive() {
        if (logger.isDebugEnabled()) {
            logger.debug("---- isAlive method invoked ----");
        }

        return "It's alive!";
    }

    protected UI createResponseErrorMessage(ErrorCodeType type) {
        return createResponseErrorMessage(type, type.getMessage());
    }

    protected UI createResponseErrorMessage(ErrorCodeType type, String errorMessage) {
        Assert.notNull(uiClassType, "UI class type cannot by null");

        UI response = ResourceGenericFactory.createInstance(uiClassType);
        response.setErrorCode(type);

        if (ErrorCodeType.SUCCESS.equals(type)) {
            response.setMessage(type.getName());
            response.setDescription(type.getMessage());
        } else {
            response.setError(type.getName());
            response.setErrorDescription(errorMessage);
        }
        return response;
    }

    protected String getCurrentUsername() {
        if (logger.isDebugEnabled())
            logger.debug("---- getCurrentUsername");

        SecurityContext ctx = SecurityContextHolder.getContext();
        Assert.notNull(ctx, "Security context cannot be null");

        Authentication auth = ctx.getAuthentication();
        Assert.notNull(auth, "Authentication cannot be null");

        User principal = (User) auth.getPrincipal();
        Assert.notNull(principal, "Principal cannot be null");
        Assert.notNull(principal.getUsername(), "Principal username cannot be null");

        if (logger.isInfoEnabled()) {
            logger.info(String.format("Current logged user is: %s", principal));
        }

        if (logger.isDebugEnabled())
            logger.debug("---- /getCurrentUsername");

        return principal.getUsername();
    }
}

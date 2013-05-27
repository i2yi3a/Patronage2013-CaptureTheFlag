package com.blstream.patronage.ctf.common.web.controller;

import com.blstream.patronage.ctf.common.errors.ErrorCodeType;
import com.blstream.patronage.ctf.common.exception.BadRequestException;
import com.blstream.patronage.ctf.web.ui.MessageUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
 * Date: 1/31/13
 *
 * This class is a representation of abstract REST controller with exception handling.
 * All errors code are reading from error-code.properties file.
 */
public abstract class AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(AbstractRestController.class);

    @Value("${error.1}")
    private String badRequestMessage;

    @Value("${error.2}")
    private String failedMessage;


    /**
     * Returns error message when BadRequestException or IllegalArgumentException
     * exception occured.
     *
     * @see com.blstream.patronage.ctf.common.exception.BadRequestException
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageUI handleBadRequestException(Exception e) {
        MessageUI messageUI = new MessageUI();

        messageUI.setErrorDescription(e.getMessage());
        messageUI.setError(badRequestMessage);
        messageUI.setErrorCode(ErrorCodeType.BAD_REQUEST);

        logger.error("handleBadRequestException", e);

        return messageUI;
    }

    /**
     * Returns error message when Exception occured.
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageUI handleException(Exception e) {
        MessageUI messageUI = new MessageUI();

        messageUI.setErrorDescription(e.getMessage());
        messageUI.setError(failedMessage);
        messageUI.setErrorCode(ErrorCodeType.INTERNAL_ERROR);

        logger.error("handleException", e);

        return messageUI;
    }
}

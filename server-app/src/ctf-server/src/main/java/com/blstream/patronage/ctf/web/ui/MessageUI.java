package com.blstream.patronage.ctf.web.ui;

import com.blstream.patronage.ctf.common.errors.ErrorCodeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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
 * This class is a representation of UI message object. It's used for sending
 * all necessary message in JSON from server to clients.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "errorCodeType" })
public class MessageUI implements Serializable {

    private static final long serialVersionUID = -5666869456289227620L;

    private String message;

    private String error;

    @JsonProperty("message_description")
    private String description;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error_code")
    private ErrorCodeType errorCode;


    /**
     * Returns a message body.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a message body.
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets error message.
     * @return String
     */
    public String getError() {
        return error;
    }

    /**
     * Returns error message.
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns a message description.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets a message description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns error description.
     * @return String
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets error description.
     * @param errorDescription
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * Returns an error code in int format.
     * For example: for SUCCESS response error code is: 0.
     * @return Integer
     */
    public Integer getErrorCode() {
        return errorCode != null ? errorCode.getCode() : null;
    }

    /**
     * Returns an error code in enum type.
     * For example: for error code "0.0.0" enum is SUCCESS.
     * @return ErrorCodeType
     */
    public ErrorCodeType getErrorCodeType() {
        return errorCode;
    }

    /**
     * Sets an error code.
     * @param errorCode
     */
    public void setErrorCode(ErrorCodeType errorCode) {
        this.errorCode = errorCode;
    }
}

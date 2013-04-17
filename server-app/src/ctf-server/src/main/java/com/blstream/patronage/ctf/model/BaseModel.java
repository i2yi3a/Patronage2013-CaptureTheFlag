package com.blstream.patronage.ctf.model;

import java.io.Serializable;

/**
 * User: mkr
 * Date: 4/17/13
 */
public interface BaseModel<ID> extends Serializable {
    ID getId();
}

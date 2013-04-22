package com.blstream.patronage.ctf.common.exception;

/**
 * User: mkr
 * Date: 4/19/13
 */
public class CannotDeleteException extends RuntimeException {
    public CannotDeleteException() {
    }

    public CannotDeleteException(String s) {
        super(s);
    }
}

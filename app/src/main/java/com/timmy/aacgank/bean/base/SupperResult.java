package com.timmy.aacgank.bean.base;

import java.io.Serializable;

public class SupperResult implements Serializable {

    private boolean error;

    public boolean isSuccess() {
        return !error;
    }
}

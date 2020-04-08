package com.rabo.cst.domain;

import lombok.Data;

/**
 * Created by mravindran on 08/04/20.
 */
@Data
public class CustomErrorResponse {

    private int status;
    private String error;
}

package com.systemspecs.remita.easypay.extended.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String token_type;

    private Integer expires_in;

    private Integer ext_expires_in;

    private String access_token;

}

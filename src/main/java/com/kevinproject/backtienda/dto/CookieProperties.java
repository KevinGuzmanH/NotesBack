package com.kevinproject.backtienda.dto;

import com.kevinproject.backtienda.entity.SessionIdHash;
import lombok.Builder;

@Builder
public class CookieProperties {
    private String hash;
    private String username;
}

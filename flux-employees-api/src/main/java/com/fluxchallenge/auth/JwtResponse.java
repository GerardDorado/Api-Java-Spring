package com.fluxchallenge.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -24124123124145151L;
    private final String token;
}

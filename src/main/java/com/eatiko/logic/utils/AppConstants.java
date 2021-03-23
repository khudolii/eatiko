package com.eatiko.logic.utils;

public class AppConstants {

    //SECURITY
    public static final String SECURITY_SIGN_UP_URL = "/auth/**";
    public static final String SECURITY_SECRET = "SecretKeyGenJWT";
    public static final String SECURITY_TOKEN_PREFIX = "Brearer ";
    public static final String SECURITY_HEADER_STRING = "Authorization";
    public static final String SECURITY_CONTENT_TYPE = "application/json";
    public static final long SECURITY_EXPIRATION_TIME = 600_000;
}

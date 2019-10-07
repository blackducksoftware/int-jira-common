package com.synopsys.integration.jira.common.server;

public class OAuthTokenResponse {
    private String token;
    private String tokenSecret;

    public OAuthTokenResponse(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

}

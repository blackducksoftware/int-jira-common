/*
 * int-jira-common
 *
 * Copyright (c) 2023 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.rest.oauth1a;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.function.Supplier;

import org.apache.commons.codec.binary.Base64;

import com.google.api.client.auth.oauth.AbstractOAuthGetToken;
import com.google.api.client.auth.oauth.OAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.synopsys.integration.jira.common.model.oauth.OAuthAuthorizationData;
import com.synopsys.integration.jira.common.model.oauth.OAuthCredentialsData;

public class JiraOAuthService {
    public static final String OUT_OF_BAND_TO_SHOW_TOKEN_SECRET = "oob";
    public static final String TEMPORARY_TOKEN_SUFFIX = "/plugins/servlet/oauth/request-token";
    public static final String AUTHORIZATION_URL_SUFFIX = "/plugins/servlet/oauth/authorize";
    public static final String ACCESS_TOKEN_SUFFIX = "/plugins/servlet/oauth/access-token";

    private final String jiraServerUrl;
    private final KeyFactory rsaKeyFactory;

    public JiraOAuthService(String jiraServerUrl, KeyFactory rsaKeyFactory) {
        this.jiraServerUrl = jiraServerUrl;
        this.rsaKeyFactory = rsaKeyFactory;
    }

    public OAuthParameters createOAuthParameters(OAuthCredentialsData oAuthCredentialsData) throws InvalidKeySpecException {
        String accessTokenRequestUrl = jiraServerUrl + JiraOAuthService.ACCESS_TOKEN_SUFFIX;
        JiraOAuthGetAccessToken getAccessToken = new JiraOAuthGetAccessToken(accessTokenRequestUrl);
        getAccessToken.consumerKey = oAuthCredentialsData.getConsumerKey();
        getAccessToken.signer = getOAuthRsaSigner(oAuthCredentialsData.getPrivateKey());
        getAccessToken.temporaryToken = oAuthCredentialsData.getAccessToken();

        return getAccessToken.createParameters();
    }

    public OAuthAuthorizationData createOAuthAuthorizationData(String consumerKey, String privateKey) throws InvalidKeySpecException, IOException {
        JiraOAuthGetTemporaryToken getTemporaryToken = createJiraOAuthGetTemporaryToken(consumerKey, privateKey);
        OAuthCredentialsResponse oAuthCredentialsResponse = getTemporaryToken.execute();

        String authorizationToken = oAuthCredentialsResponse.token;
        String authorizationUrl = getAuthorizationUrl(oAuthCredentialsResponse);

        return new OAuthAuthorizationData(authorizationUrl, authorizationToken);
    }

    public JiraOAuthGetTemporaryToken createJiraOAuthGetTemporaryToken(String consumerKey, String privateKey) throws InvalidKeySpecException {
        String temporaryTokenRequestUrl = jiraServerUrl + JiraOAuthService.TEMPORARY_TOKEN_SUFFIX;
        JiraOAuthGetTemporaryToken getTemporaryToken = createTokenWithKeys(() -> new JiraOAuthGetTemporaryToken(temporaryTokenRequestUrl), consumerKey, privateKey);
        getTemporaryToken.transport = new NetHttpTransport();
        getTemporaryToken.callback = JiraOAuthService.OUT_OF_BAND_TO_SHOW_TOKEN_SECRET;

        return getTemporaryToken;
    }

    public String getAuthorizationUrl(OAuthCredentialsResponse oAuthCredentialsResponse) {
        String authorizeTemporaryTokenUrl = jiraServerUrl + JiraOAuthService.AUTHORIZATION_URL_SUFFIX;
        OAuthAuthorizeTemporaryTokenUrl authorizationUrl = new OAuthAuthorizeTemporaryTokenUrl(authorizeTemporaryTokenUrl);
        authorizationUrl.temporaryToken = oAuthCredentialsResponse.token;

        return authorizationUrl.toString();
    }

    public JiraOAuthGetAccessToken getJiraOAuthGetAccessToken(String temporaryToken, String verificationCodeFromJira, String consumerKey, String privateKey) throws InvalidKeySpecException {
        String accessTokenRequestUrl = jiraServerUrl + JiraOAuthService.ACCESS_TOKEN_SUFFIX;
        JiraOAuthGetAccessToken getAccessToken = createTokenWithKeys(() -> new JiraOAuthGetAccessToken(accessTokenRequestUrl), consumerKey, privateKey);
        getAccessToken.transport = new NetHttpTransport();
        getAccessToken.verifier = verificationCodeFromJira;
        getAccessToken.temporaryToken = temporaryToken;

        return getAccessToken;
    }

    private <T extends AbstractOAuthGetToken> T createTokenWithKeys(Supplier<T> supplier, String consumerKey, String privateKey) throws InvalidKeySpecException {
        T oAuthGetToken = supplier.get();
        oAuthGetToken.consumerKey = consumerKey;
        oAuthGetToken.signer = getOAuthRsaSigner(privateKey);
        return oAuthGetToken;
    }

    private OAuthRsaSigner getOAuthRsaSigner(String privateKeyString) throws InvalidKeySpecException {
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = rsaKeyFactory.generatePrivate(keySpec);

        OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
        oAuthRsaSigner.privateKey = privateKey;
        return oAuthRsaSigner;
    }

}

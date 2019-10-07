package com.synopsys.integration.jira.common.server;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth.OAuthRsaSigner;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.rest.client.IntHttpClient;

public class OAuthAccessTokenRetriever {
    private static final String REQUEST_TOKEN_SPEC = "/plugins/servlet/oauth/request-token";
    private static final String ACCESS_TOKEN_SPEC = "/plugins/servlet/oauth/access-token";
    private static final String AUTHORIZE_TOKEN_SPEC = "/plugins/servlet/oauth/authorize";
    private static final String PARAM_OAUTH_TOKEN = "oauth_token";

    private IntHttpClient intHttpClient;
    private String jiraBaseUrl;

    public OAuthAccessTokenRetriever(IntHttpClient intHttpClient, String jiraBaseUrl) {
        this.intHttpClient = intHttpClient;
        this.jiraBaseUrl = jiraBaseUrl;
    }

    public OAuthTokenResponse getRequestToken(String consumerKey, String privateKey) throws IntegrationException {
        try {
            OAuthGetTemporaryToken oAuthGetTemporaryToken = new OAuthGetTemporaryToken(jiraBaseUrl + REQUEST_TOKEN_SPEC);
            oAuthGetTemporaryToken.consumerKey = consumerKey;
            oAuthGetTemporaryToken.signer = getOAuthRsaSigner(privateKey);
            oAuthGetTemporaryToken.transport = new ApacheHttpTransport(); // TODO construct the correct transport
            oAuthGetTemporaryToken.callback = "oob";

            OAuthCredentialsResponse credentials = oAuthGetTemporaryToken.execute();
            return new OAuthTokenResponse(credentials.token, credentials.tokenSecret);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            throw new IntegrationException(e);
        }
    }

    /**
     * @param oAuthRequestTokenResponse the response from calling OAuthAccessTokenRetriever.getRequestToken()
     * @return A link that must be followed as part of 2-step verification for the request token.
     */
    public String createAuthorizationLink(OAuthTokenResponse oAuthRequestTokenResponse) {
        String baseUrl = jiraBaseUrl;
        if (jiraBaseUrl.endsWith("/")) {
            baseUrl = StringUtils.substringBeforeLast(jiraBaseUrl, "/");
        }
        return String.format("%s%s?%s=%s", baseUrl, AUTHORIZE_TOKEN_SPEC, PARAM_OAUTH_TOKEN, oAuthRequestTokenResponse.getToken());
    }

    public OAuthTokenResponse getAccessToken(String consumerKey, String privateKey, OAuthTokenResponse oAuthRequestTokenResponse, String verificationCode) throws IntegrationException {
        try {
            OAuthGetAccessToken oAuthGetAccessToken = new OAuthGetAccessToken(jiraBaseUrl + ACCESS_TOKEN_SPEC);
            oAuthGetAccessToken.consumerKey = consumerKey;
            oAuthGetAccessToken.signer = getOAuthRsaSigner(privateKey);
            oAuthGetAccessToken.transport = new ApacheHttpTransport(); // TODO construct the correct transport
            oAuthGetAccessToken.verifier = verificationCode;
            oAuthGetAccessToken.temporaryToken = oAuthRequestTokenResponse.getToken();

            OAuthCredentialsResponse credentials = oAuthGetAccessToken.execute();
            return new OAuthTokenResponse(credentials.token, credentials.tokenSecret);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new IntegrationException(e);
        }
    }

    private OAuthRsaSigner getOAuthRsaSigner(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        OAuthRsaSigner oAuthRsaSigner = new OAuthRsaSigner();
        oAuthRsaSigner.privateKey = getPrivateKey(privateKey);
        return oAuthRsaSigner;
    }

    private PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

}

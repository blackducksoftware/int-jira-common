package com.synopsys.integration.jira.common.server;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.LogLevel;
import com.synopsys.integration.log.PrintStreamIntLogger;
import com.synopsys.integration.rest.client.IntHttpClient;
import com.synopsys.integration.rest.proxy.ProxyInfo;

public class OAuthAccessTokenRetrieverTest {
    // @Test
    public void getRequestTokenTest() throws IntegrationException {
        PrintStreamIntLogger logger = new PrintStreamIntLogger(System.out, LogLevel.INFO);
        IntHttpClient intHttpClient = new IntHttpClient(logger, 300, true, ProxyInfo.NO_PROXY_INFO);
        OAuthAccessTokenRetriever oAuthAccessTokenRetriever = new OAuthAccessTokenRetriever(intHttpClient, "http://localhost:2990/jira");
        OAuthTokenResponse requestToken = oAuthAccessTokenRetriever.getRequestToken(
            "Alert",
            "<PRIVATE KEY>");
        System.out.println(requestToken.getToken());
    }

}

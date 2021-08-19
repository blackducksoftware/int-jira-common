package com.synopsys.integration.jira.common.test;

import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.Assumptions;

public class TestProperties {
    private Properties properties;
    private String propertiesLocation;

    public TestProperties() {
        try {
            propertiesLocation = TestResourceUtils.createTestPropertiesCanonicalFilePath().toString();
        } catch (IOException e) {
            propertiesLocation = TestResourceUtils.DEFAULT_PROPERTIES_FILE_NAME;
        }
        loadProperties();
    }

    public Properties getProperties() {
        loadProperties();
        return properties;
    }

    public void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties = TestResourceUtils.loadProperties(propertiesLocation);
            } catch (Exception ex) {
                System.out.println("Couldn't load " + propertiesLocation + " file!");
                System.out.println("Reading from the environment...");
            }
            populatePropertiesFromEnv();
        }
    }

    public String getProperty(TestPropertyKey propertyKey) {
        return getProperty(propertyKey.getPropertyKey());
    }

    public String getProperty(String propertyKey) {
        assumeTrue(propertyKey);
        return getProperties().getProperty(propertyKey);
    }

    public void assumeTrue(TestPropertyKey propertyKey) {
        assumeTrue(propertyKey.getPropertyKey());
    }

    public void assumeTrue(String propertyKey) {
        Assumptions.assumeTrue(getProperties().containsKey(propertyKey));
    }

    public boolean containsKey(TestPropertyKey propertyKey) {
        return containsKey(propertyKey.getPropertyKey());
    }

    public boolean containsKey(String propertyKey) {
        return getProperties().containsKey(propertyKey);
    }

    private void populatePropertiesFromEnv() {
        for (TestPropertyKey key : TestPropertyKey.values()) {
            String prop = System.getenv(key.name());
            if (prop != null && !prop.isEmpty()) {
                properties.put(key.getPropertyKey(), prop);
            }
        }
    }
}

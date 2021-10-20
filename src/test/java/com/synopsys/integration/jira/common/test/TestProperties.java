package com.synopsys.integration.jira.common.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.jupiter.api.Assumptions;

public class TestProperties {
    private static final String DEFAULT_PROPERTIES_FILE_NAME = "test.properties";
    private static final String PROPERTIES_LOCATION = "src/main/resources/" + DEFAULT_PROPERTIES_FILE_NAME;

    private static Properties properties;

    public static TestProperties loadTestProperties() {
        loadProperties();
        return new TestProperties();
    }

    private TestProperties() { }

    public Properties getProperties() {
        loadProperties();
        return properties;
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

    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream iStream = new FileInputStream(PROPERTIES_LOCATION)) {
                properties.load(iStream);
            } catch (IOException ioException) {
                System.out.println("Failed to load properties from " + PROPERTIES_LOCATION);
            }
        }
        populatePropertiesFromEnv();
    }

    private static void populatePropertiesFromEnv() {
        for (TestPropertyKey key : TestPropertyKey.values()) {
            String prop = System.getenv(key.name());
            if (prop != null && !prop.isEmpty()) {
                properties.put(key.getPropertyKey(), prop);
            }
        }
    }
}

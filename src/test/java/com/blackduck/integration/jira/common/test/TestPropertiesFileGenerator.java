package com.blackduck.integration.jira.common.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestPropertiesFileGenerator {
    @Test
    @Disabled("This test is to generate the test.properties for new developers.")
    public void generatePropertiesFile() throws IOException {
        System.out.println("Generating file: " + TestProperties.PROPERTIES_LOCATION + "..");

        File testPropertiesFile = new File(TestProperties.PROPERTIES_LOCATION);

        if (!testPropertiesFile.exists()) {
            boolean successfullyCreated = testPropertiesFile.createNewFile();
            if (!successfullyCreated) {
                System.out.println("There was a problem creating the file '" + TestProperties.PROPERTIES_LOCATION + "'.");
                return;
            }

            StringBuilder dataBuilder = new StringBuilder();
            for (TestPropertyKey propertyKey : TestPropertyKey.values()) {
                dataBuilder.append(propertyKey.getPropertyKey());
                dataBuilder.append('=');
                dataBuilder.append(System.lineSeparator());
            }
            FileUtils.write(testPropertiesFile, dataBuilder.toString(), Charset.defaultCharset(), false);
        } else {
            System.out.println("The file '" + TestProperties.PROPERTIES_LOCATION + "' already exists, please rename or back it up.");
        }
    }
}

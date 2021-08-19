package com.synopsys.integration.jira.common.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

//TODO: Remove this class and roll changes into private method  of TestProperties
public class TestResourceUtils {
    public static final String DEFAULT_PROPERTIES_FILE_NAME = "test.properties";

    //private static final String SUB_PROJECT_NAME = "test-common";
    private static final File EXPECTED_BASE_TEST_RESOURCE_DIR = new File(TestResourceUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "../../../../src/main/resources/");

    //TODO: This is older code, prior to moving to test-common
    public static final File BASE_TEST_RESOURCE_DIR = new File(TestResourceUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "../../../../src/main/resources/");

    public static String createTestPropertiesCanonicalFilePath() throws IOException {
        return new File(TestResourceUtils.BASE_TEST_RESOURCE_DIR.getCanonicalFile(), TestResourceUtils.DEFAULT_PROPERTIES_FILE_NAME).getCanonicalPath();
    }

    /*
    //TODO: Verify all the paths we are using here, it is taken directly from alert's test-common
    //TODO: This was newer code, added when it was moved to test-common (?)
    public static Path createTestPropertiesCanonicalFilePath() throws IOException {
        File buildOutputDir = new File(TestResourceUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalFile();
        File subProjectDir = findAncestorDirectory(buildOutputDir, SUB_PROJECT_NAME).orElse(EXPECTED_BASE_TEST_RESOURCE_DIR);
        return Path.of(subProjectDir.getAbsolutePath(), "src", "main", "resources", TestResourceUtils.DEFAULT_PROPERTIES_FILE_NAME);
    }

    public static Optional<File> findAncestorDirectory(File file, String ancestorDirectoryName) {
        while (!ancestorDirectoryName.equals(file.getName())) {
            file = file.getParentFile();
            if (null == file) {
                return Optional.empty();
            }
        }
        return Optional.of(file);
    }
    */
    //TODO: ClassPathResource is from spring framework. Is it not availible here?
    public static Properties loadProperties(String resourceLocation) throws IOException {
        Properties properties = new Properties();

        //No spring so we can't get the class path resource?
        //ClassPathResource classPathResource = new ClassPathResource(resourceLocation);

        //Alternative:
        File propertiesFile = new File(EXPECTED_BASE_TEST_RESOURCE_DIR, DEFAULT_PROPERTIES_FILE_NAME);

        //try (InputStream classPathInputStream = classPathResource.getInputStream()) {
        try (InputStream iStream = new FileInputStream(propertiesFile)) {
            //properties.load(classPathInputStream);
            properties.load(iStream);
            return properties;
        } catch (IOException ioException) {
            System.out.printf("Failed to load [%s] from classpath%n", resourceLocation);
        }

        File fileResource = new File(resourceLocation);
        try (FileInputStream fileInputStream = FileUtils.openInputStream(fileResource)) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException ioException) {
            System.out.printf("Failed to load [%s] as file%n", resourceLocation);
            throw ioException;
        }
    }

}

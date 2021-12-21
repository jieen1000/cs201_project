package com.kaizen.model;

import com.kaizen.model.entity.Image;

import org.springframework.mock.web.MockMultipartFile;

/**
 * {@code TestImage} is a mock class that stored configurations needed to do
 * testing related to {@link Image} and {@link ImageController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
public class TestImage {
    /**
     * Represents the image's id used for testing.
     */
    public final static Long TEST_ID = 0L;

    /**
     * Represents the default image url.
     */
    public final static String DEFAULT_IMAGE_URL = "https://s3.ap-southeast-1.amazonaws.com/kaizen-imagebucket/1634743589773-worker1.jpg";

    /**
     * Represents the URL extension of the image's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/employees/image/";

    /**
     * Represents the employee's id key that used in image's API call.
     */
    public final static String EMP_ID_KEY = "empId";

    /**
     * Represents the url's id key that used in image's API call.
     */
    public final static String URL_ID_KEY = "url";

    /**
     * Create an image with necessary fields filled to use for testing.
     * 
     * @return the image to use for testing.
     */
    public static Image createImage() {
        Image image = new Image();
        image.setId(TEST_ID);
        image.setProfileURL("profile_url");
        image.setEmployee(TestEmployee.createEmployee());
        return image;
    }

    public static MockMultipartFile createMockMultipartFile() {
        String name = "file";
        byte[] content = null;
        return new MockMultipartFile(name, content);
    }

    public static MockMultipartFile createInvalidMockMultipartFile() {
        String name = "name";
        byte[] content = null;
        return new MockMultipartFile(name, content);
    }
}

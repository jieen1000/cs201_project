package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.TestImage;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;
import com.kaizen.repository.ImageRepository;
import com.kaizen.service.image.ImageService;
import com.kaizen.service.image.ImageServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code ImageServiceTest} is a test class to do unit testing on
 * {@link ImageService} using {@link ImageServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { ImageServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class ImageServiceTest {
    /**
     * The mocked image's repository used for testing.
     */
    @MockBean
    private ImageRepository imageRepository;

    /**
     * The image's service used for testing.
     */
    @Autowired
    private ImageService imageService;

    /**
     * {@code listImages_Found_ReturnFound} is a test on
     * {@link ImageService#listCompanies()} to verify if the method will call
     * {@link ImageRepository#findAll()} and return the list of all images.
     */
    @Test
    void listImages_Found_ReturnFound() {
        List<Image> images = new ArrayList<>();
        when(imageRepository.findAll()).thenReturn(images);

        List<Image> foundImages = imageService.listImages();

        assertSame(images, foundImages);
        verify(imageRepository).findAll();
    }

    /**
     * {@code addImage_Null_ThrowNullValueException} is a test on
     * {@link ImageService#addImage(Image)} to verify if the method will throw
     * {@link NullValueException} when the specific image is null.
     */
    @Test
    void addImage_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            imageService.addImage(null);
        });
    }

    /**
     * {@code addImage_New_ReturnSaved} is a test on
     * {@link ImageService#addImage(Image)} to verify if the method will call
     * {@link ImageRepository#findById(String)} and
     * {@link ImageRepository#save(Image)} and save and return the specific image.
     */
    @Test
    void addImage_New_ReturnSaved() {
        Image image = TestImage.createImage();
        when(imageRepository.save(image)).thenReturn(image);

        Image savedImage = imageService.addImage(image);

        assertSame(image, savedImage);
        verify(imageRepository).save(image);
    }

    /**
     * {@code deleteImage_NullId_ThrowNullValueException} is a test on
     * {@link ImageService#deleteImage(String)} to verify if the method will throw
     * {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteImage_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            imageService.deleteImage(null);
        });
    }

    /**
     * {@code deleteImage_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link ImageService#deleteImage(String)} to verify if the method will call
     * {@link ImageRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the image with the specific id does not
     * exists.
     */
    @Test
    void deleteImage_NotFound_ThrowObjectNotExistsException() {
        when(imageRepository.findById(TestImage.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            imageService.deleteImage(TestImage.TEST_ID);
        });

        verify(imageRepository).findById(TestImage.TEST_ID);
    }

    /**
     * {@code deleteImage_Deleted} is a test on
     * {@link ImageService#deleteImage(String)} to verify if the method will call
     * {@link ImageRepository#findById(String)} and
     * {@link ImageRepository#deleteById(String)} and delete the image with specific
     * id.
     */
    @Test
    void deleteImage_Deleted() {
        when(imageRepository.findById(TestImage.TEST_ID)).thenReturn(Optional.of(TestImage.createImage()));
        doNothing().when(imageRepository).deleteById(TestImage.TEST_ID);

        imageService.deleteImage(TestImage.TEST_ID);

        verify(imageRepository).findById(TestImage.TEST_ID);
        verify(imageRepository).deleteById(TestImage.TEST_ID);
    }

    /**
     * {@code getProfileImageURL_NotFound_ThrowObjectNotExistsException} is a test
     * on {@link ImageService#getProfileImageURL(Employee)} to verify if the method
     * will throw {@link NullValueException} when the specific employee is null.
     */
    @Test
    void getProfileImageURL_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            imageService.getProfileImageURL(null);
        });
    }

    /**
     * {@code getProfileImageURL_NotFound_ThrowObjectNotExistsException} is a test
     * on {@link ImageService#getProfileImageURL(Employee)} to verify if the method
     * will call {@link ImageRepository#findByEmployee(Employee)} and return a
     * default image url.
     */
    @Test
    void getProfileImageURL_NotFound_ReturnDefault() {
        Employee employee = TestEmployee.createEmployee();
        when(imageRepository.findByEmployee(employee)).thenReturn(new ArrayList<>());

        String foundImageURL = imageService.getProfileImageURL(employee);

        assertSame(TestImage.DEFAULT_IMAGE_URL, foundImageURL);
        verify(imageRepository).findByEmployee(employee);
    }

    /**
     * {@code getProfileImageURL_Found_ReturnFound} is a test on
     * {@link ImageService#getProfileImageURL(Employee)} to verify if the method
     * will call {@link ImageRepository#findByEmployee(Employee)} and return the
     * image with the specific id.
     */
    @Test
    void getProfileImageURL_Found_ReturnFound() {
        Image image = TestImage.createImage();
        List<Image> images = new ArrayList<>();
        images.add(image);
        when(imageRepository.findByEmployee(image.getEmployee())).thenReturn(images);

        String foundImageURL = imageService.getProfileImageURL(image.getEmployee());

        assertEquals(image.getProfileURL(), foundImageURL);
        verify(imageRepository).findByEmployee(image.getEmployee());
    }
}

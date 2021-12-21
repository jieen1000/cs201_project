package com.kaizen.service.image;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;
import com.kaizen.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Tan Jie En
 * @version 1.0
 * @since 2021-10-20
 */

@Service
public class ImageServiceImpl implements ImageService {

    /**
     * The Image's repository that store Images.
     */
    private final ImageRepository imageRepository;

    /**
     * Represents the simple name of the Image class.
     */
    private final String Image_SIMPLE_NAME;

    /**
     * Create a Image's service implementation with the specific Image's repository
     * and set the {@code image_SIMPLE_NAME} with the simple name of the Image's
     * class
     * 
     * @param ImageRepository the image's repository used by the application.
     */
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        Image_SIMPLE_NAME = Image.class.getSimpleName();
    }

    /**
     * Get all Images stored in the repository.
     * 
     * @return the list of all Images.
     */
    @Override
    public List<Image> listImages() {
        return imageRepository.findAll();
    }

    /**
     * Create the specific Image in the repository.
     * 
     * @param Image the Image to create.
     * @exception NullValueException    If the id of the Image is null.
     * @exception ObjectExistsException If the Image is in the repository.
     * @return the created Image.
     */
    @Override
    public Image addImage(Image image) throws NullValueException, ObjectExistsException {
        validateImageNotNull(image);
        return imageRepository.save(image);
    }

    /**
     * Delete the Image with the specific id in the repository.
     * 
     * @param id the id of the Image to delete.
     * @exception NullValueException       If the id of the Image is null.
     * @exception ObjectNotExistsException If the Image is not in the repository.
     */
    @Override
    public void deleteImage(Long id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateImageExists(id);
        imageRepository.deleteById(id);
    }

    /**
     * Get the most recent Image URL through the employee in the repository.
     * 
     * @param emp employee which we want to retrieve the profile image for.
     * @exception NullValueException If the employee passed in is null.
     * @return return the most recent Image URL.
     */
    @Override
    public String getProfileImageURL(Employee emp) throws NullValueException {
        validateEmployeeNotNull(emp);
        List<Image> imageList = imageRepository.findByEmployee(emp);
        if (imageList.size() == 0)
            return "https://s3.ap-southeast-1.amazonaws.com/kaizen-imagebucket/1634743589773-worker1.jpg";
        return imageList.get(imageList.size() - 1).getProfileURL() + "";
    }

    

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the Image to validate.
     * @exception NullValueException If the id of the Image is null.
     */
    private void validateIdNotNull(Long id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(Image_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific Image is not null.
     * 
     * @param Image the Image to validate.
     * @exception NullValueException If the Art is null.
     */
    private void validateImageNotNull(Image image) throws NullValueException {
        if (image == null) {
            throw new NullValueException(Image_SIMPLE_NAME);
        }
    }

    /**
     * Validate the Image with the specific id is in the repository.
     * 
     * @param id the id of the Image to validate.
     * @exception ObjectNotExistsException If the Image is not in the repository.
     */
    private void validateImageExists(Long id) throws ObjectNotExistsException {
        if (imageRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(Image_SIMPLE_NAME, "" + id);
        }
    }


    /**
     * Validate the Image with the specific id is in the repository.
     * 
     * @param emp the emp of the Image to validate.
     * @exception NullValueException If the employee is null
     */
    private void validateEmployeeNotNull(Employee emp) throws NullValueException {
        if (emp == null) {
            throw new NullValueException("Employee is null");
        }
    }

}
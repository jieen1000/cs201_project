package com.kaizen.service.image;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;

import java.util.List;

/**
 * @author Tan Jie En
 * @version 1.0
 * @since 2021-10-20
 */

public interface ImageService {

    /**
     * Get the list of images within the repository
     * 
     * @return a list of images from a repository
     */
    List<Image> listImages();

    /**
   * Create the specific image in the repository.
   * 
   * @param image the image to create.
   * @exception NullValueException    If the id of the image is null.
   * @exception ObjectExistsException If the image exists in the repository.
   * @return the created image.
   */
    Image addImage(Image image) throws NullValueException, ObjectExistsException;


    /**
     * Delete the Image with the specific id in the repository.
     * 
     * @param id the id of the Image to delete.
     * @exception NullValueException       If the id of the Image is null.
     * @exception ObjectNotExistsException If the Image is not in the repository.
     */
    void deleteImage(Long id) throws NullValueException, ObjectNotExistsException;


    /**
     * Get the most recent Image URL through the employee in the repository.
     * 
     * @param emp the emp of the Art to delete.
     * @exception NullValueException       If the id of the Art is null.
     * @exception ObjectNotExistsException If the Art is not in the repository.
     * @return return the most recent Image URL.
     */
    String getProfileImageURL(Employee emp);

}
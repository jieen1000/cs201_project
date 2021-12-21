package com.kaizen.service.art;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Art;

import java.util.List;

/**
 * {@code ArtService} captures what are needed for business's logic for Art.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-15
 */
public interface ArtService {
    /**
     * Get all ARTs stored in the repository.
     * 
     * @return the list of all ARTs.
     */
    List<Art> listArts();

    /**
     * Get all latest ARTs stored in the repository.
     * 
     * @return the list of all latest ARTs.
     */
    List<Art> listLatestArts();


    /**
     * Get all Arts from a company stored in the repository.
     * 
     * @return the list of all ARTs from a company.
     */
    List<Art> listArtsByCompany(String compId);

     /**
     * Create the specific Art in the repository.
     * 
     * @param Art the Art to create.
     * @exception NullValueException    If the id of the Art is null.
     * @exception ObjectExistsException If the Art is in the repository.
     * @return the created Art.
     */
    Art addArt(Art Art) throws NullValueException, ObjectExistsException;

   
    /**
     * Delete the Art with the specific id in the repository.
     * 
     * @param id the id of the Art to delete.
     * @exception NullValueException       If the id of the Art is null.
     * @exception ObjectNotExistsException If the Art is not in the repository.
     */
    void deleteArt(Long id) throws NullValueException, ObjectNotExistsException;
}
package com.kaizen.service.art;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Art;
import com.kaizen.model.entity.Company;
import com.kaizen.repository.ArtRepository;
import com.kaizen.service.company.CompanyService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code ArtServiceImpl} is an implementation of {@code ArtService}.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-15
 */
@Service
public class ArtServiceImpl implements ArtService {
    /**
     * The Art's repository that stored Arts.
     */
    private final ArtRepository artRepository;

    /**
     * The Company's service that provides company.
     */
    private final CompanyService companyService;

    /**
     * Represents the simple name of the Art's class.
     */
    private final String Art_SIMPLE_NAME;

    /**
     * Create a Art's service implementation with the specific Art's repository
     * and set the {@code Art_SIMPLE_NAME} with the simple name of the Art's
     * class
     * 
     * @param ArtRepository the Art's repository used by the application.
     * @param CompanyService the Company's service used by the application.
     */
    public ArtServiceImpl(ArtRepository artRepository, CompanyService companyService) {
        this.artRepository = artRepository;
        this.companyService = companyService;
        Art_SIMPLE_NAME = Art.class.getSimpleName();
    }

    /**
     * Get all Arts stored in the repository.
     * 
     * @return the list of all Arts.
     */
    @Override
    public List<Art> listArts() {
        return artRepository.findAll();
    }

    /**
     * Get all the list of latest Arts of each employee stored in the repository.
     * 
     * @return the list of latest Arts of each employee.
     */
    @Override
    public List<Art> listLatestArts() {
        return artRepository.findLatestResultsforAllEmployee();
    }

    /**
     * Get all the list of Arts from each company stored in the repository.
     * 
     * @return the list of Arts from each company.
     */
    @Override
    public List<Art> listArtsByCompany(String compId) {
        Company com = companyService.getCompany(compId);
        return artRepository.findByCompany(com);
    }

    /**
     * Create the specific Art in the repository.
     * 
     * @param Art the Art to create.
     * @exception NullValueException    If the id of the Art is null.
     * @return the created Art.
     */
    @Override
    public Art addArt(Art Art) throws NullValueException {
        validateArtNotNull(Art);
        return artRepository.save(Art);
    }


    /**
     * Delete the Art with the specific id in the repository.
     * 
     * @param id the id of the Art to delete.
     * @exception NullValueException       If the id of the Art is null.
     * @exception ObjectNotExistsException If the Art is not in the repository.
     */
    @Override
    public void deleteArt(Long id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateArtExists(id);
        artRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the Art to validate.
     * @exception NullValueException If the id of the Art is null.
     */
    private void validateIdNotNull(Long id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(Art_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific Art is not null.
     * 
     * @param Art the Art to validate.
     * @exception NullValueException If the Art is null.
     */
    private void validateArtNotNull(Art Art) throws NullValueException {
        if (Art == null) {
            throw new NullValueException(Art_SIMPLE_NAME);
        }
    }

    /**
     * Validate the Art with the specific id is in the repository.
     * 
     * @param id the id of the Art to validate.
     * @exception ObjectNotExistsException If the Art is not in the repository.
     */
    private void validateArtExists(Long id) throws ObjectNotExistsException {
        if (artRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(Art_SIMPLE_NAME, "" + id);
        }
    }

}
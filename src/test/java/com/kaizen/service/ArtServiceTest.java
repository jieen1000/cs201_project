package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestArt;
import com.kaizen.model.TestCompany;
import com.kaizen.model.entity.Art;
import com.kaizen.model.entity.Company;
import com.kaizen.repository.ArtRepository;
import com.kaizen.service.art.ArtService;
import com.kaizen.service.art.ArtServiceImpl;
import com.kaizen.service.company.CompanyService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code ArtServiceTest} is a test class to do unit testing on
 * {@link ArtService} using {@link ArtServiceImpl}.
 *
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { ArtServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class ArtServiceTest {
    /**
     * The mocked art's repository used for testing.
     */
    @MockBean
    private ArtRepository artRepository;

    /**
     * The company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The art's service used for testing.
     */
    @Autowired
    private ArtService artService;

    /**
     * {@code listArts_Found_ReturnFound} is a test on
     * {@link ArtService#listLatestArts()} to verify if the method will call
     * {@link ArtRepository#findAll()} and return the list of all arts.
     */
    @Test
    void listArts_Found_ReturnFound() {
        List<Art> arts = new ArrayList<>();
        when(artRepository.findAll()).thenReturn(arts);

        List<Art> foundArts = artService.listArts();

        assertSame(arts, foundArts);
        verify(artRepository).findAll();
    }

    /**
     * {@code listLatestArts_Found_ReturnFound} is a test on
     * {@link ArtService#listLatestArts()} to verify if the method will call
     * {@link ArtRepository#findLatestResultsforAllEmployee()} and return the list
     * of all latest arts.
     */
    @Test
    void listLatestArts_Found_ReturnFound() {
        List<Art> arts = new ArrayList<>();
        when(artRepository.findLatestResultsforAllEmployee()).thenReturn(arts);

        List<Art> foundArts = artService.listLatestArts();

        assertSame(arts, foundArts);
        verify(artRepository).findLatestResultsforAllEmployee();
    }

    /**
     * {@code listArtsByCompany_Null_ThrowNullValueException} is a test on
     * {@link ArtService##listArtsByCompany(String)} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and throw
     * {@link NullValueException} when the specific company's id is null.
     */
    @Test
    void listArtsByCompany_Null_ThrowNullValueException() {
        when(companyService.getCompany(any(String.class))).thenThrow(new NullValueException());

        assertThrows(NullValueException.class, () -> {
            artService.listArtsByCompany(TestCompany.TEST_ID);
        });

        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code listArtsByCompany_Null_ObjectNotExistsException} is a test on
     * {@link ArtService##listArtsByCompany(String)} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and throw
     * {@link ObjectNotExistsException} when the specific company's id is null.
     */
    @Test
    void listArtsByCompany_Null_ObjectNotExistsException() {
        when(companyService.getCompany(any(String.class))).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            artService.listArtsByCompany(TestCompany.TEST_ID);
        });

        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code listLatestArts_Found_ReturnFound} is a test on
     * {@link ArtService##listArtsByCompany(String)} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and
     * {@link ArtRepository#findByCompany(Company)} and return the list of all arts
     * of a company.
     */
    @Test
    void listArtsByCompany_Found_ReturnFound() {
        List<Art> arts = new ArrayList<>();
        when(companyService.getCompany(any(String.class))).thenReturn(TestCompany.createCompany());
        when(artRepository.findByCompany(any(Company.class))).thenReturn(arts);

        List<Art> foundArts = artService.listArtsByCompany(TestCompany.TEST_ID);

        assertSame(arts, foundArts);
        verify(companyService).getCompany(any(String.class));
        verify(artRepository).findByCompany(any(Company.class));
    }

    /**
     * {@code addArt_Null_ThrowNullValueException} is a test on
     * {@link ArtService#addArt(Art)} to verify if the method will throw
     * {@link NullValueException} when the specific art is null.
     */
    @Test
    void addArt_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            artService.addArt(null);
        });
    }

    /**
     * {@code addArt_New_ReturnSaved} is a test on {@link ArtService#addArt(Art)} to
     * verify if the method will call {@link ArtRepository#findById(String)} and
     * {@link ArtRepository#save(Art)} and save and return the specific art.
     */
    @Test
    void addArt_New_ReturnSaved() {
        Art art = TestArt.createArt();
        when(artRepository.save(art)).thenReturn(art);

        Art savedArt = artService.addArt(art);

        assertSame(art, savedArt);
        verify(artRepository).save(art);
    }

    /**
     * {@code deleteArt_NullId_ThrowNullValueException} is a test on
     * {@link ArtService#deleteArt(String)} to verify if the method will throw
     * {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteArt_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            artService.deleteArt(null);
        });
    }

    /**
     * {@code deleteArt_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link ArtService#deleteArt(String)} to verify if the method will call
     * {@link ArtRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the art with the specific id does not
     * exists.
     */
    @Test
    void deleteArt_NotFound_ThrowObjectNotExistsException() {
        when(artRepository.findById(TestArt.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            artService.deleteArt(TestArt.TEST_ID);
        });

        verify(artRepository).findById(TestArt.TEST_ID);
    }

    /**
     * {@code deleteArt_Deleted} is a test on {@link ArtService#deleteArt(String)}
     * to verify if the method will call {@link ArtRepository#findById(String)} and
     * {@link ArtRepository#deleteById(String)} and delete the art with specific id.
     */
    @Test
    void deleteArt_Deleted() {
        when(artRepository.findById(TestArt.TEST_ID)).thenReturn(Optional.of(TestArt.createArt()));
        doNothing().when(artRepository).deleteById(TestArt.TEST_ID);

        artService.deleteArt(TestArt.TEST_ID);

        verify(artRepository).findById(TestArt.TEST_ID);
        verify(artRepository).deleteById(TestArt.TEST_ID);
    }
}

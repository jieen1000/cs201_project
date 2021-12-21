package com.kaizen.model;

import java.time.LocalDate;

import com.kaizen.model.dto.ArtDTO;
import com.kaizen.model.entity.Art;

/**
 * {@code TestArt} is a mock class that stored configurations needed to do
 * testing related to {@link Art} and {@link ArtController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
public class TestArt {
    /**
     * Represents the ART's id used for testing.
     */
    public final static Long TEST_ID = 0L;

    /**
     * Represents the URL extension of the covidTest's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/covidTest/";

    /**
     * Represents the URL extension of the covidTest's API endpoint to get the latest.
     */
    public final static String URL_EXTENSION_LATEST = URL_EXTENSION + "latest";

    /**
     * Represents the employee's id key that used in ART's API call.
     */
    public final static String EMP_ID_KEY = "empId";

    /**
     * Represents the company's id key that used in ART's API call.
     */
    public final static String COMP_ID_KEY = "compId";

    /**
     * Create an ART with necessary fields filled to use for testing.
     * 
     * @return the ART to use for testing.
     */
    public static Art createArt() {
        Art art = new Art();
        art.setId(TEST_ID);
        art.setDateOfTest(LocalDate.ofEpochDay(1L));
        art.setExpiryDate(art.getDateOfTest().plusDays(7));
        art.setResult(false);
        art.setCompany(TestCompany.createCompany());
        art.setEmployee(TestEmployee.createEmployee());
        return art;
    }

    /**
     * Create an ART DTO with necessary fields filled to use for testing.
     * 
     * @return the ART DTO to use for testing.
     */
    public static ArtDTO createArtDTO() {
        ArtDTO artDTO = new ArtDTO();
        Art art = createArt();
        artDTO.setId(art.getId());
        artDTO.setDateOfTest(art.getDateOfTest());
        artDTO.setExpiryDate(art.getExpiryDate());
        artDTO.setResult(art.isResult());
        artDTO.setCompany(art.getCompany().getUEN());
        artDTO.setEmployeeName(art.getEmployee().getName());
        artDTO.setEmployeeWP(art.getEmployee().getWorkPermitNumber());
        return artDTO;
    }
}

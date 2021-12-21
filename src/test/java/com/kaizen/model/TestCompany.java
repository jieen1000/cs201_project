package com.kaizen.model;

import com.kaizen.model.entity.Company;

/**
 * {@code TestCompany} is a mock class that stored configurations needed to do
 * testing related to {@link Company} and {@link CompanyController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
public class TestCompany {
    /**
     * Represents the company's id used for testing.
     */
    public final static String TEST_ID = "0123456789";

    /**
     * Represents the URL extension of the company's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/companies/";

    /**
     * Represents the URL extension of the company's API endpoint to get company by specific name.
     */
    public final static String URL_EXTENSION_SPECIFIC = URL_EXTENSION + "specific";

    /**
     * Represents the company's name key that used in ART's API call.
     */
    public final static String NAME_KEY = "name";

    /**
     * Create a company with necessary fields filled to use for testing.
     * 
     * @return the company to use for testing.
     */
    public static Company createCompany() {
        Company company = new Company();
        company.setUEN(TEST_ID);
        company.setName("Name");
        return company;
    }
}

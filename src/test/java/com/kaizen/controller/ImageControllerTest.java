package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.client.AmazonClient;
import com.kaizen.model.*;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.image.ImageService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@code ImageControllerTest} is a test class to do unit testing on
 * {@link ImageController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { ImageController.class })
@ExtendWith(SpringExtension.class)
class ImageControllerTest {
    /**
     * The image's controller used for testing.
     */
    @Autowired
    private ImageController imageController;

    /**
     * The mocked image's service used for testing.
     */
    @MockBean
    private ImageService imageService;

    /**
     * The mocked employee's service used for testing.
     */
    @MockBean
    private EmployeeService employeeService;

    /**
     * The mocked amazon's client used for testing.
     */
    @MockBean
    private AmazonClient amazonClient;

    /**
     * {@code uploadFile_MissingFile_ExpectBadRequest} is a test on
     * {@link ImageController#uploadFile(String, MultipartFile)} to verify if the
     * method will return Http Status Bad Request(400) when the specific image DTO
     * is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void uploadFile_MissingFile_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(TestImage.URL_EXTENSION)
                .param(TestImage.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(imageController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code uploadFile_MissingEmpId_ExpectBadRequest} is a test on
     * {@link ImageController#uploadFile(String, MultipartFile)} to verify if the
     * method will return Http Status Bad Request(400) when the specific employee's
     * id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void uploadFile_MissingEmpId_ExpectBadRequest() throws Exception {
        MockMultipartFile file = TestImage.createMockMultipartFile();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(TestImage.URL_EXTENSION)
                .file(file);

        MockMvcBuilders.standaloneSetup(imageController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code uploadFile_InvalidImageDTO_ExpectBadRequest} is a test on
     * {@link ImageController#uploadFile(String, MultipartFile)} to verify if the
     * method will return Http Status Bad Request(400) when the specific file is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void uploadFile_InvalidFile_ExpectBadRequest() throws Exception {
        MockMultipartFile file = TestImage.createInvalidMockMultipartFile();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(TestImage.URL_EXTENSION)
                .file(file).param(TestImage.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(imageController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code uploadFile_MissingEmployee_ExpectNotFound} is a test on
     * {@link ImageController#uploadFile(String, MultipartFile)} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and return Http
     * Status Not Found(404) when the employee with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void uploadFile_MissingEmployee_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockMultipartFile file = TestImage.createMockMultipartFile();
        when(employeeService.getEmployee(employee.getWorkPermitNumber())).thenReturn(employee);
        when(imageService.addImage(any(Image.class))).thenReturn(TestImage.createImage());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(TestImage.URL_EXTENSION)
                .file(file).param(TestImage.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(imageController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(employeeService).getEmployee(employee.getWorkPermitNumber());
        verify(imageService).addImage(any(Image.class));
    }

    /**
     * {@code uploadFile_New_ExpectCreatedSaved} is a test on
     * {@link ImageController#uploadFile(String, MultipartFile))} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and
     * {@link ImageService#addImage(Image)} and return the created specific image
     * with Http Status Created(201) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void uploadFile_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockMultipartFile file = TestImage.createMockMultipartFile();
        when(employeeService.getEmployee(employee.getWorkPermitNumber())).thenReturn(employee);
        when(imageService.addImage(any(Image.class))).thenReturn(TestImage.createImage());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(TestImage.URL_EXTENSION)
                .file(file).param(TestImage.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(imageController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(employeeService).getEmployee(employee.getWorkPermitNumber());
        verify(imageService).addImage(any(Image.class));
    }
}

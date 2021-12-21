package com.kaizen.controller;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.image.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpStatus;

import com.kaizen.client.AmazonClient;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;

/**
 *
 * @author Tan Jie En
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/api/employees/image")
public class ImageController {

    /**
     * The AmazonClient used to connect service to AmazonS3 for image persistence.
     */
    private AmazonClient amazonClient;

    /**
     * The Image's service used to do the image's logic for Image.
     */
    private final ImageService imageService;

    /**
     * The employee's service used to do the business's logic for employee.
     */
    private final EmployeeService employeeService;

    /**
     * Create an Image's controller with the specific Image's service and employee's
     * service.
     * 
     * @param employeeService the employee's service used by the application.
     * @param imageService    the image's service used by the application.
     * @param amazonclient    the amazonclient service used by the application.
     */
    @Autowired
    ImageController(AmazonClient amazonClient, ImageService imageService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.imageService = imageService;
        this.amazonClient = amazonClient;
    }

    /**
     * Create an Image's controller with the specific Image's service and employee's
     * service.
     * 
     * @param empid the employee id used by the application.
     * @param file  the image file sent through by the application.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(params = { "empId" })
    public void uploadFile(@RequestParam String empId, @RequestPart(value = "file") MultipartFile file)
            throws NullPointerException {
        String imageURL = this.amazonClient.uploadFile(file);
        Employee emp = employeeService.getEmployee(empId);
        Image image = new Image(imageURL, emp);
        imageService.addImage(image);
        System.out.println(imageURL);
    }

    // @DeleteMapping(params = { "empId" })
    // public void deleteFile(@RequestParam String empId)
    // throws NullValueException, ObjectNotExistsException, NumberFormatException {
    // Employee emp = employeeService.getEmployee(empId);
    // Long imageId = Long.parseLong(imageService.getImageId(emp));
    // imageService.deleteImage(imageId);
    // // delete based on URL
    // this.amazonClient.deleteFileFromS3Bucket(imageService.getProfileImageURL(emp));
    // }

}
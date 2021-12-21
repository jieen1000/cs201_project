package com.kaizen.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * {@code TestJsonConverter} is a mock class that stored configurations needed
 * to do testing that required conversion between object and json.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
public class TestJsonConverter {
    /**
     * Represents the object mapper used for testing that registered
     * {@link JavaTimeModule}.
     */
    public final static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    /**
     * Represents the message converter used for testing.
     */
    public final static MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
    /**
     * Set the message converter's object mapper to object mapper used by this class.
     */
    static {
        messageConverter.setObjectMapper(objectMapper);
    }

    /**
     * Get the string of the specific value.
     * 
     * @param value the value to convert into string
     * @throws JsonProcessingException if any proccessing exceptions occurs.
     * @return a string of the specific value
     */
    public static String writeValueAsString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}

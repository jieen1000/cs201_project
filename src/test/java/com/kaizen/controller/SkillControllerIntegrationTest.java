package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.*;
import com.kaizen.model.entity.Skill;
import com.kaizen.repository.SkillRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code SkillControllerIntegrationTest} is a test class to do integration
 * testing from {@link SkillController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SkillControllerIntegrationTest {
    /**
     * The skill's controller used for testing.
     */
    @Autowired
    private SkillController skillController;

    /**
     * The skill's repository used for testing.
     */
    @Autowired
    private SkillRepository skillRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database
     */
    @AfterEach
    public void tearDown() {
        skillRepository.deleteAll();
    }

    /**
     * {@code getSkills_Found_ExpectOKFound} is a test on
     * {@link SkillController#getSkills()} to verify if the method will return the
     * list of all skills with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkills_Found_ExpectOKFound() throws Exception {
        List<Skill> skills = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestSkill.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(skillController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skills)));
    }

    /**
     * {@code getSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#getSkill(String)} to verify if the method will return
     * Http Status Not Found(404) when the specific skill is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkill_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getSkill_Found_ExpectOKFound} is a test on
     * {@link SkillController#getSkill(String)} to verify if the method will return
     * the skill with the specific id with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkill_Found_ExpectOKFound() throws Exception {
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestSkill.URL_EXTENSION + skill.getSkill());

        MockMvcBuilders.standaloneSetup(skillController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));
    }

    /**
     * {@code addSkill_SameID_ExpectConflict} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will
     * return Http Status Conflict(409) when a company with the same id already
     * exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_SameID_ExpectConflict() throws Exception {
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(skill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * {@code addSkill_New_ExpectCreatedSaved} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will
     * return the created specific skill with Http Status Created(201) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_New_ExpectCreatedSaved() throws Exception {
        Skill skill = TestSkill.createSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(skill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));
    }

    /**
     * {@code updateSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will return Http Status Not Found(404) when the skill with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_NotFound_ExpectNotFound() throws Exception {
        Skill skill = TestSkill.createSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + skill.getSkill()).content(TestJsonConverter.writeValueAsString(skill))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateSkill_Updated_ExpectOKUpdated} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will return the updated specific skill with Http Status OK(200) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_Updated_ExpectOKUpdated() throws Exception {
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        String task = skill.getTask();
        skill.setTask("Blah blah");
        skillRepository.save(skill);
        skill.setTask(task);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + skill.getSkill()).content(TestJsonConverter.writeValueAsString(skill))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));
    }

    /**
     * {@code deleteSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#deleteSkill(String)} to verify if the method will
     * return Http Status Not Found(404) when the skill with the specific id is not
     * found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteSkill_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteSkill_Deleted_ExpectOK} is a test on
     * {@link SkillController#deleteSkill(String)} to verify if the method will
     * return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteSkill_Deleted_ExpectOK() throws Exception {
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestSkill.URL_EXTENSION + skill.getSkill());

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
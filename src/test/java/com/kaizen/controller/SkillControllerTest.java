package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.entity.Skill;
import com.kaizen.service.skill.SkillService;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code SkillControllerTest} is a test class to do unit testing on
 * {@link SkillController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
@ContextConfiguration(classes = { SkillController.class })
@ExtendWith(SpringExtension.class)
class SkillControllerTest {
    /**
     * The skill's controller used for testing.
     */
    @Autowired
    private SkillController skillController;

    /**
     * The mocked skill's service used for testing.
     */
    @MockBean
    private SkillService skillService;

    /**
     * {@code getSkills_Found_ExpectOKFound} is a test on
     * {@link SkillController#getSkills()} to verify if the method will call
     * {@link SkillService#listSkills()} and return the list of all skills with Http
     * Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkills_Found_ExpectOKFound() throws Exception {
        List<Skill> skills = new ArrayList<>();
        when(skillService.listSkills()).thenReturn(skills);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestSkill.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skills)));

        verify(skillService).listSkills();
    }

    /**
     * {@code getSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#getSkill(String)} to verify if the method will call
     * {@link SkillService#getSkill(String)} and return Http Status Not Found(404)
     * when the specific skill is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkill_NotFound_ExpectNotFound() throws Exception {
        when(skillService.getSkill(TestSkill.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(skillService).getSkill(TestSkill.TEST_ID);
    }

    /**
     * {@code getSkill_Found_ExpectOKFound} is a test on
     * {@link SkillController#getSkill(String)} to verify if the method will call
     * {@link SkillService#getSkill(String)} and return the skill with the specific
     * id with Http Status OK(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getSkill_Found_ExpectOKFound() throws Exception {
        Skill skill = TestSkill.createSkill();
        when(skillService.getSkill(skill.getSkill())).thenReturn(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestSkill.URL_EXTENSION + skill.getSkill());

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));

        verify(skillService).getSkill(skill.getSkill());
    }

    /**
     * {@code addSkill_Missing_ExpectBadRequest} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will
     * return Http Status Bad Request(400) when the specific skill is missing in the
     * call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_Missing_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addSkill_InvalidSkill_ExpectBadRequest} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will
     * return Http Status Bad Request(400) when the specific skill is invalid in the
     * call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_InvalidSkill_ExpectBadRequest() throws Exception {
        Skill skill = TestSkill.createSkill();
        skill.setTask(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(skill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addSkill_SameID_ExpectConflict} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will call
     * {@link SkillService#addSkill(Skill)} and return Http Status Conflict(409)
     * when a skill with the same id already exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_SameID_ExpectConflict() throws Exception {
        Skill skill = TestSkill.createSkill();
        when(skillService.addSkill(skill)).thenThrow(new ObjectExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(skill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(skillService).addSkill(skill);
    }

    /**
     * {@code addSkill_New_ExpectCreatedSaved} is a test on
     * {@link SkillController#createSkill(Skill)} to verify if the method will call
     * {@link SkillService#addSkill(Skill)} and return the created specific skill
     * with Http Status Created(201) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addSkill_New_ExpectCreatedSaved() throws Exception {
        Skill skill = TestSkill.createSkill();
        when(skillService.addSkill(skill)).thenReturn(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(skill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));

        verify(skillService).addSkill(skill);
    }

    /**
     * {@code updateSkill_MissingSkill_ExpectBadRequest} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will return Http Status Bad Request(400) when the specific skill is missing
     * in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_MissingSkill_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateSkill_InvalidSkill_ExpectBadRequest} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will return Http Status Bad Request(400) when the specific skill is invalid
     * in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_InvalidSkill_ExpectBadRequest() throws Exception {
        Skill skill = TestSkill.createSkill();
        skill.setTask(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + skill.getSkill()).content(TestJsonConverter.writeValueAsString(skill))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will call {@link SkillService#updateSkill(String, Skill)} and return Http
     * Status Not Found(404) when the skill with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_NotFound_ExpectNotFound() throws Exception {
        Skill skill = TestSkill.createSkill();
        when(skillService.updateSkill(skill.getSkill(), skill)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + skill.getSkill()).content(TestJsonConverter.writeValueAsString(skill))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(skillService).updateSkill(skill.getSkill(), skill);
    }

    /**
     * {@code updateSkill_Updated_ExpectOKUpdated} is a test on
     * {@link SkillController#updateSkill(String, Skill)} to verify if the method
     * will call {@link SkillService#updateSkill(String, Skill)} and return the
     * updated specific skill with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateSkill_Updated_ExpectOKUpdated() throws Exception {
        Skill skill = TestSkill.createSkill();
        when(skillService.updateSkill(skill.getSkill(), skill)).thenReturn(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestSkill.URL_EXTENSION + skill.getSkill()).content(TestJsonConverter.writeValueAsString(skill))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(skill)));

        verify(skillService).updateSkill(skill.getSkill(), skill);
    }

    /**
     * {@code deleteSkill_NotFound_ExpectNotFound} is a test on
     * {@link SkillController#deleteSkill(String)} to verify if the method will call
     * {@link SkillService#deleteSkill(String)} and return Http Status Not
     * Found(404) when the skill with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteSkill_NotFound_ExpectNotFound() throws Exception {
        doThrow(new ObjectNotExistsException()).when(skillService).deleteSkill(TestSkill.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(skillService).deleteSkill(TestSkill.TEST_ID);
    }

    /**
     * {@code deleteSkill_Deleted_ExpectOK} is a test on
     * {@link SkillController#deleteSkill(String)} to verify if the method will call
     * {@link SkillService#deleteSkill(String)} and return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteSkill_Deleted_ExpectOK() throws Exception {
        doNothing().when(skillService).deleteSkill(TestSkill.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestSkill.URL_EXTENSION + TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(skillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(skillService).deleteSkill(TestSkill.TEST_ID);
    }
}

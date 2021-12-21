package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestSkill;
import com.kaizen.model.entity.Skill;
import com.kaizen.repository.SkillRepository;
import com.kaizen.service.skill.SkillService;
import com.kaizen.service.skill.SkillServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code SkillServiceTest} is a test class to do unit testing on
 * {@link SkillService} using {@link SkillServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
@ContextConfiguration(classes = { SkillServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class SkillServiceTest {
    /**
     * The mocked skill's repository used for testing.
     */
    @MockBean
    private SkillRepository skillRepository;

    /**
     * The skill's service used for testing.
     */
    @Autowired
    private SkillService skillService;

    /**
     * {@code listSkills_Found_ReturnFound} is a test on
     * {@link SkillService#listCompanies()} to verify if the method will call
     * {@link SkillRepository#findAll()} and return the list of all skills.
     */
    @Test
    void listSkills_Found_ReturnFound() {
        List<Skill> skills = new ArrayList<>();
        when(skillRepository.findAll()).thenReturn(skills);

        List<Skill> foundSkills = skillService.listSkills();

        assertSame(skills, foundSkills);
        verify(skillRepository).findAll();
    }

    /**
     * {@code getSkill_Null_ThrowNullValueException} is a test on
     * {@link SkillService#getSkill(String)} to verify if the method will throw
     * {@link NullValueException} when the specific id is null.
     */
    @Test
    void getSkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            skillService.getSkill(null);
        });
    }

    /**
     * {@code getSkill_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link SkillService#getSkill(String)} to verify if the method will call
     * {@link SkillRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the skill with the specific id does
     * not exists.
     */
    @Test
    void getSkill_NotFound_ThrowObjectNotExistsException() {
        when(skillRepository.findById(TestSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            skillService.getSkill(TestSkill.TEST_ID);
        });

        verify(skillRepository).findById(TestSkill.TEST_ID);
    }

    /**
     * {@code getSkill_Found_ReturnFound} is a test on
     * {@link SkillService#getSkill(String)} to verify if the method will call
     * {@link SkillRepository#findById(String)} and return the skill with the
     * specific id.
     */
    @Test
    void getSkill_Found_ReturnFound() {
        Skill skill = TestSkill.createSkill();
        when(skillRepository.findById(skill.getSkill())).thenReturn(Optional.of(skill));

        Skill foundSkill = skillService.getSkill(skill.getSkill());

        assertSame(skill, foundSkill);
        verify(skillRepository).findById(skill.getSkill());
    }

    /**
     * {@code addSkill_Null_ThrowNullValueException} is a test on
     * {@link SkillService#addSkill(Skill)} to verify if the method will throw
     * {@link NullValueException} when the specific skill is null.
     */
    @Test
    void addSkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            skillService.addSkill(null);
        });
    }

    /**
     * {@code addSkill_SameID_ThrowObjectExistsException} is a test on
     * {@link SkillService#addSkill(Skill)} to verify if the method will call
     * {@link SkillRepository#findById(String)} and throw
     * {@link ObjectExistsException} when the id of the specific skill exists.
     */
    @Test
    void addSkill_SameID_ThrowObjectExistsException() {
        Skill skill = TestSkill.createSkill();
        when(skillRepository.findById(skill.getSkill())).thenReturn(Optional.of(skill));

        assertThrows(ObjectExistsException.class, () -> {
            skillService.addSkill(skill);
        });

        verify(skillRepository).findById(skill.getSkill());
    }

    /**
     * {@code addSkill_New_ReturnSaved} is a test on
     * {@link SkillService#addSkill(Skill)} to verify if the method will call
     * {@link SkillRepository#findById(String)} and
     * {@link SkillRepository#save(Skill)} and save and return the specific
     * skill.
     */
    @Test
    void addSkill_New_ReturnSaved() {
        Skill skill = TestSkill.createSkill();
        when(skillRepository.findById(skill.getSkill())).thenReturn(Optional.empty());
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill savedSkill = skillService.addSkill(skill);

        assertSame(skill, savedSkill);
        verify(skillRepository).findById(skill.getSkill());
        verify(skillRepository).save(skill);
    }

    /**
     * {@code updateSkill_NullId_ThrowNullValueException} is a test on
     * {@link SkillService#updateSkill(String, Skill)} to verify if the method
     * will throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void updateSkill_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            skillService.updateSkill(null, TestSkill.createSkill());
        });
    }

    /**
     * {@code updateSkill_NullUpdatedSkill_ThrowNullValueException} is a test on
     * {@link SkillService#updateSkill(String, Skill)} to verify if the method
     * will throw {@link NullValueException} when the specific skill is null
     *.
     */
    @Test
    void updateSkill_NullUpdatedSkill_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            skillService.updateSkill(TestSkill.TEST_ID, null);
        });
    }

    /**
     * {@code updateSkill_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link SkillService#updateSkill(String, Skill)} to verify if the method
     * will call {@link SkillRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the skill with the specific id does
     * not exists.
     */
    @Test
    void updateSkill_NotFound_ThrowObjectNotExistsException() {
        when(skillRepository.findById(TestSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            skillService.updateSkill(TestSkill.TEST_ID, TestSkill.createSkill());
        });

        verify(skillRepository).findById(TestSkill.TEST_ID);
    }

    /**
     * {@code updateSkill_Updated_ReturnUpdated} is a test on
     * {@link SkillService#updateSkill(String, Skill)} to verify if the method
     * will call {@link SkillRepository#findById(String)} and
     * {@link SkillRepository#save(Skill)} and save and return the specific
     * skill.
     */
    @Test
    void updateSkill_Updated_ReturnUpdated() {
        Skill skill = TestSkill.createSkill();
        when(skillRepository.findById(skill.getSkill())).thenReturn(Optional.of(skill));
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill updatedSkill = skillService.updateSkill(skill.getSkill(), skill);

        assertSame(skill, updatedSkill);
        verify(skillRepository).findById(skill.getSkill());
        verify(skillRepository).save(skill);
    }

    /**
     * {@code deleteSkill_NullId_ThrowNullValueException} is a test on
     * {@link SkillService#deleteSkill(String)} to verify if the method will
     * throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteSkill_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            skillService.deleteSkill(null);
        });
    }

    /**
     * {@code deleteSkill_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link SkillService#deleteSkill(String)} to verify if the method will
     * call {@link SkillRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the skill with the specific id does
     * not exists.
     */
    @Test
    void deleteSkill_NotFound_ThrowObjectNotExistsException() {
        when(skillRepository.findById(TestSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            skillService.deleteSkill(TestSkill.TEST_ID);
        });

        verify(skillRepository).findById(TestSkill.TEST_ID);
    }

    /**
     * {@code deleteSkill_Deleted} is a test on
     * {@link SkillService#deleteSkill(String)} to verify if the method will
     * call {@link SkillRepository#findById(String)} and
     * {@link SkillRepository#deleteById(String)} and delete the skill with
     * specific id.
     */
    @Test
    void deleteSkill_Deleted() {
        when(skillRepository.findById(TestSkill.TEST_ID)).thenReturn(Optional.of(TestSkill.createSkill()));
        doNothing().when(skillRepository).deleteById(TestSkill.TEST_ID);

        skillService.deleteSkill(TestSkill.TEST_ID);

        verify(skillRepository).findById(TestSkill.TEST_ID);
        verify(skillRepository).deleteById(TestSkill.TEST_ID);
    }
}

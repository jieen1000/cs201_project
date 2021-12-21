package com.kaizen.service.skill;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Skill;
import com.kaizen.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@code SkillServiceImpl} is an implementation of {@code SkillService}.
 *
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@Service
public class SkillServiceImpl implements SkillService {
    /**
     * The skill's repository that stored skills.
     */
    private final SkillRepository skillRepository;

    /**
     * Represents the simple name of the Skill's class.
     */
    private final String SKILL_SIMPLE_NAME;

    /**
     * Create a skill's service implementation with the specific skill's repository
     * and set the {@code SKILL_SIMPLE_NAME} with the simple name of the Skill's
     * class
     * 
     * @param skillRepository the skill's repository used by the application.
     */
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
        SKILL_SIMPLE_NAME = Skill.class.getSimpleName();
    }

    /**
     * Get all skills stored in the repository.
     * 
     * @return the list of all skills.
     */
    @Override
    public List<Skill> listSkills() {
        return skillRepository.findAll();
    }

    /**
     * Get the skill with the specific id from the repository.
     * 
     * @param id the id of the skill.
     * @exception NullValueException       If the id of the skill is null.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     * @return the skill with that id.
     */
    @Override
    public Skill getSkill(String id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        return skillRepository.findById(id).orElseThrow(() -> new ObjectNotExistsException(SKILL_SIMPLE_NAME, id));
    }

    /**
     * Create the specific skill in the repository.
     * 
     * @param skill the skill to create.
     * @exception NullValueException    If the id of the skill is null.
     * @exception ObjectExistsException If the skill is in the repository.
     * @return the created skill.
     */
    @Override
    public Skill addSkill(Skill skill) throws NullValueException, ObjectExistsException {
        validateSkillNotNull(skill);
        validateIdNotNull(skill.getSkill());
        if (skillRepository.findById(skill.getSkill()).isPresent()) {
            throw new ObjectExistsException(SKILL_SIMPLE_NAME, skill.getSkill());
        }
        return skillRepository.save(skill);
    }

    /**
     * Update the skill with the specific id and skill in the repository.
     * 
     * @param id    the id of the skill to update.
     * @param skill the skill to update.
     * @exception NullValueException       If the id of the skill is null or the
     *                                     skill is null.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     * @return the updated skill.
     */
    @Override
    public Skill updateSkill(String id, Skill skill) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateSkillNotNull(skill);
        validateSkillExists(id);
        return skillRepository.save(skill);
    }

    /**
     * Delete the skill with the specific id in the repository.
     * 
     * @param id the id of the skill to delete.
     * @exception NullValueException       If the id of the skill is null.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     */
    @Override
    public void deleteSkill(String id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateSkillExists(id);
        skillRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the skill to validate.
     * @exception NullValueException If the id of the skill is null.
     */
    private void validateIdNotNull(String id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(SKILL_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific skill is not null.
     * 
     * @param skill the skill to validate.
     * @exception NullValueException If the skill is null.
     */
    private void validateSkillNotNull(Skill skill) throws NullValueException {
        if (skill == null) {
            throw new NullValueException(SKILL_SIMPLE_NAME);
        }
    }

    /**
     * Validate the skill with the specific id is in the repository.
     * 
     * @param id the id of the skill to validate.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     */
    private void validateSkillExists(String id) throws ObjectNotExistsException {
        if (skillRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(SKILL_SIMPLE_NAME, id);
        }
    }
}
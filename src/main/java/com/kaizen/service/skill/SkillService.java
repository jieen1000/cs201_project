package com.kaizen.service.skill;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Skill;

import java.util.List;

/**
 * {@code SkillService} captures what are needed for business's logic for skill.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
public interface SkillService {
    /**
     * Get all skills stored in the repository.
     * 
     * @return the list of all skills.
     */
    List<Skill> listSkills();

    /**
     * Get the skill with the specific id from the repository.
     * 
     * @param id the id of the skill.
     * @exception NullValueException       If the id of the skill is null.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     * @return the skill with that id.
     */
    Skill getSkill(String skill) throws NullValueException, ObjectNotExistsException;

    /**
     * Create the specific skill in the repository.
     * 
     * @param skill the skill to create.
     * @exception NullValueException    If the id of the skill is null.
     * @exception ObjectExistsException If the skill is in the repository.
     * @return the created skill.
     */
    Skill addSkill(Skill skill) throws NullValueException, ObjectExistsException;

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
    Skill updateSkill(String id, Skill skill) throws NullValueException, ObjectNotExistsException;

    /**
     * Delete the skill with the specific id in the repository.
     * 
     * @param id the id of the skill to delete.
     * @exception NullValueException       If the id of the skill is null.
     * @exception ObjectNotExistsException If the skill is not in the repository.
     */
    void deleteSkill(String id) throws NullValueException, ObjectNotExistsException;

}
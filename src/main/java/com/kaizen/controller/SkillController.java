package com.kaizen.controller;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Skill;
import com.kaizen.service.skill.SkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

/**
 * {@code SkillController} is a rest controller for skill.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    /**
     * The skill's service used to do the business's logic for skill.
     */
    private final SkillService skillService;

    /**
     * Create a skill's controller with the specific skill's service.
     * 
     * @param skillService the skill's service used by the application.
     */
    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Get all skills through skill's service.
     * 
     * @return the list of all skills.
     */
    @GetMapping
    public List<Skill> getSkills() {
        return skillService.listSkills();
    }

    /**
     * Get the skill with the specific id through skill's service.
     * 
     * @param id the id of the skill.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the skill with that id.
     */
    @GetMapping("/{id}")
    public Skill getSkill(@PathVariable String id) throws NullValueException, ObjectNotExistsException {
        return skillService.getSkill(id);
    }

    /**
     * Create the specific skill through skill's service.
     * 
     * @param skill the skill to create.
     * @exception NullValueException    If the id of the company is null.
     * @exception ObjectExistsException If the company is in the repository.
     * @return the created skill.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Skill createSkill(@Valid @RequestBody Skill skill) throws NullValueException, ObjectExistsException {
        return skillService.addSkill(skill);
    }

    /**
     * Update the skill with the specific id and skill through skill's service.
     * 
     * @param id    the id of the skill to update.
     * @param skill the skill to update.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the updated skill.
     */
    @PutMapping("/{id}")
    public Skill updateSkill(@PathVariable String id, @Valid @RequestBody Skill skill)
            throws NullValueException, ObjectNotExistsException {
        return skillService.updateSkill(id, skill);
    }

    /**
     * Delete the skill with the specific id through skill's service.
     * 
     * @param id the id of the skill to delete.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @DeleteMapping("/{id}")
    public void deleteSkill(@PathVariable String id) throws NullValueException, ObjectNotExistsException {
        skillService.deleteSkill(id);
    }

}
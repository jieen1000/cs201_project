package com.kaizen.model;

import com.kaizen.model.entity.Skill;

/**
 * {@code TestSkill} is a mock class that stored configurations needed to do
 * testing related to {@link Skill} and {@link SkillController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
public class TestSkill {
    /**
     * Represents the skill's id used for testing.
     */
    public final static String TEST_ID = "Test Skill";

    /**
     * Represents the URL extension of the skill's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/skills/";

    /**
     * Create a skill with necessary fields filled to use for testing.
     * 
     * @return the skill to use for testing.
     */
    public static Skill createSkill() {
        Skill skill = new Skill();
        skill.setSkill(TEST_ID);
        skill.setTask("Task");
        return skill;
    }
}

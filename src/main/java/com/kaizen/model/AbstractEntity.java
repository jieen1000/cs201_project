package com.kaizen.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

/**
 * {@code AbstractEntity} that could be a base entity for any entity that
 * required to be serializable.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-15
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {
    /**
     * Represents the abstarct entity's id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Represents the abstarct entity's version.
     */
    @Version
    private int version;
}

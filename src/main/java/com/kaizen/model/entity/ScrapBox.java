package com.kaizen.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.kaizen.model.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a scrap box.
 *
 * @author Teo Keng Swee
 * @author Bryan Tan
 * @author Tan Jie En
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScrapBox extends AbstractEntity {
    /**
     * Represents the scrap box's header.
     */
    @Column(name = "header")
    private String header;

    /**
     * Represents the scrap box's url.
     */
    @Column(name = "url")
    private String url;
}
package com.kaizen.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a news.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-22
 */
@NoArgsConstructor
@Entity
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "news")
public class News extends ScrapBox {
    /**
     * Represents the news's excerpt.
     */
    @Column(name = "excerpt", length = 1000)
    private String excerpt;

    /**
     * Represents the news's image's url.
     */
    @Column(name = "image_url", length = 400)
    private String imageUrl;

    /**
     * Create a news with the specific header, url, excerpt and image's url.
     * 
     * @param header   the news's header.
     * @param url      the news's url.
     * @param excerpt  the news's excerpt.
     * @param imageUrl the news's image's url.
     */
    public News(String header, String url, String excerpt, String imageUrl) {
        super(header, url);
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
    }
}
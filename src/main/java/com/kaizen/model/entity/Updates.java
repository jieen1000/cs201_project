package com.kaizen.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an updates.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@NoArgsConstructor
@Entity
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "updates")
public class Updates extends ScrapBox {
    /**
     * Represents the updates's date.
     */
    @Column(name = "date")
    private String date; // maybe change to DateTime format?

    /**
     * Create an updates with the specific header, url and date.
     * 
     * @param header the updates's header.
     * @param url    the updates's url.
     * @param date   the updates's date.
     */
    public Updates(String header, String url, String date) {
        super(header, url);
        this.date = date;
    }
}
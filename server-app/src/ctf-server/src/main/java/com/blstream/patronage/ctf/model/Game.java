package com.blstream.patronage.ctf.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;



@Document
public class Game {

    private String name;
    private String descryption;
    private Date time_start;
    private Long duration;
    private Integer points_max;
    private Integer players_max;
    private Localization localization;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescryption() {
        return descryption;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public Date getTime_start() {
        return time_start;
    }

    public void setTime_start(Date time_start) {
        this.time_start = time_start;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getPoints_max() {
        return points_max;
    }

    public void setPoints_max(Integer points_max) {
        this.points_max = points_max;
    }

    public Integer getPlayers_max() {
        return players_max;
    }

    public void setPlayers_max(Integer players_max) {
        this.players_max = players_max;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }
}

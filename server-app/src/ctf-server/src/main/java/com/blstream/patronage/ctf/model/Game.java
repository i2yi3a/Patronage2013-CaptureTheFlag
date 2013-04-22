package com.blstream.patronage.ctf.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Game implements BaseModel<String> {

    @Id
    private String id;
    private String name;
    private String description;
    private Date timeStart;
    private Long duration;
    private Integer pointsMax;
    private Integer playersMax;
    private Localization localization;
    private GameStatusType status;
    private List<String> players;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getPointsMax() {
        return pointsMax;
    }

    public void setPointsMax(Integer pointsMax) {
        this.pointsMax = pointsMax;
    }

    public Integer getPlayersMax() {
        return playersMax;
    }

    public void setPlayersMax(Integer playersMax) {
        this.playersMax = playersMax;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public GameStatusType getStatus() {
        return status;
    }

    public void setStatus(GameStatusType status) {
        this.status = status;
    }

    public List<String> getPlayers() {
        if (players == null)
            players = new ArrayList<String>();
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

package com.blstream.patronage.ctf.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game implements BaseModel<String> {

    @Id
    private String id;

    private String name;
    private String description;

    @JsonProperty("time_start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;

    private Long duration;

    @JsonProperty("points_max")
    private Integer pointsMax;

    @JsonProperty("players_max")
    private Integer playersMax;

    private Localization localization;


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
}

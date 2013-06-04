package com.blstream.patronage.ctf.web.ui;


import com.blstream.patronage.ctf.model.GameStatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.oval.constraint.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: mkr
 * Date: 4/22/13
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GameUI extends BaseUI<String> {


    @NotEmpty(message = "Id cannot be empty")
    private String id;

    @NotNull(message = "Game name cannot be Null")
    @NotEmpty(message = "Game name cannot be empty")
    @Length(min = 5, max = 60, message = "Name between 5 and 60 characters")
    private String name;

    private String description;

    @NotNull(message = "TimeStart cannot be Null")
    @NotEmpty(message = "time start cannot be empty")
    @JsonProperty(value = "time_start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;

    @NotNull(message = "Duration cannot be Null")
    @NotEmpty(message = "Duration cannot be empty")
    private Long duration;

    @JsonProperty("points_max")
    private Integer pointsMax;

    @JsonProperty("players_max")
    private Integer playersMax;

    @NotNull(message = "Localization cannot be Null")
    @NotEmpty(message = "Localization cannot be empty")
    @AssertValid(message = "Name, latLng and radius in Localization cannot be Null or Empty")
    private LocalizationUI localization;

    private GameStatusType status;

    private List<String> players;

    private String owner;

    @NotNull(message = "redTeamBase cannot be Null")
    @NotEmpty(message = "redTeamBase cannot be Empty")
    @AssertValid(message = "Name and latLng in RedTeamBase cannot be Null or Empty")
    @JsonProperty("red_team_base")
    private TeamBaseUI redTeamBase;

    @NotNull(message = "blueTeamBase cannot be Null")
    @NotEmpty(message = "blueTeamBase cannot be Empty")
    @AssertValid(message = "Name and latLng in BlueTeamBase cannot be Null or Empty")
    @JsonProperty("blue_team_base")
    private TeamBaseUI blueTeamBase;


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

    public LocalizationUI getLocalization() {
        return localization;
    }

    public void setLocalization(LocalizationUI localization) {
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

    public TeamBaseUI getRedTeamBase() {
        return redTeamBase;
    }

    public void setRedTeamBase(TeamBaseUI redTeamBase) {
        this.redTeamBase = redTeamBase;
    }

    public TeamBaseUI getBlueTeamBase() {
        return blueTeamBase;
    }

    public void setBlueTeamBase(TeamBaseUI blueTeamBase) {
        this.blueTeamBase = blueTeamBase;
    }

    @JsonProperty("players_count")
    public Integer getPlayersCount() {
        return players != null ? players.size() : 0;
    }
}

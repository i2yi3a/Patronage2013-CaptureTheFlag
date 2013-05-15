package com.blstream.patronage.ctf.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamBase {

    @JsonProperty(required = false, value = "red_Team_Base")
    private BlueTeamBase blueTeamBase;

    @JsonProperty(required = false, value = "blue_Team_Base")
    private RedTeamBase redTeamBase;

    public BlueTeamBase getBlueTeamBase() {
        return blueTeamBase;
    }

    public void setBlueTeamBase(BlueTeamBase blueTeamBase) {
        this.blueTeamBase = blueTeamBase;
    }

    public RedTeamBase getRedTeamBase() {
        return redTeamBase;
    }

    public void setRedTeamBase(RedTeamBase redTeamBase) {
        this.redTeamBase = redTeamBase;
    }
}

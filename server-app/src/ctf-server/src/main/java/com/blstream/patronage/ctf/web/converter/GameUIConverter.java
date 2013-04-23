package com.blstream.patronage.ctf.web.converter;

import com.blstream.patronage.ctf.model.Game;
import com.blstream.patronage.ctf.web.ui.GameUI;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * User: mkr
 * Date: 4/22/13
 */
@Named("gameUIConverter")
public class GameUIConverter extends BaseUIConverter<GameUI, Game, String> {

    @Override
    public Game convert(GameUI source) {
        if (source == null)
            return null;

        Game target = new Game();

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setTimeStart(source.getTimeStart());
        target.setDuration(source.getDuration());
        target.setPointsMax(source.getPointsMax());
        target.setPlayersMax(source.getPlayersMax());
        target.setLocalization(source.getLocalization());
        target.setStatus(source.getStatus());
        target.setPlayers(source.getPlayers());
        target.setOwner(source.getOwner());

        return target;
    }

    @Override
    public GameUI convert(Game source) {
        GameUI target = new GameUI();

        if (source != null) {
            target.setId(source.getId());
            target.setName(source.getName());
            target.setDescription(source.getDescription());
            target.setTimeStart(source.getTimeStart());
            target.setDuration(source.getDuration());
            target.setPointsMax(source.getPointsMax());
            target.setPlayersMax(source.getPlayersMax());
            target.setLocalization(source.getLocalization());
            target.setStatus(source.getStatus());
            target.setPlayers(source.getPlayers());
            target.setOwner(source.getOwner());
        }

        return target;
    }

    public List<GameUI> convertModelList(List<Game> sourceList) {
        List<GameUI> targetList = new ArrayList<GameUI>();

        if (sourceList != null) {
            for (Game source : sourceList) {
                GameUI target = new GameUI();
                target.setId(source.getId());
                target.setName(source.getName());
//                target.setDescription(source.getDescription());
//                target.setTimeStart(source.getTimeStart());
//                target.setDuration(source.getDuration());
//                target.setPointsMax(source.getPointsMax());
//                target.setPlayersMax(source.getPlayersMax());
//                target.setLocalization(source.getLocalization());
                target.setStatus(source.getStatus());
//                target.setPlayers(source.getPlayers());
                target.setOwner(source.getOwner());

                if (target != null)
                    targetList.add(target);
            }
        }

        return targetList;
    }
}

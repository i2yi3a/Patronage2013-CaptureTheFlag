package com.blstream.patronage.ctf.web.converter;

import com.blstream.patronage.ctf.model.Player;
import com.blstream.patronage.ctf.web.ui.PlayerUI;

import javax.inject.Named;

/**
 * User: mkr
 * Date: 4/22/13
 */
@Named("playerUIConverter")
public class PlayerUIConverter extends BaseUIConverter<PlayerUI, Player, String> {

    @Override
    public Player convert(PlayerUI source) {
        // not implemented yet
        return null;
    }

    @Override
    public PlayerUI convert(Player source) {
        PlayerUI target = new PlayerUI();

        if (source != null) {
            target.setUsername(source.getPortalUser().getUsername());
        }

        return target;
    }
}

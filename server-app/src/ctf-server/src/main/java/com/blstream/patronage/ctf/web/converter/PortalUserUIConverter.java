package com.blstream.patronage.ctf.web.converter;

import com.blstream.patronage.ctf.model.PortalUser;
import com.blstream.patronage.ctf.web.ui.PortalUserUI;

import javax.inject.Named;

/**
 * User: mkr
 * Date: 4/22/13
 */
@Named("portalUserUIConverter")
public class PortalUserUIConverter extends BaseUIConverter<PortalUserUI, PortalUser, String> {

    @Override
    public PortalUser convert(PortalUserUI source) {
        if (source == null)
            return null;

        PortalUser target = new PortalUser();

        target.setUsername(source.getUsername());
        target.setPassword(source.getPassword());
        target.setAccountNonExpired(source.isAccountNonExpired());
        target.setAccountNonLocked(source.isAccountNonLocked());
        target.setCredentialsNonExpired(source.isCredentialsNonExpired());
        target.setEnabled(source.isEnabled());

        return target;
    }

    @Override
    public PortalUserUI convert(PortalUser source) {
        PortalUserUI target = new PortalUserUI();

        if (source != null) {
            target.setUsername(source.getUsername());
            // target.setPassword(source.getPassword()); !!! password hash is omitted!!!
            target.setAccountNonExpired(source.isAccountNonExpired());
            target.setAccountNonLocked(source.isAccountNonLocked());
            target.setCredentialsNonExpired(source.isCredentialsNonExpired());
            target.setEnabled(source.isEnabled());
        }

        return target;
    }
}

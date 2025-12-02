package com.cafe.coffeejava.config.security;

import com.cafe.coffeejava.config.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public JwtUser getSignedUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return myUserDetails == null ? null : myUserDetails.getJwtUser();
    }

    public long getSignedUserId() {
        return getSignedUser().getSignedUserId();
    }

}

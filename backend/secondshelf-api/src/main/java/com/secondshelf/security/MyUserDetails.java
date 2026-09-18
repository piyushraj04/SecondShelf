package com.secondshelf.security;

import com.secondshelf.entity.User;
import com.secondshelf.enums.UserStatus;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private User user;
    public MyUserDetails(User user){
        this.user = user;
    }
    //Security stuffs
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        //Spring Security expects the authority:ROLE_SELLER
        //Convert the enum to String using .name():
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public String getUsername(){
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //So Spring Security can automatically consider an inactive user disabled.
        return user.getUserStatus()==UserStatus.ACTIVE;
    }



}

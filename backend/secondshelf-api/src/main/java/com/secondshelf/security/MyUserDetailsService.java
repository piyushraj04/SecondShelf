package com.secondshelf.security;

import com.secondshelf.entity.User;
import com.secondshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new MyUserDetails(user);
    }


}

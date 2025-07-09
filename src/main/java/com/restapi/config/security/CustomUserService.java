package com.restapi.config.security;

import com.restapi.entity.User;
import com.restapi.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;

//        Hard Coded for username and password
//       UserDetails user = User.builder().username("bipin")
//               .password("{noop}bipin123")
//               .roles("USER").build();
//
//       if(user.getUsername().equals(username)){
//           return user;
//       }
//       else{
//           throw new UsernameNotFoundException("User Not Found with usrname" + username);
//       }

    }
}

package com.example.todolist.security;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SecurityUserDetail implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityUserDetail(UserRepository userRepository){
        this.userRepository = userRepository;
        User user = new User();
        user.setName("Test");
        user.setUserName("Test");
        user.setPassword("{noop}test");
        user.setAdmin(false);
        User admin = new User();
        admin.setName("Admin");
        admin.setUserName("Admin");
        admin.setPassword("{noop}admin");
        admin.setAdmin(true);
        userRepository.save(user);
        userRepository.save(admin);
    }


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User dbUser = Optional.ofNullable(userRepository.findByUserName(username))
                .orElseThrow(() -> new UsernameNotFoundException("User wasn't found in our System"));
        List<GrantedAuthority> admin = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        List<GrantedAuthority> user = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(
                dbUser.getUserName(),
                dbUser.getPassword(),
                dbUser.isAdmin() ? admin : user
        );
    }

}

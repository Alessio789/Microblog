package it.marconivr.microblog.security;

import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.IUserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * MyUserDetailService
 *
 * @author Alessio Trentin
 */
@Service
public class UserPrincipalDetailService implements UserDetailsService {

    @Autowired
    private IUserRepo repo;

    public UserDetails loadUserByUsername(String username){

        User user = repo.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("User " + username + " not found");

        return new UserPrincipal(user);
    }

}

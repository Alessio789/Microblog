package it.marconivr.microblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SecurityConfiguration
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/Microblog/Registration").permitAll()
                .antMatchers("/Microblog/Login").permitAll()
                .antMatchers("/Microblog/Logout").permitAll()
                .antMatchers("/Microblog/Posts").permitAll()
                .antMatchers("/h2").permitAll()
                .antMatchers("/h2/l**").permitAll()
                .antMatchers("/Microblog/Posts/AddPost").hasRole("ADMIN")
                .antMatchers("/Microblog/Posts/PostAdded").hasRole("ADMIN")
                .antMatchers("/Microblog/Posts/AddComment/{postId}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/Microblog/Posts/AddComment/SaveComment/{postId}").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/Microblog");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
package it.marconivr.microblog.security;

import it.marconivr.microblog.repos.IUserRepo;
import it.marconivr.microblog.rest.jwt.JWTAuthenticationFilter;
import it.marconivr.microblog.rest.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security Configuration
 *
 * @author Alessio Trentin
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserPrincipalDetailService userPrincipalDetailService;

    @Autowired
    protected IUserRepo repo;

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

    @Order(1)
    @Configuration
    public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
            jwtAuthenticationFilter.setFilterProcessesUrl("/Microblog/rest/login");

            http.
                    antMatcher("/Microblog/rest/**")
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JWTAuthorizationFilter(authenticationManager(), repo))
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/Microblog/rest/v1/users").permitAll()
                    .antMatchers("/Microblog/rest/v1/users").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/Microblog/rest/v1/posts").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/Microblog/rest/v1/posts/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/Microblog/rest/v1/posts/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/Microblog/rest/v1/comments").authenticated()
                    .antMatchers(HttpMethod.PUT, "/Microblog/rest/v1/comments/{id}").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/Microblog/rest/v1/comments/{id}").authenticated()
                    .antMatchers("/Microblog/rest/v1/**").permitAll();
        }
    }

    @Order(2)
    @Configuration
    public class MCVConfiguration extends WebSecurityConfigurerAdapter {

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
    }
}
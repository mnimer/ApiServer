package apiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by mnimer on 5/14/14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
            .withUser("user")  // #1
            .password("password")
            .roles("USER")
            .and()
            .withUser("admin") // #2
            .password("password")
            .roles("ADMIN", "USER");
    }


    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/pdf/**").permitAll() // #4
            .antMatchers("/admin/**").hasRole("ADMIN") // #6
            .antMatchers("/images/**").hasRole("USER") // #6
            .anyRequest().authenticated() // 7
            .and().httpBasic();
    }

}

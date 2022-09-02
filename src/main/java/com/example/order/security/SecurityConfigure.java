package com.example.order.security;

//import com.example.order.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyUserDetailsService myUserDetailService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;



    public static final String AUTHORITIES_CLAIM_NAME = "roles";

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws  Exception
    {
//
//        httpSecurity.csrf().disable();
//        httpSecurity.authorizeRequests().antMatchers("/authenticate").permitAll();
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        httpSecurity.authorizeRequests().antMatchers("/users/**").hasAnyAuthority("user").
//                antMatchers("/inventory","/orders","/discountSchemes","/vouchers").hasAnyAuthority("admin")
//                .anyRequest().authenticated().and().formLogin().and().logout().permitAll();
//
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/authenticate","/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/users").hasAnyAuthority("ADMIN")
//                .antMatchers("/products").hasAnyAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET,"/orders","/discounts","/vouchers").hasAnyAuthority("USER","ADMIN")
//                .antMatchers(HttpMethod.POST,"/users/**").hasAnyAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate","/users","/roles","/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws  Exception
    {
        return super.authenticationManager();
    }


}
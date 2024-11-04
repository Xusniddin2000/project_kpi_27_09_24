package com.example.project_kpi_27_09_24.config;


import com.example.project_kpi_27_09_24.security.AuthService;
import com.example.project_kpi_27_09_24.security.api.JwtAuthenticationEntryPoint;
import com.example.project_kpi_27_09_24.security.api.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig {

      @Autowired
      private JwtAuthenticationEntryPoint   jwtAuthenticationEntryPoint;

      @Autowired
      private AuthService authService;


    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewal(){
        StrictHttpFirewall firewall=new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return   firewall;
    }

    @Bean
    public JwtAuthenticationFilter   jwtAuthenticationFilter  (){   return   new JwtAuthenticationFilter(); }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder(){  return    new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity  httpSecurity)  throws   Exception{
        AuthenticationManagerBuilder authenticationManager=httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManager.userDetailsService(authService).passwordEncoder(passwordEncoder());
        return   authenticationManager.build();

    }


    @Bean
    protected SecurityFilterChain   filterChain(HttpSecurity  httpSecurity)  throws  Exception{

            httpSecurity.csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
                    .antMatchers("/api/admin/**").access("hasRole('ADMIN')")
                    .antMatchers("/api/user/**").access("hasAnyRole('USER','ADMIN')")
                    .antMatchers("/api/moderator/**").access("hasAnyRole('MODERATOR')")
                    .anyRequest().authenticated();
             return  httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }


     @Bean
     public WebSecurityCustomizer   webSecurityCustomizer(){
              return  (web)->{
                        web.ignoring().antMatchers("/assets/**");
                         web.httpFirewall(allowUrlEncodedSlashHttpFirewal());
              };
     }
}

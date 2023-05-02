package fontys.sem3.school.config;

import fontys.sem3.school.config.filter.CustomAuthenticationFilter;
import fontys.sem3.school.config.filter.CustomAuthorisationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/user/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //http.authorizeRequests().antMatchers("/user/login","/user/register","/user/token/refresh", "/properties","/properties/{id}","/properties/Approved/{city}").permitAll();

        http.authorizeRequests().antMatchers("/user/login","/user/registerUser","/user/token/refresh", "/properties","/properties/{id}","/properties/Approved/{city}").permitAll();
        http.authorizeRequests().antMatchers(PUT, "/properties/UpdateProperty/{id}").hasAnyAuthority("ROLE_ADMIN");
    //    http.authorizeRequests().antMatchers(GET, "/properties/Unapproved/count").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/properties/Unapproved").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/user/admin").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().antMatchers(GET, "/user/check").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(POST, "/properties/CreateProperty").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(GET, "/user/MyPersonalDetails").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(PUT, "/user/{id}").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(GET, "/properties/AllProperties/user").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(GET, "/properties/Unapproved/user").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(GET, "/properties/Approved/user").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(DELETE, "/properties/{id}").hasAnyAuthority("ROLE_DEFAULT");
        http.authorizeRequests().antMatchers(POST, "/properties/DeleteProperty/{id}").hasAnyAuthority("ROLE_DEFAULT");

        //New requests based on new arch
        http.authorizeRequests().antMatchers(GET, "/user/MyPersonalDetails").hasAnyAuthority("ROLE_DEFAULT");

        http.authorizeRequests().antMatchers("/ws/**").permitAll();
        //http.authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/","http://localhost:8080/" )); // <-- you may change "*"
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept", "Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}

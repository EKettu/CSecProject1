package sec.project.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // A8-Cross-Site Request Forgery (CSRF): 
        //Thymeleaf automatically adds a CSRF token for a session, 
        // but this disables the token enabling CSRF attacks.
        http.csrf().disable();

        //http.headers().frameOptions().sameOrigin(); 
        //Previous line of code would
        //help to prevent clickjacking attacks...
        
        http.authorizeRequests()
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/form").permitAll()
                .anyRequest().permitAll();
        http.formLogin()
                .permitAll()
                .defaultSuccessUrl("/form", true);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //A9-Using Components with Known Vulnerablities:
    //I have switched BCryptPasswordEncoder to StandardPasswordEncoder,
    //which is an older and less secure version. 
    // See http://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/crypto/password/StandardPasswordEncoder.html
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}

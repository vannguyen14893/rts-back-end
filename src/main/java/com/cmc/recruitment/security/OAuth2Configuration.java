//package com.cmc.recruitment.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import com.cmc.recruitment.security.config.CustomAuthenticationEntryPoint;
//import com.cmc.recruitment.security.config.CustomLogoutSuccessHandler;
//
//
//@Configuration
//public class OAuth2Configuration {
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Autowired
//        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//
//        @Autowired
//        private CustomLogoutSuccessHandler customLogoutSuccessHandler;
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//
//            http   
//                    .exceptionHandling()
//                    .authenticationEntryPoint(customAuthenticationEntryPoint)
//                    .and()
//                    .logout()
//                    .logoutUrl("/oauth/logout")
//                    .logoutSuccessHandler(customLogoutSuccessHandler)
//                    .and()
//                    .csrf()
//                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                    .disable()
//                    .headers()
//                    .frameOptions().disable().and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers(HttpMethod.GET, "/resources/public/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/resources/static/**").permitAll()
//                    .antMatchers("/login").permitAll()
//                    .antMatchers("/reset-password").permitAll()
//                    .antMatchers("users/**").authenticated()
//                    .antMatchers("/upload/**").authenticated()
//                    .antMatchers("/skill/**").authenticated()
//                    .antMatchers("/cvs/**").hasAnyRole("HR_MANAGER","HR_MEMBER")
//                    .antMatchers("/cv-status/**").hasAnyRole("HR_MANAGER","HR_MEMBER")
//                    .antMatchers("/secure/**").authenticated()
//                    ;
//
//        }
//
//    }
//
//}

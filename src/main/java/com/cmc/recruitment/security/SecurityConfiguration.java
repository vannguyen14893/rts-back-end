package com.cmc.recruitment.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//
//	}

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//        web
//        .ignoring()
//        .antMatchers("/h2console/**")
//        .antMatchers("/api/register")
//        .antMatchers("/api/activate")
//        .antMatchers("/api/lostpassword")
//        .antMatchers("/api/reset-password")
//        .antMatchers("/user/reset-password")
//        .antMatchers("/reset-password")
//        .antMatchers("/login")
//        .antMatchers("/api/hello/");
//       
//    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.cors().and().csrf().disable();
		// http.csrf().ignoringAntMatchers("/api/**");
		http.cors().and().csrf().disable()
				// .exceptionHandling()
				// .authenticationEntryPoint(customAuthenticationEntryPoint)
				// .and()
				//.logout().logoutUrl("/oauth/logout")
				// .logoutSuccessHandler(customLogoutSuccessHandler)
				
				// .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
				//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/resources/public/**").permitAll()
				.antMatchers(HttpMethod.GET, "/resources/static/**").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/reset-password").permitAll().antMatchers("users/**").authenticated()
				.antMatchers("/upload/**").authenticated().antMatchers("/skill/**").authenticated()
				.antMatchers("/cvs/**").hasAnyRole("HR_MANAGER", "HR_MEMBER").antMatchers("/cv-status/**")
				.hasAnyRole("HR_MANAGER", "HR_MEMBER").antMatchers("/secure/**").authenticated()
				.antMatchers(HttpMethod.POST,"/api/check-user/ams").permitAll().and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
				//.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

//    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
//    private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
//        @Override
//        protected MethodSecurityExpressionHandler createExpressionHandler() {
//            return new OAuth2MethodSecurityExpressionHandler();
//        }
//
//    }
}
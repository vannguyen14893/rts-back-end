package com.cmc.recruitment.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//	private static final String ENV_OAUTH = "authentication.oauth.";
//	private static final String PROP_CLIENTID = "clientid";
//	private static final String PROP_SECRET = "secret";
//	private static final String PROP_TOKEN_VALIDITY_SECONDS = "tokenValidityInSeconds";
//
//	private RelaxedPropertyResolver propertyResolver;

	@Autowired
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.authenticationManager(authenticationManager);
		
		 DefaultTokenServices tokenServices = new DefaultTokenServices();
         tokenServices.setTokenStore(endpoints.getTokenStore());
         tokenServices.setSupportRefreshToken(true);
         tokenServices.setReuseRefreshToken(false);
         tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
         tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
         tokenServices.setAccessTokenValiditySeconds(100000);
         tokenServices.setRefreshTokenValiditySeconds(150000);
//         tokenServices.refreshAccessToken(refreshTokenValue, tokenRequest);
         
         endpoints.tokenServices(tokenServices); 
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
		
//		.inMemory().withClient(propertyResolver.getProperty(PROP_CLIENTID)).scopes("read", "write")
//		.authorities(Authorities.ROLE_ADMIN.name(), Authorities.ROLE_APPROVER.name(),  Authorities.ROLE_CREATER.name())
//		.authorizedGrantTypes("password", "refresh_token").secret(propertyResolver.getProperty(PROP_SECRET))
//		.accessTokenValiditySeconds(
//				propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDS, Integer.class, 10));

	}

//	@Override
//	public void setEnvironment(Environment environment) {
//		this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
//	}

}

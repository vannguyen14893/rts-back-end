package com.cmc.recruitment.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.repository.UserRepository;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final static String TOKEN_HEADER = "authorization";

	@Autowired
	private UserRepository userdao;

	@Override

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String username = httpRequest.getHeader(TOKEN_HEADER);

		if (username == null) {
			chain.doFilter(request, response);
		} else {
			username = username.split(" ")[1];
			String returnVanlue = httpRequest.getHeader(TOKEN_HEADER).split(" ")[1];
			com.cmc.recruitment.entity.User user = userdao.loadUserByUsername(returnVanlue);
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            
			for (Role role : user.getRoleCollection()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
				grantedAuthorities.add(grantedAuthority);

			}
			UserDetails userDetail = new User(user.getUsername(), user.getPassword(), grantedAuthorities);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
					null, userDetail.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}

	}
}
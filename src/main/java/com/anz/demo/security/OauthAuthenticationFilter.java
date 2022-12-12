package com.anz.demo.security;

import com.anz.demo.model.User;
import com.anz.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OauthAuthenticationFilter extends GenericFilterBean {

    private final UserService userService;

    public OauthAuthenticationFilter(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        final String authHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, servletResponse);
            return;
        }
        if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            try {
                Jwt jwt = (Jwt)authenticationToken.getPrincipal();
                final String subject = jwt.getSubject();
                final String[] subs = subject.split("\\|");
                final String userId = subs.length > 1 ? subs[1] : subs[0];
                Assert.hasText(userId, "user id can not be empty");
                loadUserAndSetSecurityContext(userService, request, userId);
                filterChain.doFilter(request, servletResponse);
            } catch (Exception e) {
               log.error("Unable to load user details, clearing security context");
               SecurityContextHolder.clearContext();
            }
        }
    }

    private void loadUserAndSetSecurityContext(UserService userService, HttpServletRequest request, String userId) {
        Optional<User> userOptional = userService.getUser(userId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userOptional.ifPresent(user -> {
            user.getRoles().forEach(role -> {
                final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                authorities.add(authority);
            });

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getId(), "n/a", authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
        });
    }
}

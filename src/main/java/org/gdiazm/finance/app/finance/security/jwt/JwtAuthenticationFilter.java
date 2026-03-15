package org.gdiazm.finance.app.finance.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.security.CustomUserDetails;
import org.gdiazm.finance.app.finance.security.jwt.service.JwtService;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    //private final UserRepository userRepository;
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (header == null || !header.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(AUTHORIZATION_HEADER_PREFIX.length());
        UUID userId = jwtService.getUserIdFromToken(token);
        //User user = userRepository.findById(userId).orElse(null);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           CustomUserDetails userDetails = new CustomUserDetails(userId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}

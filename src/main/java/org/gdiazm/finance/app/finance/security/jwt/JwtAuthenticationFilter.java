package org.gdiazm.finance.app.finance.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.security.CustomUserDetails;
import org.gdiazm.finance.app.finance.security.jwt.service.JwtService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
        UUID userId;
        //User user = userRepository.findById(userId).orElse(null);

        try {
            userId = jwtService.getUserIdFromToken(token);
        }catch (ExpiredJwtException e){
        sendUnauthorized(response, "Token has expired");
        return;
        }catch (Exception e){
            sendUnauthorized(response, "Invalid token");
            return;
        }


        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           CustomUserDetails userDetails = new CustomUserDetails(userId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }


    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("""
                {"status": 401, "error": "Unauthorized", "message": "%s"}
                """.formatted(message));
    }
}

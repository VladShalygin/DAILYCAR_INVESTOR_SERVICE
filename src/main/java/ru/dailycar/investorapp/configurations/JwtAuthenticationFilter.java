package ru.dailycar.investorapp.configurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.dailycar.investorapp.entities.CustomUserDetails;
import ru.dailycar.investorapp.services.JWTService;
import ru.dailycar.investorapp.services.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        try {
            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer")) {
                filterChain.doFilter(request, response);
                return;
            }

            System.out.println(request);

            jwt = authHeader.substring(7);
            username = jwtService.extractUserName(jwt);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication((token));
                    SecurityContextHolder.setContext(securityContext);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

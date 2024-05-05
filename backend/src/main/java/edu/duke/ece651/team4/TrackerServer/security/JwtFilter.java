// package edu.duke.ece651.team4.TrackerServer.security;

// import java.io.IOException;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JwtFilter extends OncePerRequestFilter {

//     private final JwtTokenProvider jwtTokenProvider;

//     public JwtFilter(JwtTokenProvider jwtTokenProvider) {
//         this.jwtTokenProvider = jwtTokenProvider;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String token = jwtTokenProvider.resolveToken(request);
//         if (token != null && jwtTokenProvider.validateToken(token)) {
//             Authentication auth = jwtTokenProvider.getAuthentication(token);
//             if (auth != null) {
//                 SecurityContextHolder.getContext().setAuthentication(auth);
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }

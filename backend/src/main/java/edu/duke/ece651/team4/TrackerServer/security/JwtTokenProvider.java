// package edu.duke.ece651.team4.TrackerServer.security;

// import java.util.Base64;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;

// import edu.duke.ece651.team4.TrackerServer.service.TokenService;
// import edu.duke.ece651.team4.TrackerServer.service.UserService;
// import jakarta.annotation.PostConstruct;

// public class JwtTokenProvider {
//     @Value("${jwt.secret}")
//     private String secretKey;

//     @Autowired
//     private TokenService tokenService; // 替换成你自己的 TokenService

//     @PostConstruct
//     protected void init() {
//         secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//     }

//     public String createToken(UserDetails userDetails) {
//         return TokenService.generateToken(userDetails); 
//     }

//     public Authentication getAuthentication(String token) {
//         String username = tokenService.getUsernameFromToken(token); // 使用你自己的方法从 Token 中获取用户名
//         UserDetails userDetails = UserService.loadUserByUsername(username); // 使用你自己的方法根据用户名加载用户信息
//         return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//     }

//     public String getUsername(String token) {
//         return yourTokenService.getUsernameFromToken(token); // 使用你自己的方法从 Token 中获取用户名
//     }


// }

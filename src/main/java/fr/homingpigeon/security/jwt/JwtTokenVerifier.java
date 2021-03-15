package fr.homingpigeon.security.jwt;

import fr.homingpigeon.common.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

public class JwtTokenVerifier extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String token = authorizationHeader.replace("Bearer ", "");
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(
                    JwtConfig.secretKey()).build()
                                        .parseClaimsJws(token);


            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String,String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet =
                    authorities.stream().map(x -> new SimpleGrantedAuthority(x.get("authority")))
                       .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,simpleGrantedAuthoritySet);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(JwtException e){
            throw new IllegalStateException("Token has been forged\n" + token);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

package graduation.design.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String jwtToken = "123456TeachingPlatform";

    public static String createToken(Integer userId,String account){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        if(account.startsWith("ad")){
            claims.put("role","admin");
        }else if(account.startsWith("as")){
            claims.put("role","assistant");
        }else if(account.startsWith("au")){
            claims.put("role","author");
        }else if(account.startsWith("t")){
            claims.put("role","teacher");
        }else if(account.startsWith("s")){
            claims.put("role","student");
        };
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

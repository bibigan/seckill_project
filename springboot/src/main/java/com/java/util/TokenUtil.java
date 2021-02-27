package com.java.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.java.pojo.Users;
import org.apache.commons.lang.StringUtils;
import java.util.Date;

public class TokenUtil {

    private static final long EXPIRE_TIME= 10*60*60*1000;
    private static final String TOKEN_SECRET="txdy";  //密钥盐

    private static String userName="";

    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String sign(Users user){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", user.getUser_name())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     * @param tokenValue
     * @return
     */
    public static boolean verify(String tokenValue){
        String token =getToken(tokenValue);
        System.out.println("5个字符后的token:"+token);
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("username: " + jwt.getClaim("username").asString());
            userName=jwt.getClaim("username").asString();
            System.out.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            System.out.println("验证不通过："+e);
            userName="";
            return false;
        }
    }
    public static String getToken(String v){
        if(!StringUtils.isEmpty(v)){
            String[] tokenStr=v.split(" ");
            return StringUtils.substring(v,5);
        }
        return "";
    }

    public static String getUserName() {
        return userName;
    }
}

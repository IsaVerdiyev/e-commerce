package ibar.task.ecommerce.demo.services;

import ibar.task.ecommerce.demo.domain.AuthenticationInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.secretkey}")
    String secretKey;

    @Value("${jwt.issuer}")
    String issuer;

    @Value("${jwt.expiration.hours}")
    Integer expirationHours;

    @Value("${jwt.remembered.expiration.hours}")
    Integer expirationHoursForRemembered;

    public void validateToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    public String generateToken(AuthenticationInfo authenticationInfo) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date issueDate = new Date();
        Date expirationDate = getExpirationDate(issueDate, authenticationInfo.getIsRemembered());
        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(issueDate)
                .setSubject(authenticationInfo.getMerchantName())
                .setIssuer(issuer)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, signingKey);
        String token = builder.compact();

        return token;
    }

    Date getExpirationDate(Date issueDate, Boolean isRemembered) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(issueDate);
        if (!isRemembered) {
            cal.add(Calendar.HOUR, expirationHours);
        } else {
            cal.add(Calendar.HOUR, expirationHoursForRemembered);
        }
        return cal.getTime();
    }
}

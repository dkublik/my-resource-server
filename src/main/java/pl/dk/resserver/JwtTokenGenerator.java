package pl.dk.resserver;

import io.jsonwebtoken.Jwts;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtTokenGenerator {

    public static final String AUDIENCE = "some Audience";
    public static final String ISSUER = "http://some-issuer.dk.com";
    public static final String SUBJECT = "some Subject";


    public static String generateToken(String id, Date issuedAt, Date expirationDate) throws Exception {
        String jwt = Jwts.builder()
                         .setAudience(AUDIENCE)
                         .setIssuedAt(issuedAt)
                         .setExpiration(expirationDate)
                         .setIssuer(ISSUER)
                         .setSubject(SUBJECT)
                         .setId(id)
                         .signWith(privateKey())
                         .compact();
        return jwt;
    }

    private static PrivateKey privateKey() throws Exception {
        InputStream inputStream = JwtTokenGenerator.class
            .getClassLoader().getResourceAsStream("rsakey.pem");
        String pk = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                                                             .generatePrivate(new PKCS8EncodedKeySpec(getKeySpec(pk)));
        return privateKey;
    }

    private static byte[] getKeySpec(String keyValue) {
        keyValue = keyValue.replace("-----BEGIN PRIVATE KEY-----", "")
                           .replace("-----END PRIVATE KEY-----", "")
                           .replace("\n", "");
        return Base64.getDecoder().decode(keyValue);
    }
}

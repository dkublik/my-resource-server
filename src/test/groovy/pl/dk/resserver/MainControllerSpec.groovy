package pl.dk.resserver

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

import java.text.SimpleDateFormat

import static java.time.Instant.now
import static java.time.temporal.ChronoUnit.MINUTES
import static java.util.UUID.randomUUID
import static org.springframework.http.HttpHeaders.AUTHORIZATION

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerSpec extends Specification {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);

    def setup() {
        format.setTimeZone(TimeZone.getTimeZone('UTC'))
    }

    @Autowired
    WebTestClient webTestClient

    def "should return 401 without a token"() {
        when:
        def response = webTestClient.get()
                .uri('/showJwt')
                .exchange()

        then:
        response
                .expectStatus()
                .isUnauthorized()
    }

    def "should return 401 when no token signature"() {
        given:
        String jwt = Jwts.builder()
                .setAudience('doesnt matter')
                .setIssuedAt(Date.from(now()))
                .setExpiration(Date.from(now().plus(5L, MINUTES)))
                .setIssuer('http://doesnt-matter.dk.com')
                .setSubject('doesnt matter')
                .setId(randomUUID().toString())
                .compact()

        when:
        def response = webTestClient.get()
                .uri('/showJwt')
                .header(AUTHORIZATION, "Bearer $jwt")
                .exchange()

        then:
        response
                .expectStatus()
                .isUnauthorized()
    }

    def "should parse and validate jwt token"() {
        given:
        Date issuedAt = Date.from(now())
        Date expirationDate = Date.from(now().plus(5L, MINUTES))
        String id = randomUUID().toString()
        String jwt = JwtTokenGenerator.generateToken(id, issuedAt, expirationDate)

        when:
        def response = webTestClient.get()
                .uri('/showJwt')
                .header(AUTHORIZATION, "Bearer $jwt")
                .exchange()

        then:
        response
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath('$').isEqualTo([
                    tokenValue: jwt,
                    issuedAt: format.format(issuedAt),
                    expiresAt: format.format(expirationDate),
                    headers: [
                            alg: 'RS256'
                    ],
                    claims: [
                            aud: [
                                    JwtTokenGenerator.AUDIENCE
                            ],
                            sub:  JwtTokenGenerator.SUBJECT,
                            iss: JwtTokenGenerator.ISSUER,
                            exp: format.format(expirationDate),
                            iat: format.format(issuedAt),
                            jti: id
                    ],
                    id: id,
                    subject: JwtTokenGenerator.SUBJECT,
                    issuer: JwtTokenGenerator.ISSUER,
                    audience: [
                            JwtTokenGenerator.AUDIENCE
                    ],
                    notBefore: null
            ])
    }
}

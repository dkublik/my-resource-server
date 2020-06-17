package pl.dk.resserver;

import io.jsonwebtoken.Jwts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class ResserverApplication {

	public static void main(String[] args) throws Exception {
		 printKey();

		SpringApplication.run(ResserverApplication.class, args);
	}

	private static void printKey() throws Exception {
		PrivateKey privateKey = privateKey2();

		Instant now = Instant.now();

		String jwt = Jwts.builder()
						 .setAudience("https://${yourOktaDomain}/oauth2/default/v1/token")
						 .setIssuedAt(Date.from(now))
						 .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
						 .setIssuer("http://dk.com")
						 .setSubject("dksubject")
						 .setId(UUID.randomUUID().toString())
						 .signWith(privateKey)
						 .compact();

		System.out.println(jwt);
	}

	private static PrivateKey privateKey2() throws Exception {
		String pk = "-----BEGIN PRIVATE KEY-----\n"
			+ "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpxd3LZ85FkOcs\n"
			+ "y2M+usyEe/3EpOTdyQ5O5xdFRgSbwJHShIxIF3y2CXuiKkChkXGbe3hRlgzcD0kJ\n"
			+ "KWqoXKjGFRhruMLOcasj5yKj2WaCthq7JsnuWwFsyFO0cw9+FRuTjbHRRh5O3zvH\n"
			+ "NrgCwVgsrMJ95SX004eKIkYdDF8TDKQ/JRiM0cNyiVx5ji//auqCneEdUGOWVEUA\n"
			+ "YwaQ0vpP0FA9MzXBivZvKDMOiGPDejxthAwmbWsm7dzthYYogPJ1IT6F7Gs8MNJs\n"
			+ "ITRZOXLPPDyuF1inUelsdhe3aNTyFZ1Ya/FTCi0SJxOezKUF7gava/S+7iemXSnR\n"
			+ "JpxRvGSbAgMBAAECggEAYljGtVLneWa7iqHKD9LgRYAwQ3Eng5KY/WLE7HTHbYr3\n"
			+ "DfyLU6zl32Nf6tohtJcOl127QX+By3Z2wG73GvtYWXT4gCELe3RgjksnzIyiTq0z\n"
			+ "07Y+z5Ljp5Mje8/IHb/diRbcTvFTwNSzPlzrzuTuJmVLsVEcxcHTzK4clw3kVYt3\n"
			+ "FH+C3ibpD8P5vTXwEC5Jm2W+aHjhvmUb5jA8/xqFKX+OrrQW1JcydMPOZckujRo0\n"
			+ "687WNl5sQS8aRtFFFqG8OW3ohKxasfVgO0n87HXmf6beFmPx/LYLIaqahfs3gKV5\n"
			+ "tbg/gNMdwV9G4lwWC0VLGD6xDc1EMjjkEilm10cpYQKBgQD4yRYAPhv/OE5oyKT6\n"
			+ "mQansHWIWEjIT5/NNRtg4V48LQjRfIhUZwbX30jTPDBpnKj+YhOpmLiYv2RlevjS\n"
			+ "DX0/AGKCHouJVWmL5k1hg1+NgVd1cZ+fWirhXRJ8JIogn++EEAInYe55OUNEBek6\n"
			+ "hkCJibIMjPs6r643PlYtHInKUQKBgQDwjVTN4Y5lR+XLHQVGZACKcwJ+Pgyb9fHB\n"
			+ "m6VcNYTgtqW7lhaLqgiZWUhstDLjprG6e9QNHoebW0u+f3gszb7Oh6hZmtG2Nkum\n"
			+ "0mP2ujrEio8poNIoSiJv0vIHxuy6zZz9zA7bi7V+76NcTElRAf2++69nG9tN562G\n"
			+ "MnVISZ2ZKwKBgHtNJkNOhQ+z92CFeQbeiubXVRkTMFde50OMO5qDd/H5G/K/ds3m\n"
			+ "FgUtm+ldXl0pkLQrJbWbhHnn+bk1/lYU62srJKd6vgt9sobsyJM4ykXWweLqQoN2\n"
			+ "6+Ov/jZa4b6TpeDdEkQ0jD22fLkc+N/Ro/DGtuDzipwmcADfO1S2XTBRAoGAI35X\n"
			+ "EwHDBwKyz2N60cYTploN9h6lsaV6hHy4y4BCGP4aOjGNJhJyrl3UJ+X21ExWURPQ\n"
			+ "WBc3Hx87b+JTExlQnBy8JGyJoP8l7nmAsnkMDs/7C8Nk/vjhV+qiN6+MYovQLEqd\n"
			+ "rptQrmghjI7dCozmqfVtDiO4GiijG/f6Ai6KiH0CgYEAs9k+y9e9GzmdHeg5ai0x\n"
			+ "k5n1MCHkSrei3hVU49sMcj3+aUo037OzHzF3fn7gcoCUguvJKbJVOkRGInw7GlFz\n"
			+ "vjHLhdcQ3TKyV5ymz+p9cVYLl0EnkYMEYnDt7Tc/wasN6Yt34JL6rZclaGCMdUQd\n"
			+ "aeak+/OYYsLSQso4Jpmjpqw=\n"
			+ "-----END PRIVATE KEY-----\n";
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

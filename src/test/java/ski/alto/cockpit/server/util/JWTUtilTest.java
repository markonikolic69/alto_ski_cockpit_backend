package ski.alto.cockpit.server.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

@SpringBootTest
public class JWTUtilTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	
	public static void decodeJWT(String token) {
		
        try {
            // Decode JWT without verifying signature
            DecodedJWT decodedJWT = JWT.decode(token);
            
            // Print JWT claims
            System.out.println("Header: " + decodedJWT.getHeader());
            System.out.println("Payload: " + decodedJWT.getPayload());
            System.out.println("Signature: " + decodedJWT.getSignature());
            
            System.out.println("Issuer: " + decodedJWT.getIssuer());
            System.out.println("Subject: " + decodedJWT.getSubject());
            System.out.println("Expiration Time: " + decodedJWT.getExpiresAt());
        } catch (JWTDecodeException exception) {
            // Invalid token
            System.err.println("Invalid JWT token: " + exception.getMessage());
        }
        
		
	}
	
	@Test
    void assetJWTToken() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MjgxNjY3NTAsInN1YiI6Mzk5NCwidHlwZSI6ImNvbnN1bWVyIn0.z02GQW4ROgdNlXucWTcnnT1xn78ojsPepxJQ3GirxK0";
		decodeJWT(token);
	}

}

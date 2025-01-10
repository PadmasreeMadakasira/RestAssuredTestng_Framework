package api;

import java.time.Instant;
import java.util.HashMap;

import Utils.ConfigLoader;
import io.restassured.http.ContentType;

//import com.spotify.oauth2.api.RestResource;
//import com.spotify.oauth2.utils.ConfigLoader;

import io.restassured.response.Response;
import sun.security.krb5.Config;

public class TokenManager
{
	 private static String access_token;
	    private static Instant expiry_time;

	    public static String getToken() {
	        try {
	            if (access_token == null || Instant.now().isAfter(expiry_time)) {
	                System.out.println("Renewing token....");
	                Response response = renewToken();
	                access_token = response.path("access_token");
	                int expiryDurationInSeconds = response.path("expires_in");
	                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
	            } else {
	                System.out.println("Token is good to use");
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("ABORT!! Failed to get the token");
	        }
	        return access_token;	
	    }
	public  static Response renewToken()  {
 
        HashMap<String, String> formParams = new HashMap<String, String>();
        
        formParams.put("client_id",    ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("grant_type",    ConfigLoader.getInstance().getGrantType());
        
        Response response=RestResource.postAccount(formParams);
        if(response.statusCode()!=200)
        {
        	throw new RuntimeException("Abort!!! Renew token failed");
        }
        return response;
	}
}
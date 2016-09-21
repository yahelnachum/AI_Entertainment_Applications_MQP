import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class OAuthCalculation {

	private static String imgRequestKey = "image_request[local]";
	private static String imgRequestValue = "en";
	
	private static String oConsumerKey = "oauth_consumer_key";
	private static String oConsumerValue = "PK2x9PRpXn-3BSAh6QRdpA";
	
	private static String oNonceKey = "oauth_nonce";
	private static String oNonceValue = "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg";
	
	private static String oSigMethodKey = "oauth_signature_method";
	private static String oSigMethodValue = "HMAC-SHA1";
	
	private static String oTimestampKey = "oauth_timestamp";
	private static String oTimestampValue = "1318622958";
	
	private static String oTokenKey = "oauth_token";
	private static String oTokenValue = "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb";
	
	private static String oVersionKey = "oauth_version";
	private static String oVersionValue = "1.0";
	
	private static ArrayList<OAuthPair> pairs = new ArrayList<OAuthPair>();
	
	public static String calculateOAuth() throws Exception{
		initializePairs();
		printPairs();

		String fullOAuth = createFullOAuth();
		System.out.println("Full OAuth before HMAC-SHA1: "+fullOAuth);
		String hashedOAuthSig = hmacSha1(fullOAuth, "MtqVwfOVA2U8iT33e7Hs7A");
		String oAuthRequest = createOAuthRequest(hashedOAuthSig);
		
		
		System.out.println("Full OAuth Authentication: "+oAuthRequest);
		
		return oAuthRequest;
	}
	
	private static String createOAuthRequest(String hashedOAuthSig) {
		StringBuilder sb = new StringBuilder();
		sb.append("OAuth ");
		for(int i = 0; i < pairs.size(); i++){
			if(pairs.get(i).getKey().compareTo(imgRequestKey) != 0){
				sb.append(pairs.get(i).getKey());
				sb.append("=\"");
				sb.append(pairs.get(i).getValue());
				sb.append("\", ");
			}
		}
		
		sb.append("oauth_signature=\"");
		sb.append(hashedOAuthSig);
		sb.append("\"");
		
		return sb.toString();
	}

	private static void printPairs() {
		for(int i = 0; i < pairs.size(); i++){
			System.out.printf("%25s = %50s\n", pairs.get(i).getKey(), pairs.get(i).getValue());
		}
	}

	public static String hmacSha1(String value, String key) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		byte[] result = mac.doFinal(value.getBytes());

		System.out.println("Full OAuth after HMAC-SHA1 : "+DatatypeConverter.printBase64Binary(result));
		return DatatypeConverter.printBase64Binary(result);
    }
	
	private static String createFullOAuth() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		
		Collections.sort(pairs);
		for(int i = 0; i < pairs.size(); i++){
			sb.append(pairs.get(i).getKey());
			sb.append("=");
			sb.append(pairs.get(i).getValue());
			
			if(i < pairs.size() - 1){
				sb.append("&");
			}
		}
		
		String URL = "POST&https://api.cloudsightapi.com/";
		String fullOAuth = URL + sb.toString();
		fullOAuth = URLEncoder.encode(fullOAuth, StandardCharsets.UTF_8.toString());
		
		return sb.toString();
	}

	private static void initializePairs(){
		pairs.add(new OAuthPair(imgRequestKey, imgRequestValue));
		pairs.add(new OAuthPair(oConsumerKey, oConsumerValue));
		pairs.add(new OAuthPair(oNonceKey, oNonceValue));
		pairs.add(new OAuthPair(oSigMethodKey, oSigMethodValue));
		pairs.add(new OAuthPair(oTimestampKey, oTimestampValue));
		pairs.add(new OAuthPair(oTokenKey, oTokenValue));
		pairs.add(new OAuthPair(oVersionKey, oVersionValue));
	}
}

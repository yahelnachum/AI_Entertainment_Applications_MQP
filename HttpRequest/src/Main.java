import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base32;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws Exception {
		Main.sendPostWatson();
		Main.sendPostCloudSight();
		Main.sendPostClarifai();
	}

	private static void sendPostClarifai() throws Exception  {
		
		// get access token
		String url1 = "https://api.clarifai.com/v1/token/";
		
		URL obj1 = new URL(url1);
		HttpsURLConnection con1 = (HttpsURLConnection) obj1.openConnection();

		//add reuqest header
		con1.setRequestMethod("POST");
		//con.setRequestProperty("Authorization:", " CloudSight PK2x9PRpXn-3BSAh6QRdpA");
		//con1.setRequestProperty("Authorization", "Bearer ulrTGPMGriGwsl1MpLJzgyphYKbAaE");//+OAuthCalculation.calculateOAuth());
		//con.setRequestProperty("OAuth", OAuthCalculation.calculateOAuth());
/*		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
		//con.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****1234567890");
		
		String urlParameters1 = "client_id=j7yHzbxOlue-Q4NkEXTIl1UHllT3_UerH8TLn2Cu&client_secret=dbNK8HdXVqGl4NbN-8U0v-KAJbPh40idRSvCd8vI&grant_type=client_credentials";
		//urlParameters += convertByteArrayToString(bArray);
		
		//urlParameters = URLEncoder.encode(urlParameters, StandardCharsets.UTF_8.toString());

		// Send post request
		con1.setDoOutput(true);
		DataOutputStream wr1 = new DataOutputStream(con1.getOutputStream());
		//wr.writeBytes("*****1234567890");
		wr1.writeBytes(urlParameters1);
		//wr.writeBytes("*****1234567890");
		//wr.writeBytes(DatatypeConverter.printBase64Binary(bArray));
		//wr.write(bArray);
		wr1.flush();
		wr1.close();

		
		int responseCode1 = con1.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url1);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode1);
		
		String accessToken = "";
		if(responseCode1 == 200){
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con1.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			System.out.println(response.toString());
			accessToken = (new JSONObject(response.toString())).get("access_token").toString();
			System.out.println(accessToken);
		}
		else{
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con1.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			System.out.println(response.toString());
		}
		
		// get tags
		
		String url = "https://api.clarifai.com/v1/tag";
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		String boundary = "------------------------aa2af2f6902e7855";
		
		String imagesToken = "unityWebcam.jpg";
		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		byte[] bArray = Files.readAllBytes(image.toPath());
		String str = "--"+boundary+"\r\n"+"Content-Disposition: form-data; name=\"encoded_data\"; filename=\"unityWebcam.jpg\""+"\r\n"+"Content-Type: image/jpeg"+"\r\n"+"\r\n"+"\r\n"+"--"+boundary+"--\r\n";
		System.out.println((str.length()+bArray.length));
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer "+accessToken);
		con.setRequestProperty("Content-Length", "" + (str.length()+bArray.length));
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
		
		
		
		/*FileInputStream iStream = new FileInputStream(image);
		byte[] bArray = new byte[(int) image.length()];
		iStream.read(bArray);
		iStream.close();*/
		
		System.out.printf("File Size      : %d\n",image.length());
		System.out.printf("Byte Array Size: %d\n",bArray.length);

		System.out.println();
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes("--"+boundary+"\r\n");
		wr.writeBytes("Content-Disposition: form-data; name=\"encoded_data\"; filename=\"unityWebcam.jpg\""+"\r\n");
		wr.writeBytes("Content-Type: image/jpeg"+"\r\n");
		wr.writeBytes("\r\n");
		wr.write(bArray);
		wr.writeBytes("\r\n");
		wr.writeBytes("--"+boundary+"--\r\n");
		wr.flush();
		wr.close();
		System.out.println("1");
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		System.out.println("2");
		if(responseCode == 200){
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			System.out.println("3");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				System.out.println("4");
			}
			in.close();
			System.out.println("5");
			//print result
			System.out.println(response.toString());
		}
		else{
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			System.out.println("6");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				System.out.println("7");
			}
			in.close();
			System.out.println("8");
			//print result
			System.out.println(response.toString());
		}
	}
	
	private static void sendPostCloudSight() throws Exception {
		
		
		String url = "https://api.cloudsightapi.com/image_requests";
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		String boundary = "------------------------aa2af2f6902e7855";
		
		String imagesToken = "unityWebcam.jpg";
		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		byte[] bArray = Files.readAllBytes(image.toPath());
		
		String str = "--"+boundary+"\r\n"+"Content-Disposition: form-data; name=\"image_request[locale]\"\r\n"+"Content-Type: text/plain"+"\r\n"+"\r\n"+"en"+"\r\n"+"--"+boundary+"\r\n"+"Content-Disposition: form-data; name=\"image_request[image]\"; filename=\"unityWebcam.jpg\"\r\n"+"Content-Type: image/jpeg"+"\r\n"+"\r\n"+"\r\n"+"--"+boundary+"--\r\n";
		System.out.println((str.length()+bArray.length));
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");
		con.setRequestProperty("Content-Length", "" + (str.length()+bArray.length));
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);

		System.out.printf("File Size      : %d\n",image.length());
		System.out.printf("Byte Array Size: %d\n",bArray.length);

		System.out.println();
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes("--"+boundary+"\r\n");
		wr.writeBytes("Content-Disposition: form-data; name=\"image_request[locale]\"\r\n");
		wr.writeBytes("Content-Type: text/plain"+"\r\n");
		wr.writeBytes("\r\n");
		wr.writeBytes("en");
		wr.writeBytes("\r\n");
		wr.writeBytes("--"+boundary+"\r\n");
		wr.writeBytes("Content-Disposition: form-data; name=\"image_request[image]\"; filename=\"unityWebcam.jpg\"\r\n");
		wr.writeBytes("Content-Type: image/jpeg"+"\r\n");
		wr.writeBytes("\r\n");
		wr.write(bArray);
		wr.writeBytes("\r\n");
		wr.writeBytes("--"+boundary+"--\r\n");
		wr.flush();
		wr.close();
		System.out.println("1");
		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		System.out.println("2");
		String token = "";
		if(responseCode == 200){
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			System.out.println("3");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				System.out.println("4");
			}
			in.close();
			System.out.println("5");
			//print result
			System.out.println(response.toString());
			token = (new JSONObject(response.toString())).get("token").toString();
		}
		else{
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			System.out.println("6");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				System.out.println("7");
			}
			in.close();
			System.out.println("8");
			//print result
			System.out.println(response.toString());
		}
		
		
		// figure out result of picture
		String status = "";
		String response = "";
		while(status.compareTo("completed") != 0){
			String url1 = "http://api.cloudsightapi.com/image_responses/"+token;
	
			URL obj1 = new URL(url1);
			HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();
	
			// optional default is GET
			con1.setRequestMethod("GET");
			con1.setRequestProperty("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");
			
			int responseCode1 = con1.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url1);
			System.out.println("Response Code : " + responseCode1);
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con1.getInputStream()));
			String inputLine;
			StringBuffer response1 = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response1.append(inputLine);
			}
			in.close();
	
			//print result
			System.out.println(response1.toString());
			
			status = (new JSONObject(response1.toString())).get("status").toString();
			response = response1.toString();
		}
		
		System.out.println(response);
	}

	// HTTP GET request
	private static void sendGet() throws Exception {

		String url = "http://www.google.com/search?q=mkyong";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

/*		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);*/

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	// HTTP POST request
	private static void sendPostWatson() throws Exception {

		String url = "https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=68afcccf311899e6f6cc6064de624901456c180a&version=2016-05-20";
		
		String imagesToken = "unityWebcam.jpg";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reqest header
		con.setRequestMethod("POST");

		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		
		FileInputStream iStream = new FileInputStream(image);
		byte[] bArray = new byte[(int) image.length()];
		iStream.read(bArray);
		iStream.close();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(bArray);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

	public static String convertByteArrayToString(byte[] array){
		StringBuilder sb = new StringBuilder();
		
		for(byte b: array){
			sb.append((char) b);
		}
		
		return sb.toString();
	}
	
	public static byte[] convertStringToByteArrayToString(String str){
		byte[] bytes = new byte[str.length()];
		
		for(int i = 0; i < str.length(); i++){
			bytes[i] = (byte) str.charAt(i);
		}
		
		return bytes;
	}
}

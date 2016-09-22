import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

public class Main {

	public static void main(String[] args) throws Exception {
		//Main.sendPostWatson();
		Main.sendPostCloudSight();
		//Main.sendPostClarifai();
	}

	private static void sendPostClarifai() throws Exception  {
		
		String url = "https://api.clarifai.com/v1/tag";
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		//con.setRequestProperty("Authorization:", " CloudSight PK2x9PRpXn-3BSAh6QRdpA");
		con.setRequestProperty("Authorization", "Bearer tOuvjjaXWL7Zuy9kIcOwGgpjiYBMQm");//+OAuthCalculation.calculateOAuth());
		//con.setRequestProperty("OAuth", OAuthCalculation.calculateOAuth());
/*		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
		//con.setRequestProperty("Content-Type", "multipart/form-data; boundary=*****1234567890");
		
		//String urlParameters = "form-data: name=\""+imagesString+"\"; file-name=\""+imagesToken+"\"\n\r";//imagesString+combineToken+imagesToken;//apiString+combineToken+apiToken;//+combineQuery+versionString+combineToken+versionToken+combineQuery+imagesString+combineToken+imagesToken;
		
		String imagesToken = "unityWebcam.jpg";
		String urlParameters = "encoded_data=";//@E:/Users/Yahel/Desktop/jesus/3entombm.jpg\n\r";
		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		
		/*FileInputStream iStream = new FileInputStream(image);
		byte[] bArray = new byte[(int) image.length()];
		iStream.read(bArray);
		iStream.close();*/
		
		byte[] bArray = Files.readAllBytes(image.toPath());
		
		//urlParameters += convertByteArrayToString(bArray);
		
		//urlParameters = URLEncoder.encode(urlParameters, StandardCharsets.UTF_8.toString());

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeBytes("*****1234567890");
		wr.writeBytes(urlParameters);
		//wr.writeBytes("*****1234567890");
		//wr.writeBytes(DatatypeConverter.printBase64Binary(bArray));
		//wr.write(bArray);
		wr.flush();
		wr.close();

		
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		if(responseCode == 200){
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
		else{
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			System.out.println(response.toString());
		}
	}
	
	private static void sendPostCloudSight() throws Exception {
		
		String url = "https://api.cloudsightapi.com/image_requests";
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		//con.setRequestProperty("Authorization:", " CloudSight PK2x9PRpXn-3BSAh6QRdpA");
		con.setRequestProperty("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");//+OAuthCalculation.calculateOAuth());
		//con.setRequestProperty("OAuth", OAuthCalculation.calculateOAuth());
/*		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
		//con.setRequestProperty("image_request[locale]:", "en");
		
		//String urlParameters = "form-data: name=\""+imagesString+"\"; file-name=\""+imagesToken+"\"\n\r";//imagesString+combineToken+imagesToken;//apiString+combineToken+apiToken;//+combineQuery+versionString+combineToken+versionToken+combineQuery+imagesString+combineToken+imagesToken;
		
		String imagesToken = "E:/Users/Yahel/Desktop/jesus/3entombm.jpg";
		String urlParameters = "image_request[locale]=en&image_request[image]=";//@"+imagesToken;
		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		
		FileInputStream iStream = new FileInputStream(image);
		byte[] bArray = new byte[(int) image.length()];
		iStream.read(bArray);
		iStream.close();
		
		//urlParameters += convertByteArrayToString(bArray);
		
		//urlParameters = URLEncoder.encode(urlParameters, StandardCharsets.UTF_8.toString());

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		//wr.write(convertStringToByteArrayToString(OAuthCalculation.calculateOAuth()));
		wr.write(bArray);
		//wr.write(bArray);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		if(responseCode == 200){
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
		else{
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//print result
			System.out.println(response.toString());
		}
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
		String apiString = "api_key";
		String apiToken = "68afcccf311899e6f6cc6064de624901456c180a";
		String versionString = "version";
		String versionToken = "2016-05-20";
		
		String combineQuery = "&";
		String combineToken = "=";
		
		String imagesString = "images_file";
		String imagesToken = "E:/Users/Yahel/Desktop/jesus/3entombm.jpg";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
/*		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");*/
		//con.setRequestProperty(imagesString, imagesToken);
		
		//String urlParameters = "form-data: name=\""+imagesString+"\"; file-name=\""+imagesToken+"\"\n\r";//imagesString+combineToken+imagesToken;//apiString+combineToken+apiToken;//+combineQuery+versionString+combineToken+versionToken+combineQuery+imagesString+combineToken+imagesToken;
		
		File image = new File(imagesToken);
		System.out.println("file exists? "+image.exists());
		
		FileInputStream iStream = new FileInputStream(image);
		byte[] bArray = new byte[(int) image.length()];
		iStream.read(bArray);
		iStream.close();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeBytes(urlParameters);
		wr.write(bArray);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
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

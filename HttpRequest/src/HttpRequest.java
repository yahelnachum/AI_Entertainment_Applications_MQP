import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpRequest {

	public static final String EQUALS = "=";
	public static final String AND = "&";

	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_AUTHORIZATION_CLOUDSIGHT = "CloudSight ";
	public static final String HEADER_AUTHORIZATION_CLARIFAI = "Bearer ";

	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data; ";
	public static final String HEADER_BOUDARY = "boundary=";

	public static final String BODY_BOUNDARY = "------------------------aa2af2f6902e7855";
	public static final String BODY_TWO_HYPHENS = "--";
	public static final String BODY_CR_LF = "\r\n";
	public static final String BODY_DOUBLE_QUOTES = "\"";

	public static final String BODY_CONTENT_TYPE = "Content-Type: ";
	public static final String BODY_CONTENT_DISPOSITION = "Content-Disposition: ";
	public static final String BODY_FORM_DATA = "form-data; ";
	public static final String BODY_NAME = "name=";
	public static final String BODY_FILE_NAME = "filename=";
	public static final String BODY_SEMICOLON_SPACE = "; ";

	public static final String BODY_CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";
	public static final String BODY_CONTENT_TYPE_TEXT_PLAIN = "text/plain";
	public static final String BODY_CONTENT_PLACEHOLDER = "aoidfngoiefoi12097445";
	
	public static final String URL_CLARIFAI_TOKEN = "https://api.clarifai.com/v1/token/";
	public static final String URL_CLARIFAI_TAG = "https://api.clarifai.com/v1/tag";
	
	public static byte[] htmlPostBody(ArrayList<KeyValue> pairs){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < pairs.size(); i++){
			KeyValue keyValue = pairs.get(i);
			
			sb.append(keyValue.getKey());
			sb.append(EQUALS);
			sb.append(keyValue.getValue());
			
			if(i < pairs.size() - 1){
				sb.append(AND);
			}
		}
		
		return sb.toString().getBytes();
	}
	
	public static byte[] htmlPostBody(HttpConfiguration[] configurations){
		
		StringBuilder sb = new StringBuilder();
		
		int contentSize = 0;
		int[] placeHolderIndexes = new int[configurations.length];
		for(int i = 0; i < configurations.length; i++){
			
			HttpConfiguration config = configurations[i];
			
			sb.append(BODY_TWO_HYPHENS);
			sb.append(BODY_BOUNDARY);
			sb.append(BODY_CR_LF);
			
			sb.append(BODY_CONTENT_DISPOSITION);
			sb.append(BODY_FORM_DATA);
			
			sb.append(BODY_NAME);
			sb.append(BODY_DOUBLE_QUOTES);
			sb.append(config.getName());
			sb.append(BODY_DOUBLE_QUOTES);
			sb.append(BODY_SEMICOLON_SPACE);
			
			if(config.hasFileName()){
				sb.append(BODY_FILE_NAME);
				sb.append(BODY_DOUBLE_QUOTES);
				sb.append(config.getFileName());
				sb.append(BODY_DOUBLE_QUOTES);
			}
			sb.append(BODY_CR_LF);
			
			sb.append(BODY_CONTENT_TYPE);
			sb.append(config.getContentType());
			sb.append(BODY_CR_LF);
			sb.append(BODY_CR_LF);

			// content placeholder
			placeHolderIndexes[i] = sb.toString().length();
			sb.append(BODY_CONTENT_PLACEHOLDER);
			sb.append(BODY_CR_LF);
			contentSize += config.getContent().length;

			// end entire body
			if(i == configurations.length - 1)
			{
				sb.append(BODY_TWO_HYPHENS);
				sb.append(BODY_BOUNDARY);
				sb.append(BODY_TWO_HYPHENS);
				sb.append(BODY_CR_LF);
			}
		}
		
		byte[] bodyPart = sb.toString().getBytes();
		
		int neededSize = bodyPart.length - (BODY_CONTENT_PLACEHOLDER.length() * configurations.length) + contentSize;
		byte[] wholeBody = new byte[neededSize];
		
		int currentIndexSrc = 0;
		int currentIndexDest = 0;
		for(int i = 0; i < configurations.length; i++)
		{
			byte[] content = configurations[i].getContent();

			// before placeholder
			System.arraycopy(bodyPart, currentIndexSrc, wholeBody, currentIndexDest, placeHolderIndexes[i] - currentIndexSrc);
			currentIndexDest += placeHolderIndexes[i] - currentIndexSrc;
			currentIndexSrc = placeHolderIndexes[i] + BODY_CONTENT_PLACEHOLDER.length();

			// insert content instead of placeholder
			System.arraycopy(content, 0, wholeBody, currentIndexDest, content.length);
			currentIndexDest += content.length;

			// insert rest of data if its the last configuration
			if (i == configurations.length - 1)
			{
				System.arraycopy(bodyPart, currentIndexSrc, wholeBody, currentIndexDest, bodyPart.length - currentIndexSrc);
			}

		}

		return wholeBody;
	}
	
	public static String getResponse(HttpURLConnection conn) throws IOException{

		int responseCode1 = conn.getResponseCode();
		
		if(responseCode1 == 200){
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		}
		else{
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			System.err.println(response.toString());
			return response.toString();
		}

	}
	
	public static void sendData(HttpURLConnection conn, byte[] data) throws IOException{
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(data);
		wr.flush();
		wr.close();
	}
	
	public static HttpURLConnection getConnection(String urlStr, String connectionType) throws IOException{
		
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(connectionType);
		
		return conn;
	}
	
	public static ArrayList<KeyValue> postClarifai(String fileName, byte[] imageByte){
		try {
			
			HttpURLConnection con1 = getConnection(URL_CLARIFAI_TOKEN, "POST");
		
			ArrayList<KeyValue> pairs = new ArrayList<KeyValue>();
			pairs.add(new KeyValue("client_id", "j7yHzbxOlue-Q4NkEXTIl1UHllT3_UerH8TLn2Cu"));
			pairs.add(new KeyValue("client_secret", "dbNK8HdXVqGl4NbN-8U0v-KAJbPh40idRSvCd8vI"));
			pairs.add(new KeyValue("grant_type", "client_credentials"));
		
			sendData(con1, htmlPostBody(pairs));
		
			String accessToken = (new JSONObject(getResponse(con1))).get("access_token").toString();
			
			// get tags
			HttpURLConnection con = getConnection(URL_CLARIFAI_TAG, "POST");

			HttpConfiguration[] configurations = new HttpConfiguration[1];
			configurations[0] = new HttpConfiguration("encoded_data", fileName, BODY_CONTENT_TYPE_IMAGE_JPEG, imageByte);
			
			byte[] entireData = htmlPostBody(configurations);

			con.setRequestProperty("Authorization", "Bearer "+accessToken);
			con.setRequestProperty("Content-Length", "" + entireData.length);
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BODY_BOUNDARY);
			
			sendData(con, entireData);
			
			JSONObject response = new JSONObject(getResponse(con));
			JSONObject result = response.getJSONArray("results").getJSONObject(0);
			JSONObject tag = result.getJSONObject("result").getJSONObject("tag");
			JSONArray classes = tag.getJSONArray("classes");
			JSONArray probs = tag.getJSONArray("probs");
			
			ArrayList<KeyValue> classesAndProbs = new ArrayList<KeyValue>();
			for(int i = 0; i < classes.length(); i++){
				classesAndProbs.add(new KeyValue(classes.getString(i), probs.getDouble(i)));
				//System.out.printf("Clarifai: Class: %20s, Prob: %20f\n", classes.getString(i), probs.getDouble(i));
			}
			
			return classesAndProbs;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int requestsCloudsight = 0;
	public static String postCloudSight(String fileName, byte[] imageByte, String api_key){
		try {
			
			requestsCloudsight++;
			HttpURLConnection con1 = getConnection("https://api.cloudsightapi.com/image_requests", "POST");
		
			HttpConfiguration[] configurations = new HttpConfiguration[2];
			configurations[0] = new HttpConfiguration("image_request[locale]", BODY_CONTENT_TYPE_TEXT_PLAIN, "en".getBytes());
			configurations[1] = new HttpConfiguration("image_request[image]", "unityWebcam.jpg", BODY_CONTENT_TYPE_IMAGE_JPEG, imageByte);

			byte[] entireData = htmlPostBody(configurations);
			
			con1.setRequestProperty("Authorization", "CloudSight "+api_key);
			con1.setRequestProperty("Content-Length", "" + (entireData.length));
			con1.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BODY_BOUNDARY);
			
			sendData(con1, entireData);
		
			String token = (new JSONObject(getResponse(con1))).get("token").toString();
			
			// get tags
			String status = "not completed";
			JSONObject response = null;
			while(status.compareTo("not completed") == 0){
				requestsCloudsight++;
				HttpURLConnection con = getConnection("http://api.cloudsightapi.com/image_responses/" + token, "GET");
	
				con.setRequestProperty("Authorization", "CloudSight "+api_key);
				
				response = new JSONObject(getResponse(con));
				
				status = response.get("status").toString();
				
				Thread.sleep(1500);
			}
			
			if(response.has("name") == true){
			String name = response.get("name").toString();
			//System.out.println("CloudSight: "+response.get("name").toString());
			return name;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<KeyValue> postWatson(byte[] imageByte, String api_key){
		try {
			
			HttpURLConnection con1 = getConnection("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key="+api_key+"&version=2016-05-20", "POST");
			
			sendData(con1, imageByte);
		
			JSONObject response = new JSONObject(getResponse(con1));
			JSONArray images = response.getJSONArray("images");
			
			if(images.length() > 0){
				JSONObject image = images.getJSONObject(0);
				
				if(image.has("classifiers")){
					JSONArray classifiers = image.getJSONArray("classifiers");
					
					if(classifiers.length() > 0){
						JSONObject classifier = classifiers.getJSONObject(0);
						JSONArray classes = classifier.getJSONArray("classes");
						
						ArrayList<KeyValue> classesAndScores = new ArrayList<KeyValue>();
						for(int i = 0; i < classes.length(); i++){
							classesAndScores.add(new KeyValue(classes.getJSONObject(i).getString("class"), classes.getJSONObject(i).getDouble("score")));
							//System.out.printf("Watson: Class: %20s, Score: %20f\n", classes.getJSONObject(i).getString("class"), classes.getJSONObject(i).getDouble("score"));
						}
						
						return classesAndScores;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}

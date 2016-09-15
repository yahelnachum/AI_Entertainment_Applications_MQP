import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class Main {

	public static void main(String[] args) throws IOException {
	
		URL url = new URL("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection)con;
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);
		
		Map<String,String> arguments = new HashMap<>();
		arguments.put("api_key", "68afcccf311899e6f6cc6064de624901456c180a");
		StringJoiner sj = new StringJoiner("&");
		for(Map.Entry<String,String> entry : arguments.entrySet()){
		    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
		         + URLEncoder.encode(entry.getValue(), "UTF-8"));
		    System.out.println(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
		         + URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		System.out.println(sj.toString());
		byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		
		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		http.connect();
		try(OutputStream os = http.getOutputStream()) {
		    os.write(out);
		}

		InputStream is = http.getInputStream();
		
		System.out.println(is.available());
		byte[] bytes = new byte[is.available()];
		
		is.read(bytes);

		StringBuilder sb = new StringBuilder();
		for(byte b: bytes){
			sb.append((char) b);
		}
		System.out.println(sb.toString());
	}
}

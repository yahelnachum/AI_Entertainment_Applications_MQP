import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class MultiThreadAnalyze implements Runnable{
	
	File _file;
	Service _service;
	int _indexNum;
	
	public MultiThreadAnalyze(File file, Service service, int indexNum) {
		_file = file;
		_service = service;
		_indexNum = indexNum;
	}
	
	@Override
	public void run() {
		try {
			if(_service == Service.CLARIFAI){
				Clock c = new Clock();
				ArrayList<KeyValue> classesAndProbs = HttpRequest.postClarifai(_file.getAbsolutePath(), Files.readAllBytes(_file.toPath()));
				long delta = c.delta();
				for(KeyValue keyValue : classesAndProbs){
					System.out.printf("0,%d,clarifai,%s,%f\n", _indexNum, keyValue.getKey(), keyValue.getValue());
				}
				System.out.printf("1,%d,clarifai,%d\n", _indexNum, delta);
			}
			else if(_service == Service.WATSON){
				Clock c = new Clock();
				/*ArrayList<KeyValue> classesAndScores = HttpRequest.postWatson(Files.readAllBytes(_file.toPath()));
				long delta = c.delta();
				if(classesAndScores != null){
				for(KeyValue keyValue : classesAndScores){
					System.out.printf("0,%d,watson,%s,%f\n", _indexNum, keyValue.getKey(), keyValue.getValue());
				}}
				System.out.printf("1,%d,watson,%d\n", _indexNum, delta);*/
			}
			else if(_service == Service.CLOUDSIGHT){
				Clock c = new Clock();
				//String name = HttpRequest.postCloudSight(_file.getAbsolutePath(), Files.readAllBytes(_file.toPath()));
				long delta = c.delta();
				//System.out.printf("0,%s,cloudsight,%s,1\n", _file.getAbsolutePath(), name);
				System.out.printf("1,%s,cloudsight,%d\n", _file.getAbsolutePath(), delta);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

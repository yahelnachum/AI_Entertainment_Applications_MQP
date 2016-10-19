import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class MultThreadWatson implements Runnable {

	ArrayList<File> _files;
	int _indexMin;
	int _indexMax;
	String _api_key;
	
	public MultThreadWatson(ArrayList<File> files, int indexMin, int indexMax, String api_key) {
		_files = files;
		_indexMin = indexMin;
		_indexMax = indexMax;
		_api_key = api_key;
	}
	
	@Override
	public void run() {
		for(int i = _indexMin; i < _indexMax; i++){
			
			try {
				Clock c = new Clock();
				ArrayList<KeyValue> classesAndScores = HttpRequest.postWatson(Files.readAllBytes(_files.get(i).toPath()),_api_key);
				long delta = c.delta();
				if(classesAndScores != null){
				for(KeyValue keyValue : classesAndScores){
					System.out.printf("0,%d,watson,%s,%f\n", i, keyValue.getKey(), keyValue.getValue());
				}}
				System.out.printf("1,%d,watson,%d\n", i, delta);
				Thread.sleep(65000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}

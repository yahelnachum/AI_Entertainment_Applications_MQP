import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileIOHandler {

	public static void findAllFiles(File dir, ArrayList<File> list){
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			
			for(File file : files){
				findAllFiles(file, list);
			}
		}
		else{
			list.add(dir);
		}
	}
	
	public static String getRelativePath(File dir, File file){
		return file.getAbsolutePath().substring(dir.getAbsolutePath().length() + 1);
	}
}

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {

	static String imageName;
	static File imageFile;
	static byte[] imageData;
	
	static String dir = "C:\\Users\\yahel\\Downloads\\Pictures at the WAM";
	static File root = new File(dir);
	static int option = 3;
	public static void main(String[] args) throws Exception {
		
		
		
		ArrayList<File> files = new ArrayList<File>();
		FileIOHandler.findAllFiles(root, files);
		
		int section = 0;
		int sectionSize = 5;
		
		switch(option){
		case 1:
			printFilePropertiesCSV(files);
			break;
		case 2:
			
			/*for(int i = files.size() - 1; i > 4; i--){
				files.remove(i);
			}*/
			
			
			for(int i = section * sectionSize; i < files.size() & i < (section + 1) * sectionSize; i++){
				new Thread(new MultiThreadAnalyze(files.get(i), Service.CLARIFAI, i)).run();
			}
			
			break;
		case 3:
			
			/*for(int i = files.size() - 1; i > 4; i--){
				files.remove(i);
			}*/

			int change = 149;
			
			new Thread(new MultThreadWatson(files, 230, 233, "5d9f0163843edd0f35f1fd75429b1dc1d27ccf3c")).start();
			new Thread(new MultThreadWatson(files, 233, 236, "21049e4a2ffda3adaaa92d8262708174ddc5201b")).start();
			new Thread(new MultThreadWatson(files, 236, 240, "ff582a16a3ece28e2c40f9da3fd157be1f232f89")).start();
			
			break;
		case 4:
			
			int cloudOffset = 0;
			int cloudSection = 0;
			int cloudSectionSize = 3;
			
			new Thread(new MultThreadCloudSight(files, 190, 193, "ht2VhvQapfTGpKX37mB8yQ")).start();
			new Thread(new MultThreadCloudSight(files, 193, 196, "eHfcdDKNNuplFbXw7vMUpQ")).start();
			new Thread(new MultThreadCloudSight(files, 196, 200, "sSXwCsxn6h6KDlhv4e9iBw")).start();
			break;
		}
		/*
		ArrayList<File> files = new ArrayList<File>();
		File root = new File("C:/Users/yahel/Downloads/Categories");
		FileIOHandler.findAllFiles(root, files);
		
		System.out.println(files.size());
		for(File file : files){
			System.out.println(FileIOHandler.getRelativePath(root, file));
		}*/
		
		/*imageName = "unityWebcam.jpg";
		imageFile = new File(imageName);
		imageData = Files.readAllBytes(imageFile.toPath());
		
		new Thread(new Runnable() {
					
			@Override
			public void run() {
				HttpRequest.postClarifai(imageName, imageData);
			}
		}).start();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpRequest.postCloudSight(imageName, imageData);
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpRequest.postWatson(imageData);
			}
		}).start();*/
	}

	private static void printFilePropertiesCSV(ArrayList<File> files) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("index, category path, total pixels, x pixels, y pixels\n");
			
			Clock c = new Clock();
			int i = 1;
			for(File file : files){
			
				BufferedImage bimg = ImageIO.read(file);
			
				sb.append(i - 1);
				sb.append(",");
				sb.append(file.getAbsolutePath());
				sb.append(",");
				sb.append(bimg.getWidth() * bimg.getHeight());
				sb.append(",");
				sb.append(bimg.getWidth());
				sb.append(",");
				sb.append(bimg.getHeight());
				sb.append("\n");
				
				if(i % 10 == 0) System.out.println(i);
				i++;
			}
			
			System.out.println(sb.toString());
			System.out.println(c.delta());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
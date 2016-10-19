import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HasTag2 {

	
	public static void main(String[] args){
		try{
			String indexAndAcceptedTagsString = "IndexandAcceptedTags.txt";
			String acceptedTagsAndSimilarTagsString = "AcceptedTagsandSimilarTags.txt";
			String indexAndActualTagsString = "IndexandActualTags.txt";
			
			System.out.println("IndexandAcceptedTags");
			ArrayList<String[]> indexAndAcceptedTagsArray = new ArrayList<String[]>();
			File file = new File(indexAndAcceptedTagsString);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				indexAndAcceptedTagsArray.add(line.split(","));
			}
			
			System.out.println("AcceptedTagsandSimilarTags");
			ArrayList<String[]> acceptedTagsandSimilarTagsArrays = new ArrayList<String[]>();
			File file1 = new File(acceptedTagsAndSimilarTagsString);
			Scanner sc1 = new Scanner(file1);
			while(sc1.hasNextLine()){
				String line = sc1.nextLine();
				acceptedTagsandSimilarTagsArrays.add(line.split(","));
			}
			
			ArrayList<ArrayList<String>> indexAndSimilarTagsArray = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < indexAndAcceptedTagsArray.size(); i++){
				ArrayList<String> indexAndSimilarTags = new ArrayList<String>();
				String[] indexAndAcceptedTags = indexAndAcceptedTagsArray.get(i);
				indexAndSimilarTags.add(indexAndAcceptedTags[0]);
				
				for(int j = 1; j < indexAndAcceptedTags.length; j++){
					String acceptedTag = indexAndAcceptedTags[j];
					
					for(int k = 0; k < acceptedTagsandSimilarTagsArrays.size(); k++){
						String[] similarTags = acceptedTagsandSimilarTagsArrays.get(k);
						if(similarTags[0].compareTo(acceptedTag) == 0){
							for(int l = 0; l < similarTags.length; l++){
								indexAndSimilarTags.add(similarTags[l]);
							}
						}
					}
				}
				
				indexAndSimilarTagsArray.add(indexAndSimilarTags);
			}
			
			for(int i = 0; i < indexAndSimilarTagsArray.size(); i++){
				for(int j = 0; j < indexAndSimilarTagsArray.get(i).size(); j++){
					System.out.printf("%s,",indexAndSimilarTagsArray.get(i).get(j));
				}
				System.out.println();
			}
			System.out.println(indexAndSimilarTagsArray.size());
			System.out.println();
			
			System.out.println("IndexAndActualTagsString");
			ArrayList<String[]> indexAndActualTagsStringArrays = new ArrayList<String[]>();
			File file2 = new File(indexAndActualTagsString);
			Scanner sc2 = new Scanner(file2);
			while(sc2.hasNextLine()){
				String line = sc2.nextLine();
				indexAndActualTagsStringArrays.add(line.split(","));
			}
			
			for(int i = 0; i < indexAndActualTagsStringArrays.size(); i++){
				String index = indexAndActualTagsStringArrays.get(i)[0];
				String actualTag = indexAndActualTagsStringArrays.get(i)[1];
				boolean found = false;
				for(int j = 0; j < indexAndSimilarTagsArray.size(); j++){
					ArrayList<String> indexAndSimilarTags = indexAndSimilarTagsArray.get(j);
					
					if(indexAndSimilarTags.get(0).compareTo(index) == 0){
						for(int k = 1; k < indexAndSimilarTags.size(); k++){
							String potentialTag = indexAndSimilarTags.get(k);
							
							if(actualTag.contains(potentialTag)){
								System.out.println(potentialTag);
								found = true;
							}
						}
					}
				}
				
				if(found){
					//System.out.println("Y");
				}
				else{
					//System.out.println("N");
				}
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private static String cut(String category) {
		String[] values = category.split("/");
		return values[values.length - 2];
	}
}

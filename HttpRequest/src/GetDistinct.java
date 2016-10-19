import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetDistinct {

	
	public static void main(String[] args){
		try{
			String indexAndAcceptedTagsString = "IndexandAcceptedTags.txt";
			
			System.out.println("indexAndAcceptedTags");
			ArrayList<String> duplicateTags = new ArrayList<String>();
			File file = new File(indexAndAcceptedTagsString);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] lineSplit = line.split(",");

				for(int i = 1; i < lineSplit.length; i++){
					duplicateTags.add(lineSplit[i]);
				}
			}

			ArrayList<String> distinctTags = new ArrayList<String>();
			for(int i = 0; i < duplicateTags.size(); i++){
				String potentialNewTag = duplicateTags.get(i);
				
				boolean found = false;
				for(int j = 0; j < distinctTags.size(); j++){
					if(distinctTags.get(j).compareTo(potentialNewTag) == 0){
						found = true;
					}
				}
				
				if(!found){
					distinctTags.add(potentialNewTag);
				}
			}
			
			for(int i = 0; i < distinctTags.size(); i++){
				System.out.println(distinctTags.get(i)+","+distinctTags.get(i)+"s");
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

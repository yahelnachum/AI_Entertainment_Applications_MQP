import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HasTag {

	
	public static void main(String[] args){
		try{
			String keyValues = "KeyValues.txt";
			String categoryTags = "CategoryTags.txt";
			
			System.out.println("getting allowed pairs");
			ArrayList<KeyValue<String,String[]>> allowedPairs = new ArrayList<KeyValue<String,String[]>>();
			File file = new File(keyValues);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				String[] values = line.split(",");
				
				KeyValue<String, String[]> keyValues1 = new KeyValue<String, String[]>(values[0], values);
				allowedPairs.add(keyValues1);
			}
			System.out.println(allowedPairs.size());
			
			System.out.println("getting actual pairs");
			ArrayList<KeyValue<String,String>> actualPairs = new ArrayList<KeyValue<String,String>>();
			File file1 = new File(categoryTags);
			Scanner sc1 = new Scanner(file1);
			while(sc1.hasNextLine()){
				String line = sc1.nextLine();
				String[] values = line.split(",");
			
				KeyValue<String, String> keyValues2 = new KeyValue<String, String>(values[0].replace('\\', '/'), values[1]);
				actualPairs.add(keyValues2);
			}
			System.out.println(actualPairs.size());
			
			System.out.println("checking actual with allowed");
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < actualPairs.size(); i++){
				String category = actualPairs.get(i).getKey();
				String tag = actualPairs.get(i).getValue();
				
				boolean found = false;
				String subCategory = cut(category);
				//System.out.printf("cat: %s, tag: %s, sub: %s\n", category, tag, subCategory);
				for(int j = 0; j < allowedPairs.size(); j++){
					
					if(allowedPairs.get(j).getKey().compareTo(subCategory) == 0){
						String[] allowedValues = allowedPairs.get(j).getValue();
						
						for(int k = 0; k < allowedValues.length; k++){
							if(tag.contains(allowedValues[k])){
								//sb.append("here");
								found = true;
								k = allowedValues.length;
								
							}
						}
						
						j = allowedPairs.size();
					}
					
				}
				if(found){
					sb.append("Y\n");
				} else{
					sb.append("N\n");
				}
			}
			
			System.out.println(sb.toString());
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private static String cut(String category) {
		String[] values = category.split("/");
		return values[values.length - 2];
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HasTag2 {

	
	public static void main(String[] args){
		try {
			
			ArrayList<String[]> acceptedAndSimilar = new ArrayList<String[]>();
			Scanner sc = new Scanner(new File("AtWamPreCropped/AcceptedAndSimilarData.txt"));
			while(sc.hasNextLine()){
				String[] line = sc.nextLine().split(",");
				acceptedAndSimilar.add(line);
			}
			
			ArrayList<String[]> indexAndAccepted = new ArrayList<String[]>();
			Scanner sc1 = new Scanner(new File("AtWamPreCropped/AcceptedTagsData.txt"));
			while(sc1.hasNextLine()){
				String[] line = sc1.nextLine().split(",");
				indexAndAccepted.add(line);
			}
			
			ArrayList<String[]> indexAndActual = new ArrayList<String[]>();
			Scanner sc2 = new Scanner(new File("AtWamPreCropped/ServiceTagsData.txt"));
			while(sc2.hasNextLine()){
				String[] line = sc2.nextLine().split(",");
				indexAndActual.add(line);
			}
			
			StringBuilder results = new StringBuilder();
			for(int i = 0; i < indexAndActual.size(); i++){
				String[] indexAndActualLine = indexAndActual.get(i);
				String index = indexAndActualLine[0];
				String service = indexAndActualLine[1];
				String[] actual = indexAndActualLine[2].split(" ");
				
				for(int j = 0; j < indexAndAccepted.size(); j++){
					String[] indexAndAcceptedLine = indexAndAccepted.get(j);
					String index0 = indexAndAcceptedLine[0];
					String accepted = indexAndAcceptedLine[1];
					
					if(index.compareTo(index0) == 0){
						for(int k = 0; k < acceptedAndSimilar.size(); k++){
							String[] acceptedAndSimilarLine = acceptedAndSimilar.get(k);
							String accepted0 = acceptedAndSimilarLine[0];
							String similar = acceptedAndSimilarLine[1];
							
							if(accepted.compareTo(accepted0) == 0){
								boolean found = false;
								for(int l = 0; l < actual.length; l++){
								
									if(actual[l].compareTo(similar) == 0){
										found = true;
									}
								}
								
								if(found){
									results.append(index);
									results.append(",");
									results.append(service);
									results.append(",");
									results.append(indexAndActualLine[2]);
									results.append(",");
									results.append(accepted);
									results.append(",");
									results.append(similar);
									results.append("\n");
								}
							}
						}
					}
				}
			}
			
			
			System.out.println(results);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

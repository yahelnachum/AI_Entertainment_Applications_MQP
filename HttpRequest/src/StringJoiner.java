import java.util.ArrayList;


public class StringJoiner {

	String joiner;
	ArrayList<String> strings = new ArrayList<String>();
	
	public StringJoiner(String string) {
		joiner = string;
	}

	public void add(String string) {
		strings.add(string);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < strings.size() - 1; i++){
			sb.append(strings.get(i));
			sb.append(joiner);
		}
		
		if(strings.size() == 1){
			sb.append(strings.get(0));
		}
		
		return sb.toString();
	}

}

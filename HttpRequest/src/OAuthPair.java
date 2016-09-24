
public class OAuthPair implements Comparable<OAuthPair>{

	String _key;
	String _value;
	
	public OAuthPair(String key, String value){
		_key = key;
		_value = value;
	}
	
	public void setKey(String key){
		_key = key;
	}
	
	public void setValue(String value){
		_value = value;
	}
	
	public String getKey(){
		return _key;
	}
	
	public String getValue(){
		return _value;
	}

	@Override
	public int compareTo(OAuthPair obj) {
		if(_key.compareTo(obj.getKey()) == 0){
			return _value.compareTo(obj.getValue());
		}
		else{
			return _key.compareTo(obj.getKey());
		}
	}
}

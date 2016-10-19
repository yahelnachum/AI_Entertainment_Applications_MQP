
public class KeyValue<K,V> {

	private K _key;
	private V _value;
	
	public KeyValue(K key, V value){
		_key = key;
		_value = value;
	}
	
	public K getKey(){
		return _key;
	}
	
	public V getValue(){
		return _value;
	}
}


public class HttpConfiguration {

	String _name;
	String _contentType;
	byte[] _content;

	String _filename;
	boolean _hasFilename = false;

	public HttpConfiguration(String name, String filename, String contentType, byte[] content)
	{
		_hasFilename = true;

		_name = name;
		_filename = filename;
		_contentType = contentType;
		_content = content;
	}

	public HttpConfiguration(String name, String contentType, byte[] content)
	{
		_name = name;
		_contentType = contentType;
		_content = content;
	}

	public String getName()
	{
		return _name;
	}

	public String getContentType()
	{
		return _contentType;
	}

	public byte[] getContent()
	{
		return _content;
	}

	public String getFileName()
	{
		return _filename;
	}

	public boolean hasFileName()
	{
		return _hasFilename;
	}
}

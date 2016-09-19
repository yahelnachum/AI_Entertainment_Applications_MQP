using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class HttpRequest : MonoBehaviour {

	// Use this for initialization
	void Start () {

        //WWW results = GET("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=68afcccf311899e6f6cc6064de624901456c180a&url=https://github.com/watson-developer-cloud/doc-tutorial-downloads/raw/master/visual-recognition/fruitbowl.jpg&version=2016-05-19");
        //Debug.Log("Results: " + results.text);

        /*byte[] bytes = System.IO.File.ReadAllBytes(Application.dataPath + "/unityWebcam.jpg");
        WWW results = POST("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=68afcccf311899e6f6cc6064de624901456c180a&version=2016-05-20", "images_file", "@" + Application.dataPath + "/unityWebcam.jpg", "image/jpg", bytes);
        Debug.Log("Results: " + results.text);*/

        WWW results = PostWatson(Application.dataPath + "/unityWebcam.jpg");
        Debug.Log("Results: " + results.text);
    }

    WWW PostWatson(string pathToImage)
    {
        WWWForm form = new WWWForm();
        //form.AddBinaryData("images_file", System.IO.File.ReadAllBytes(Application.dataPath + "/unityWebcam.jpg"));
        //form.AddBinaryData("images_file", System.IO.File.ReadAllBytes(Application.dataPath + "/unityWebcam.jpg"), Application.dataPath + "/unityWebcam.jpg");
        //form.AddBinaryData("images_file", System.IO.File.ReadAllBytes(Application.dataPath + "/unityWebcam.jpg"), Application.dataPath + "/unityWebcam.jpg", "multipart/form-data");
        WWW www = new WWW("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=68afcccf311899e6f6cc6064de624901456c180a&version=2016-05-20", System.IO.File.ReadAllBytes(pathToImage));// form);
        StartCoroutine(WaitForRequest(www));

        return www;
    }

    // Update is called once per frame
    void Update () {
	
	}

    public WWW GET(string url)
    {
        WWW www = new WWW(url);
        StartCoroutine(WaitForRequest(www));
        return www;
    }

    private IEnumerator WaitForRequest(WWW www)
    {
        yield return www;
        // check for errors
        if (www.error == null)
        {
            Debug.Log("WWW Ok!: " + www.text);
        }
        else
        {
            Debug.Log("WWW Error: " + www.error);
        }
    }
}

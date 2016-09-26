using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using SimpleJSON;

public class HttpRequest : MonoBehaviour {

	// Use this for initialization
	void Start () {


        //StartCoroutine(PostWatson(Application.dataPath + "/unityWebcam.jpg"));
        //StartCoroutine(PostCloudSight(Application.dataPath + "/unityWebcam.jpg"));
        StartCoroutine(PostClarifai(Application.dataPath + "/unityWebcam.jpg"));
    }

    IEnumerator PostClarifai(string pathToImage)
    {

        string urlParameters = "client_id=j7yHzbxOlue-Q4NkEXTIl1UHllT3_UerH8TLn2Cu&client_secret=dbNK8HdXVqGl4NbN-8U0v-KAJbPh40idRSvCd8vI&grant_type=client_credentials";
        //WWW www = new WWW("https://api.cloudsightapi.com/image_requests", entireData, form.headers);// form);
        WWW www = new WWW("https://api.clarifai.com/v1/token/", System.Text.Encoding.UTF8.GetBytes(urlParameters));//, headers);// form);

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

        //while (www.isDone == false) { }
        
        string token = JSON.Parse(www.text)["access_token"].Value;
        Debug.Log("access_token: " + token);

        byte[] image = System.IO.File.ReadAllBytes(pathToImage);

        string boundary = "------------------------aa2af2f6902e7855";
        string beforeImage = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"encoded_data\"; filename=\"unityWebcam.jpg\"\r\n" + "Content-Type: image/jpeg" + "\r\n" + "\r\n";
        string afterImage = "\r\n" + "--" + boundary + "--\r\n";

        byte[] bImage = System.Text.Encoding.UTF8.GetBytes(beforeImage);
        byte[] aImage = System.Text.Encoding.UTF8.GetBytes(afterImage);

        byte[] entireData = new byte[bImage.Length + image.Length + aImage.Length];
        System.Buffer.BlockCopy(bImage, 0, entireData, 0, bImage.Length);
        System.Buffer.BlockCopy(image, 0, entireData, bImage.Length, image.Length);
        System.Buffer.BlockCopy(aImage, 0, entireData, bImage.Length + image.Length, aImage.Length);

        Debug.Log(entireData.Length);

        Dictionary<string, string> headers = new Dictionary<string, string>();
        headers.Add("Authorization", "Bearer " + token);
        Debug.Log("2");
        headers.Add("Content-Length", "" + (entireData.Length));
        Debug.Log("3");
        headers.Add("Content-Type", "multipart/form-data; boundary=" + boundary);

        www = new WWW("https://api.clarifai.com/v1/tag", entireData, headers);
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

        while (www.isDone == false) { }

        Debug.Log(www.text);
    }

    IEnumerator PostCloudSight(string pathToImage)
    {
        byte[] image = System.IO.File.ReadAllBytes(pathToImage);

        string boundary = "------------------------aa2af2f6902e7855";
        string beforeImage = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"image_request[locale]\"\r\n" + "Content-Type: text/plain" + "\r\n" + "\r\n" + "en" + "\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"image_request[image]\"; filename=\"unityWebcam.jpg\"\r\n" + "Content-Type: image/jpeg" + "\r\n" + "\r\n";
        string afterImage = "\r\n" + "--" + boundary + "--\r\n";

        byte[] bImage = System.Text.Encoding.UTF8.GetBytes(beforeImage);
        byte[] aImage = System.Text.Encoding.UTF8.GetBytes(afterImage);

        byte[] entireData = new byte[bImage.Length + image.Length + aImage.Length];
        System.Buffer.BlockCopy(bImage, 0, entireData, 0, bImage.Length);
        System.Buffer.BlockCopy(image, 0, entireData, bImage.Length, image.Length);
        System.Buffer.BlockCopy(aImage, 0, entireData, bImage.Length + image.Length, aImage.Length);

        Debug.Log(entireData.Length);

        WWWForm form = new WWWForm();
        Debug.Log("1");
        form.headers.Clear();
        form.headers.Add("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");
        Debug.Log("2");
        form.headers.Add("Content-Length", "" + (entireData.Length));
        Debug.Log("3");
        form.headers["Content-Type"] = "multipart/form-data; boundary=" + boundary;
        Debug.Log("4");

        Dictionary<string, string> headers = new Dictionary<string, string>();
        headers.Add("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");
        Debug.Log("2");
        headers.Add("Content-Length", "" + (entireData.Length));
        Debug.Log("3");
        headers.Add("Content-Type","multipart/form-data; boundary=" + boundary);

        //WWW www = new WWW("https://api.cloudsightapi.com/image_requests", entireData, form.headers);// form);
        WWW www = new WWW("https://api.cloudsightapi.com/image_requests", entireData, headers);// form);

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

        //while (www.isDone == false) { }
        
        string token = JSON.Parse(www.text)["token"].Value;
        Debug.Log("token: " + token);

        string status = "not completed";
        string response = "";
        while(status.Equals("not completed"))
        {
            Debug.Log("hello");
            status = "";

            headers = new Dictionary<string, string>();
            headers.Add("Authorization", "CloudSight PK2x9PRpXn-3BSAh6QRdpA");

            www = new WWW("http://api.cloudsightapi.com/image_responses/" + token, null, headers);
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

            while (www.isDone == false) { }

            Debug.Log(www.text);

            status = JSON.Parse(www.text)["status"].Value;
            response = JSON.Parse(www.text)["name"].Value;

            yield return new WaitForSeconds(2);
        }

        Debug.Log(response);
    }

    IEnumerator PostWatson(string pathToImage)
    {
        WWWForm form = new WWWForm();
        WWW www = new WWW("https://gateway-a.watsonplatform.net/visual-recognition/api/v3/classify?api_key=68afcccf311899e6f6cc6064de624901456c180a&version=2016-05-20", System.IO.File.ReadAllBytes(pathToImage));// form);

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

        Debug.Log(www.text);
    }

    // Update is called once per frame
    void Update () {
	
	}

    public WWW GET(string url)
    {
        WWW www = new WWW(url);
        //StartCoroutine(WaitForRequest(www));
        return www;
    }
}

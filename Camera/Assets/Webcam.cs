using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class Webcam : MonoBehaviour {

    public WebCamTexture mCamera = null;
    public RawImage plane;
   // public GameObject ImageOnPanel;

    float sizeMultiplier = -0.001F;
    // Use this for initialization
    void Start () {

        Debug.Log("Script has been started");
        GameObject panel = GameObject.Find("Canvas");
        plane = panel.AddComponent<RawImage>();

        

        
    }

    // Update is called once per frame
    bool first = true;
    void Update () {
        if (first)
        {
            WebCamDevice[] devices = WebCamTexture.devices;

            int i = 0;
            while (i < devices.Length)
            {
                Debug.Log(devices[i].name);
                i++;
            }

            mCamera = new WebCamTexture(devices[0].name, 1920, 1080);
            Debug.Log("Webcam: " + mCamera == null);
            Debug.Log("RawImage: " + plane == null);
            Debug.Log("RawImage Texture: " + plane.texture == null);

            plane.texture = mCamera;
            mCamera.Play();

            int width = mCamera.width;
            int height = mCamera.height;

            Debug.Log("Width : " + width);
            Debug.Log("Height: " + height);
            Debug.Log("Requested Width : " + mCamera.requestedHeight);
            Debug.Log("Requested Height: " + mCamera.requestedWidth);

            //plane.transform.localScale = new Vector3(sizeMultiplier * width, 1F, sizeMultiplier * height);

            

            first = false;

            Debug.Log("DataPath: " + Application.dataPath);
        }
    }

    void OnGUI()
    {
        if (GUI.Button(new Rect(10, 70, 50, 30), "Click"))
            TakeSnapShot();

    }

    void TakeSnapShot()
    {
        Debug.Log("Taking a snapshot");
        Texture2D snap = new Texture2D(mCamera.width, mCamera.height);
        snap.SetPixels(mCamera.GetPixels());
        snap.Apply();

        System.IO.File.WriteAllBytes(Application.dataPath+"/unityWebcam.jpg", snap.EncodeToJPG());
    }
}

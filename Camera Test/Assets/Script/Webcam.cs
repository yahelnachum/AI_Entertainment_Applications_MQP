using UnityEngine;
using System.Collections;

public class Webcam : MonoBehaviour {

    public WebCamTexture mCamera = null;
    public GameObject plane;

    float sizeMultiplier = -0.001F;
    // Use this for initialization
    void Start () {

        Debug.Log("Script has been started");
        plane = GameObject.FindWithTag("Player");

        mCamera = new WebCamTexture(1920,1080);
        plane.GetComponent<Renderer>().material.mainTexture = mCamera;
        mCamera.Play();
    }

    // Update is called once per frame
    bool first = true;
    void Update () {
        if (first)
        {
            int width = mCamera.width;
            int height = mCamera.height;

            Debug.Log("Width : " + width);
            Debug.Log("Height: " + height);
            Debug.Log("Requested Width : " + mCamera.requestedHeight);
            Debug.Log("Requested Height: " + mCamera.requestedWidth);

            plane.transform.localScale = new Vector3(sizeMultiplier * width, 1F, sizeMultiplier * height);

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

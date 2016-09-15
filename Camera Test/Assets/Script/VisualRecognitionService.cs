using UnityEngine;
using System.Collections;

public class VisualRecognitionService : MonoBehaviour {

    private VisualRecognition m_VisualRecognition = new VisualRecognition();

    // Use this for initialization
    void Start () {
        if (!m_VisualRecognition.GetClassifiers(OnGetClassifiers))
            Log.Debug("ExampleVisualRecognition", "Getting classifiers failed!");
    }

    private void OnGetClassifiers(GetClassifiersTopLevelBrief classifiers)
    {
        if (classifiers != null && classifiers.classifiers.Length > 0)
        {
            foreach (GetClassifiersPerClassifierBrief classifier in classifiers.classifiers)
            {
                Log.Debug("ExampleVisualRecognition", "Classifier: " + classifier.name + ", " + classifier.classifier_id);
            }
        }
        else
        {
            Log.Debug("ExampleVisualRecognition", "Failed to get classifiers!");
        }
    }
    // Update is called once per frame
    void Update () {
	
	}
}

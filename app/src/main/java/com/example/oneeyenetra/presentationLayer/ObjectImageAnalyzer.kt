package com.example.oneeyenetra.presentationLayer

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.oneeyenetra.data.TfLiteObjectDetector
import com.example.oneeyenetra.domain.Classification

class ObjectImageAnalyzer(
    private val objectDetector: TfLiteObjectDetector,
    private val onResults: (List<String>) -> Unit
) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        try {
            // Convert ImageProxy to Bitmap
            val bitmap = image.toBitmap() // Use the helper function below.
            val previewWidth = image.width
            val previewHeight = image.height
            // Run object detection
            val detections = objectDetector.detect(bitmap)

            // Format results
            val results = detections.map { detection ->
                val category = detection.categories.firstOrNull()
                val label = category?.label ?: "Unknown"
                val confidence = category?.score ?: 0f
                "Label: $label, Confidence: ${"%.2f".format(confidence)}"
            }
//            val classifications = detections.map { detection ->
//                Classification(
//                    name = detection.,  // Assuming 'label' is the object name from detection
//                    score = detection.score  // Assuming 'score' is the confidence score from detection
//                )
//            }

            // Pass results to the callback

            onResults(results)
        } catch (e: Exception) {
            Log.e("LandmarkImageAnalyzer", "Error analyzing image", e)
        } finally {
            image.close()
        }
    }
}


/*package com.example.oneeyenetra.presentationLayer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.oneeyenetra.domain.Classification
import com.example.oneeyenetra.domain.LandmarkClassifier

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResults: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)
            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++
        image.close()
    }
}

package com.example.oneeyenetra.presentationLayer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.oneeyenetra.data.TfLiteObjectDetector
import com.example.oneeyenetra.domain.Classification

class ObjectDetectionImageAnalyzer(
    private val detector: TfLiteObjectDetector,
    private val onResults: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val bitmap = image.toBitmap().centerCrop(300, 300) // Adjust input size to fit model
            val results = detector.detect(bitmap)
            onResults(results)
        }
        frameSkipCounter++
        image.close()
    }
}*/

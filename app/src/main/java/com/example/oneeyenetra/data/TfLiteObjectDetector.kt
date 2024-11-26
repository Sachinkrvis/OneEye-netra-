package com.example.oneeyenetra.data

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import org.tensorflow.lite.task.vision.detector.Detection

class TfLiteObjectDetector(context: Context) {

    private val objectDetector: ObjectDetector

    init {
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5) // Limit results to 5 detections
            .setScoreThreshold(0.6f) // Confidence threshold
            .build()

        // Load model from assets folder
        objectDetector = ObjectDetector.createFromFileAndOptions(
            context,
            "efficientdet_lite0.tflite",
            options
        )
    }

    fun detect(bitmap: Bitmap): List<Detection> {
        // Convert Bitmap to TensorImage
        val tensorImage = TensorImage.fromBitmap(bitmap)

        // Perform object detection
        return objectDetector.detect(tensorImage)
    }
}

//package com.example.oneeyenetra.data
//
//import android.content.Context
//import android.graphics.Bitmap
//import com.example.oneeyenetra.domain.Classification
//import org.tensorflow.lite.support.image.TensorImage
//import org.tensorflow.lite.task.core.BaseOptions
//import org.tensorflow.lite.task.vision.detector.ObjectDetector
//
//class TfLiteObjectDetector(
//    private val context: Context,
//    private val threshold: Float = 0.5f,
//    private val maxResults: Int = 5
//) {
//
//    private var detector: ObjectDetector? = null
//
//    private fun setupDetector() {
//        val baseOptions = BaseOptions.builder()
//            .setNumThreads(2)
//            .build()
//        val options = ObjectDetector.ObjectDetectorOptions.builder()
//            .setBaseOptions(baseOptions)
//            .setMaxResults(maxResults)
//            .setScoreThreshold(threshold)
//            .build()
//
//        detector = ObjectDetector.createFromFileAndOptions(
//            context,
//            "1.tflite",
//            options
//        )
//    }
//
//    fun detect(bitmap: Bitmap): List<Classification> {
//        if (detector == null) {
//            setupDetector()
//        }
//        val tensorImage = TensorImage.fromBitmap(bitmap)
//        val results = detector?.detect(tensorImage)
//
//        return results?.flatMap { detection ->
//            detection.categories.map { category ->
//                Classification(
//                    name = category.label,
//                    score = category.score
//                )
//            }
//        } ?: emptyList()
//    }
//}

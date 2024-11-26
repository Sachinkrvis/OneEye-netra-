package com.example.oneeyenetra.data

import android.content.Context
import android.graphics.Bitmap
import com.example.oneeyenetra.domain.Classification
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class TfLiteObjectDetector(
    private val context: Context,
    private val threshold: Float = 0.5f,
    private val maxResults: Int = 5
) {

    private var detector: ObjectDetector? = null

    private fun setupDetector() {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResults)
            .setScoreThreshold(threshold)
            .build()

        detector = ObjectDetector.createFromFileAndOptions(
            context,
            "ssd_mobilenet_v1_coco_quant_postprocess_edgetpu.tflite",
            options
        )
    }

    fun detect(bitmap: Bitmap): List<Classification> {
        if (detector == null) {
            setupDetector()
        }
        val tensorImage = TensorImage.fromBitmap(bitmap)
        val results = detector?.detect(tensorImage)

        return results?.flatMap { detection ->
            detection.categories.map { category ->
                Classification(
                    name = category.label,
                    score = category.score
                )
            }
        } ?: emptyList()
    }
}

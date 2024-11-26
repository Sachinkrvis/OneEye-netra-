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
}*/

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
}

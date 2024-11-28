package com.example.oneeyenetra.presentationLayer

import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.oneeyenetra.domain.Classification
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

@Composable
fun DetectionOverlay(
    classifications: List<String>,
    boundingBoxes: List<RectF>,
    modifier: Modifier = Modifier,
    onObjectPositionDetected: (String, Offset) -> Unit // Callback to log detected positions
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Canvas dimensions
            val canvasWidth = size.width
            val canvasHeight = size.height

            android.util.Log.d("Detection", "Canvas Dimensions: width=$canvasWidth, height=$canvasHeight")
            android.util.Log.d("Detection", "Bounding Boxes: $boundingBoxes")
            android.util.Log.d("Detection", "Classifications: $classifications")

            // Pair classifications with bounding boxes
            val pairedData = boundingBoxes.zip(classifications) { box, label ->
                Pair(box, label)
            }

            // Process each bounding box and log position
            pairedData.forEachIndexed { index, (box, label) ->
                // Normalize box coordinates to canvas dimensions
                val left = box.left * canvasWidth
                val top = box.top * canvasHeight
                val right = box.right * canvasWidth
                val bottom = box.bottom * canvasHeight

                val centerX = (left + right) / 2
                val centerY = (top + bottom) / 2
                val position = Offset(centerX, centerY)

                // Log object position and classification
                android.util.Log.d(
                    "Detection",
                    "Object: $label, Position: (${position.x}, ${position.y})"
                )

                // Callback for further actions
                onObjectPositionDetected(label, position)

                // Draw bounding box
                drawRoundRect(
                    color = Color.Red.copy(alpha = 0.5f),
                    topLeft = Offset(left, top),
                    size = androidx.compose.ui.geometry.Size(
                        width = right - left,
                        height = bottom - top
                    ),
                    cornerRadius = CornerRadius(12f, 12f)
                )

                // Draw label above the bounding box
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        textSize = 36f
                        color = android.graphics.Color.WHITE
                        textAlign = Paint.Align.CENTER
                    }
                    canvas.nativeCanvas.drawText(
                        label,
                        centerX,
                        top - 10, // Slightly above the bounding box
                        paint
                    )
                }
            }
        }
    }
}



//@Composable
//fun DetectionOverlay(
//    classifications: List<String>,
//    boundingBoxes: List<RectF>,
//    modifier: Modifier = Modifier
//) {
//    Box(modifier = modifier) {
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            val canvasWidth = size.width
//            val canvasHeight = size.height
//
//            Log.d("Detection", "Canvas Dimensions: width=$canvasWidth, height=$canvasHeight")
//            Log.d("Detection", "Bounding Boxes: $boundingBoxes")
//            Log.d("Detection", "Classifications: $classifications")
//
//            // Handle mismatched sizes
//            val fixedClassifications = if (boundingBoxes.size > classifications.size) {
//                classifications + List(boundingBoxes.size - classifications.size) { "Unknown" }
//            } else {
//                classifications
//            }
//
//            boundingBoxes.forEachIndexed { index, box ->
//                drawRoundRect(
//                    color = Color.Red.copy(alpha = 0.5f),
//                    topLeft = Offset(box.left * canvasWidth, box.top * canvasHeight),
//                    size = androidx.compose.ui.geometry.Size(
//                        box.width() * canvasWidth,
//                        box.height() * canvasHeight
//                    ),
//                    cornerRadius = CornerRadius(16f, 16f)
//                )
//
//                drawContext.canvas.nativeCanvas.apply {
//                    drawText(
//                        fixedClassifications.getOrElse(index) { "Unknown" },
//                        box.left * canvasWidth,
//                        (box.top * canvasHeight) - 10, // Above the box
//                        Paint().apply {
//                            textSize = 40f
//                            color = Color.White.toArgb()
//                            textAlign = Paint.Align.LEFT
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//

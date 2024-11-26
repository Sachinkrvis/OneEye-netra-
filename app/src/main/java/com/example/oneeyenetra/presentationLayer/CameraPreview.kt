package com.example.oneeyenetra.presentationLayer

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // Display the camera preview using an AndroidView
    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                // Bind the controller to the PreviewView
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)

                // Optional: Adjust the scale type for the preview
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
            }
        },
        modifier = modifier
    )
}

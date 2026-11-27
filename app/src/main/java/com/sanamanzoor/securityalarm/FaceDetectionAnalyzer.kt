package com.sanamanzoor.securityalarm

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetectionAnalyzer(
    private val onAlertConditionMet: (eyesOpen: Boolean, smiling: Boolean) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val detector = FaceDetection.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    val primaryFace = faces.first()
                    val leftEyeOpen = (primaryFace.leftEyeOpenProbability ?: 0f) > 0.65f
                    val rightEyeOpen = (primaryFace.rightEyeOpenProbability ?: 0f) > 0.65f
                    val smiling = (primaryFace.smilingProbability ?: 0f) > 0.65f
                    onAlertConditionMet(leftEyeOpen && rightEyeOpen, smiling)
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}

@file:Suppress("MagicNumber")

package com.picmorrow.feature.camera.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.picmorrow.feature.camera.presentation.CameraViewModel
import com.picmorrow.feature.camera.presentation.components.CameraBackground
import com.picmorrow.feature.camera.presentation.components.CameraContent
import com.picmorrow.feature.camera.presentation.components.CameraTopBar
import com.picmorrow.feature.camera.presentation.model.CameraCategory
import java.io.File

@Composable
internal fun CameraScreen(
    onCloseClick: () -> Unit,
    onPhotoCaptured: (Uri, CameraCategory) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel = viewModel(),
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CameraBackground),
    ) {
        CameraTopBar(
            onCloseClick = onCloseClick,
            flashEnabled = uiState.contentUiState.flashEnabled,
            onFlashClick = viewModel::toggleFlash,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        CameraContent(
            imageCapture = imageCapture,
            uiState = uiState.contentUiState,
            onCategorySelected = viewModel::selectCategory,
            onCaptureClick = {
                val capturedCategory = uiState.contentUiState.selectedCategory
                capturePhoto(
                    context = context,
                    imageCapture = imageCapture,
                    onCaptureComplete = {
                        viewModel.onPhotoCaptured()
                        onPhotoCaptured(it, capturedCategory)
                    },
                    onCaptureError = {
                        viewModel.onPhotoCaptureFailed()
                    },
                )
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
internal fun CameraPreview(
    imageCapture: ImageCapture,
    flashEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember(context) { ProcessCameraProvider.getInstance(context) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }
    var camera by remember { mutableStateOf<Camera?>(null) }

    LaunchedEffect(camera, flashEnabled) {
        imageCapture.flashMode =
            if (flashEnabled) {
                ImageCapture.FLASH_MODE_ON
            } else {
                ImageCapture.FLASH_MODE_OFF
            }
        if (camera?.cameraInfo?.hasFlashUnit() == true) {
            camera?.cameraControl?.enableTorch(flashEnabled)
        }
    }

    AndroidView(
        factory = { previewContext ->
            PreviewView(previewContext).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                previewView = this
            }
        },
        modifier = modifier,
    )

    DisposableEffect(cameraProviderFuture, lifecycleOwner, previewView, imageCapture) {
        val currentPreviewView = previewView ?: return@DisposableEffect onDispose {}
        val executor = ContextCompat.getMainExecutor(context)
        val bindCamera = Runnable {
            camera = bindCameraUseCases(
                cameraProvider = cameraProviderFuture.get(),
                lifecycleOwner = lifecycleOwner,
                previewView = currentPreviewView,
                imageCapture = imageCapture,
            )
        }

        cameraProviderFuture.addListener(bindCamera, executor)

        onDispose {
            runCatching {
                if (cameraProviderFuture.isDone) {
                    if (camera?.cameraInfo?.hasFlashUnit() == true) {
                        camera?.cameraControl?.enableTorch(false)
                    }
                    cameraProviderFuture.get().unbindAll()
                }
            }
            camera = null
        }
    }
}

private fun bindCameraUseCases(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    imageCapture: ImageCapture,
): Camera {
    val preview =
        Preview.Builder()
            .build()
            .also { cameraPreview ->
                cameraPreview.setSurfaceProvider(previewView.surfaceProvider)
            }

    cameraProvider.unbindAll()
    return cameraProvider.bindToLifecycle(
        lifecycleOwner,
        CameraSelector.DEFAULT_BACK_CAMERA,
        preview,
        imageCapture,
    )
}

private fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onCaptureComplete: (Uri) -> Unit,
    onCaptureError: (ImageCaptureException) -> Unit,
) {
    val photoDirectory = File(context.filesDir, PHOTO_DIRECTORY_NAME).apply {
        mkdirs()
    }
    val outputFile = File(photoDirectory, "picmorrow-${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onCaptureComplete(Uri.fromFile(outputFile))
            }

            override fun onError(exception: ImageCaptureException) {
                onCaptureError(exception)
            }
        },
    )
}

private const val PHOTO_DIRECTORY_NAME = "photos"

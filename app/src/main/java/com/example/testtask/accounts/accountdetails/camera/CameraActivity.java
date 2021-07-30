package com.example.testtask.accounts.accountdetails.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.testtask.R;
import com.google.android.gms.tasks.Tasks;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class CameraActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ImageView image;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        context = this;

        previewView = findViewById(R.id.previewView);
        image = findViewById(R.id.image);
        image.setColorFilter(context.getResources().getColor(R.color.black));
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindImageAnalysis(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));

        Button photo = findViewById(R.id.snapshot);
        photo.setOnClickListener(v -> {
            takePicture();
        });
    }

    @SuppressLint("RestrictedApi")
    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        ImageAnalysis.Builder builderImageAnalysis = new ImageAnalysis.Builder();

        ImageAnalysis imageAnalysis = builderImageAnalysis
            .setTargetResolution(new Size(previewView.getWidth(), previewView.getHeight()))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build();
        imageAnalysis.setAnalyzer(newSingleThreadExecutor(), new ImageAnalyser());

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageCapture.Builder builder = new ImageCapture.Builder();
        ImageCapture imageCapture = builder
            .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
            .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector,
                                       imageAnalysis, preview, imageCapture
        );
    }

    private class ImageAnalyser implements ImageAnalysis.Analyzer {

        public ImageAnalyser() {
        }

        @SuppressLint("UnsafeOptInUsageError")
        @Override
        public void analyze(@NonNull ImageProxy image) {
            Image img = image.getImage();

            Bitmap bitmap = toBitmap(img);
            runFaceContourDetection(bitmap, 270);
            image.close();
        }
    }

    private Bitmap toBitmap(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer yBuffer = planes[0].getBuffer();
        ByteBuffer uBuffer = planes[1].getBuffer();
        ByteBuffer vBuffer = planes[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        byte[] nv21 = new byte[ySize + uSize + vSize];
        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, image.getWidth(), image.getHeight(), null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 80, out);

        byte[] imageBytes = out.toByteArray();
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    private void takePicture() {

        //        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        //
        //        File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");
        //        ImageCapture.OutputFileOptions outputFileOptions =
        //            new ImageCapture.OutputFileOptions.Builder(file).build();
        //        imageCapture.takePicture(outputFileOptions, executor,
        //             new ImageCapture.OnImageSavedCallback() {
        //                 @Override
        //                 public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
        //                    Log.d("CameraActivity", "OK!");
        //                    Log.d("CameraActivity", String.valueOf(file.getTotalSpace()));
        //                     Bitmap bitmap = BitmapFactory.decodeFile(file.);
        //                    runOnUiThread(() -> Toast.makeText(context, "СФОТКАЛ, СФОТКАЛ!!", Toast.LENGTH_SHORT).show());
        //                 }
        //
        //                 @Override
        //                 public void onError(ImageCaptureException error) {
        //                    Log.d("CameraActivity", "DUDOS");
        //                    runOnUiThread(() -> Toast.makeText(context, "ПРОБЛЕМЫ!!", Toast.LENGTH_SHORT).show());
        //                 }
        //             }
        //        );
    }

    //    private Bitmap toBitmap(ImageProxy image) {
    //        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
    //        buffer.rewind();
    //        byte[] bytes = new byte[buffer.capacity()];
    //        buffer.get(bytes);
    //        byte[] clonedBytes = bytes.clone();
    //        return BitmapFactory.decodeByteArray(clonedBytes, 0, clonedBytes.length);
    //    }
    //
    //    private String getBatchDirectoryName() {
    //
    //        String app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
    //        File dir = new File(app_folder_path);
    //        if (!dir.exists()) {
    //            dir.mkdirs();
    //        }
    //
    //        return app_folder_path;
    //    }

    private void runFaceContourDetection(Bitmap mSelectedImage, int rotation) {
        if (mSelectedImage == null) {
            return;
        }

        InputImage image = InputImage.fromBitmap(mSelectedImage, rotation);
        FaceDetectorOptions options =
            new FaceDetectorOptions.Builder()
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .build();

        FaceDetector detector = FaceDetection.getClient(options);

        try {
            Tasks.await(detector.process(image)
                            .addOnSuccessListener(this::processFaceContourDetectionResult)
                            .addOnFailureListener(Throwable::printStackTrace));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void processFaceContourDetectionResult(List<Face> faces) {
        // Task completed successfully
        if (faces.size() == 0) {
            //runOnUiThread(() -> showAToast("No face"));
            Log.d("ImageAnaliser", "Face not found");
            if (image.getColorFilter().hashCode() == context.getResources().getColor(R.color.white)) {
                return;
            }

            image.setColorFilter(context.getResources().getColor(R.color.white));
            return;
        }

        //        for (Face face : faces){
        //            Rect bounds = face.getBoundingBox();
        //            float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
        //            float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees
        //
        //            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
        //            // nose available):
        //            FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
        //            if (leftEar != null) {
        //                PointF leftEarPos = leftEar.getPosition();
        //            }
        //
        //            // If contour detection was enabled:
        //            List<PointF> leftEyeContour =
        //                face.getContour(FaceContour.LEFT_EYE).getPoints();
        //            List<PointF> upperLipBottomContour =
        //                face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints();
        //
        //            // If classification was enabled:
        //            if (face.getSmilingProbability() != null) {
        //                float smileProb = face.getSmilingProbability();
        //            }
        //            if (face.getRightEyeOpenProbability() != null) {
        //                float rightEyeOpenProb = face.getRightEyeOpenProbability();
        //            }
        //
        //            // If face tracking was enabled:
        //            if (face.getTrackingId() != null) {
        //                int id = face.getTrackingId();
        //            }
        //        }

        Log.d("ImageAnaliser", "Face found");
        if (image.getColorFilter().hashCode() == context.getResources().getColor(R.color.purple_200)) {
            return;
        }

        image.setColorFilter(context.getResources().getColor(R.color.purple_200));

        //runOnUiThread(() -> showAToast("Face found!"));
    }
}

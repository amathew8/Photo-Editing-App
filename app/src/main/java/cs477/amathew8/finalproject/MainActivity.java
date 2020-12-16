package cs477.amathew8.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String currentPhotoPath;
    String path;
    private final int PICK_IMAGE_CODE = 100;
    public static final String OUTPUT_PHOTO_DIRECTORY = "Edit_Photos"; // Folder to save edited pictures in Gallery App

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = "";
    }

    public void onCameraRollButtonClicked(View v) {

        // If selecting from camera roll, open Gallery app
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }


    public void onCameraButtonClicked(View v) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    public void onActivityResult(int requestCode, int result, Intent data) {
        super.onActivityResult(requestCode, result, data);
        if (result == Activity.RESULT_OK) {
            if (requestCode == 1) {
                startEditingFromCamera();
            } else if (requestCode == PICK_IMAGE_CODE) {
                Uri inputImageUri = data.getData();
                startEditingFromPhotos(inputImageUri);
            }

        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void startEditingFromCamera() {
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        Intent editorIntent = new Intent(this, DsPhotoEditorActivity.class);
        editorIntent.setData(contentUri);
        editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, OUTPUT_PHOTO_DIRECTORY);
        int[] exclusions = {DsPhotoEditorActivity.TOOL_PIXELATE, DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_FILTER,
                DsPhotoEditorActivity.TOOL_DRAW, DsPhotoEditorActivity.TOOL_STICKER, DsPhotoEditorActivity.TOOL_VIGNETTE,DsPhotoEditorActivity.TOOL_ROUND,DsPhotoEditorActivity.TOOL_FRAME};
        editorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, exclusions);
        startActivityForResult(editorIntent, 200);
    }


    private void startEditingFromPhotos(Uri inputUri) {
        Uri inputImageUri = inputUri;
        if (inputImageUri != null) {
            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
            dsPhotoEditorIntent.setData(inputImageUri);
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, OUTPUT_PHOTO_DIRECTORY);

            // Unnecessary tools
            int[] exclusions = {DsPhotoEditorActivity.TOOL_PIXELATE, DsPhotoEditorActivity.TOOL_ORIENTATION, DsPhotoEditorActivity.TOOL_FILTER,
                    DsPhotoEditorActivity.TOOL_DRAW, DsPhotoEditorActivity.TOOL_STICKER, DsPhotoEditorActivity.TOOL_VIGNETTE,DsPhotoEditorActivity.TOOL_ROUND,DsPhotoEditorActivity.TOOL_FRAME};
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, exclusions);

            // Opens Photo Editor Activity
            startActivityForResult(dsPhotoEditorIntent, 200);

        }
    }

    public void onTutorialsButtonClicked(View v){
        Intent intent = new Intent(this,Tutorials.class);
        startActivity(intent);
    }

}
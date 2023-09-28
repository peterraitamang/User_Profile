package com.example.userprofile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userprofile.ROOM.Dao;
import com.example.userprofile.ROOM.Model;
import com.example.userprofile.ROOM.ViewModel;
import com.example.userprofile.databinding.ActivityAddUserProfileBinding;

import java.io.File;

public class AddUserProfileActivity extends AppCompatActivity {
    ActivityAddUserProfileBinding binding;
    private File output = null;
    private final int CAMERA_REQ_CODE = 100;
    private final int GALLERY_REQ_CODE = 101;
    ImageView imageView;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    TextView name, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        binding.customActionBar.ivBack.setOnClickListener(view -> finish());
        binding.customActionBar.actionBarTitle.setText("Add New User");

        binding.ivImage.setOnClickListener(v -> selectPhoto(AddUserProfileActivity.this));
        imageView = binding.ivImage;

        name = binding.etFullName;
        email = binding.etEmailId;
        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etFullName.getText().toString();
                String email = binding.etEmailId.getText().toString();
                if (!name.isEmpty() && !email.isEmpty()) {
                    viewModel.insert(new Model(name,null, email, null, null));
                    finish();

                }
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                if (extras != null) {
                    Bitmap img = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(img);
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Uri imageUri = result.getData().getData();
                imageView.setImageURI(imageUri);
            }
        });


    }



    private void selectPhoto(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Profile Picture");

        builder.setItems(options, (dialog, item) -> {
            if (item == 0) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 221);
                }
            } else if (item == 1) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 222);
                }
            } else if (item == 2) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void chooseFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(pickPhoto);
    }

    private void takePhoto() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePicture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 221) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                } else {
                    showPermissionExplanationDialog("Camera permission");
                }
            }
        } else if (requestCode == 222) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_IMAGES)) {
            } else {
                showPermissionExplanationDialog("Gallery access permission");
            }
        }
    }

    private void showPermissionExplanationDialog(String permissionName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required");
        builder.setMessage("You have denied " + permissionName + ". To use this feature, please allow the permission in the app settings.");

        builder.setPositiveButton("Go to Settings", (dialog, which) -> openAppSettings());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
        Toast.makeText(this, "Go to Permissions and allow permission", Toast.LENGTH_SHORT).show();
    }

}
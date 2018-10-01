package com.example.user.chat_oss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CaptureImage extends AppCompatActivity {

    private Button mUploadBtn;
    private ImageView mImageView;
    private Uri filepath;

    private static final int CAMERA_REQUEST_CODE = 1;

    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        storageReference = FirebaseStorage.getInstance().getReference();

        mUploadBtn = (Button)findViewById(R.id.upload);
        mImageView = (ImageView)findViewById(R.id.capture);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){

            final ProgressDialog mProgress = new ProgressDialog(this);

            mProgress.setTitle("Uploading Image...");
            mProgress.show();

            Uri uri = data.getData();

            StorageReference riversRef = storageReference.child("photos").child(uri.getLastPathSegment());

            riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgress.dismiss();

                    Toast.makeText(CaptureImage.this, "Uploading finished...", Toast.LENGTH_LONG).show();

                }
            });

        }
    }
}

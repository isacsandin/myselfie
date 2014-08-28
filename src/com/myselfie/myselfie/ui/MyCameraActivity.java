package com.myselfie.myselfie.ui;

import com.myselfie.myselfie.R;
import com.myselfie.myselfie.R.id;
import com.myselfie.myselfie.R.layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;

public class MyCameraActivity extends Fragment {
    private static final int CAMERA_REQUEST = 1888; 
    private ImageView imageView;

	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_camera, container, false);
	    this.imageView = (ImageView)view.findViewById(R.id.imageView1);
        Button photoButton = (Button) view.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
            }
        });
	    return view;
	}
    

    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            imageView.setImageBitmap(photo);
        }  
    } 
}

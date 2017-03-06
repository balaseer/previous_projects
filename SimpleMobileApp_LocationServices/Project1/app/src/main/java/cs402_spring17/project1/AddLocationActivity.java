package cs402_spring17.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class AddLocationActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText addLoc;
    private EditText locDescription;
    private TextView locName;
    private TextView locDesciption;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean imageTaken;
    private LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        location = (LatLng) extras.get("User_Location");

        locName = (TextView)findViewById(R.id.locationName);
        locDesciption = (TextView)findViewById((R.id.locDescription));
        addLoc = (EditText) findViewById(R.id.addLocName);
        locDescription = (EditText)findViewById(R.id.addLocDescription);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageTaken = false;
        Button addImage = (Button)findViewById(R.id.addImage);
        addImage.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View view){
            launchCameraIntent();
        }
    });

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick(View view){
             savePin();
    }
});

    }

    private void launchCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void savePin(){

       Building buildingAdded = new Building(addLoc.getText().toString(), location.latitude, location.longitude, locDescription.getText().toString());
        if(imageTaken) {
            buildingAdded.setImageTaken(((BitmapDrawable) imageView.getDrawable()).getBitmap());
            BuildingsController.addBuilding(buildingAdded);
            imageTaken = false;
        } else{
            BuildingsController.addBuilding(buildingAdded);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imageTaken = true;
        }
    }


}

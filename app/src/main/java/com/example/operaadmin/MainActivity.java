package com.example.operaadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.operaadmin.Model.SongModels;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    Spinner category_name;
    String image_url,url,id,category_sel;
    ImageView song_image;
    Button upload,load_prv;
    EditText artist_name,song_name;
    TextView tv_title,tv_album, tv_artist, tv_data, tv_duration;
    Uri audiouri;
    StorageTask storageTask;
    StorageReference storageReference;
    SongModels songModels;
    List<String> song_category,ids;
    List<SongModels> songModelsList;
    DatabaseReference referenceSong;
    MediaMetadataRetriever metadataRetriever;
    FirebaseStorage firebaseStorage;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        category_name = findViewById(R.id.category_name);
        upload = findViewById(R.id.upload_btn);
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.artist_name);
        tv_title = findViewById(R.id.title_view);
        tv_album = findViewById(R.id.album_view);
        tv_artist = findViewById(R.id.artist_view);
        tv_data = findViewById(R.id.dataa_view);
        tv_duration = findViewById(R.id.duration_view);
        song_image = findViewById(R.id.song_image);
        load_prv = findViewById(R.id.load_prv);
        song_category = new ArrayList<>();
        ids =new ArrayList<>();
        songModelsList =new ArrayList<>();
        referenceSong = FirebaseDatabase.getInstance().getReference().child("songs");
        storageReference = FirebaseStorage.getInstance().getReference().child("songs");
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Context context;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Please wait while we upload this song to your backend!");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

       // metadataRetriever = new MediaMetadataRetriever();
        final List<String> song_category = new ArrayList<>();
        song_category.add("HipHop");
        song_category.add("Rock");
        song_category.add("Sad");
        song_category.add("Punjabi");
        song_category.add("Back In 90's");
        song_category.add("Love");
        song_category.add("Hindi");
        song_category.add("English");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,song_category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_name.setHasTransientState(true);
        category_name.setAdapter(dataAdapter);

        category_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_sel = song_category.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        load_prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAudioFile(v);
            }
        });
//        song_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent photoPicker=new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                startActivityForResult(photoPicker,1);
//            }
//        });

//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                myFileIntent.setType("audio/*");
//                startActivityForResult(myFileIntent,1);

//                if (song_name.getText().toString().isEmpty()){
//                    song_name.setError("Required");
//                    song_name.requestFocus();
//                }else  if (category_sel==null){
//                    Toast.makeText(getApplicationContext(), "Category not filled", Toast.LENGTH_SHORT).show();
//                }else if (artist_name.getText().toString().isEmpty()){
//                    artist_name.setError("Required");
//                    artist_name.requestFocus();
//                }else if (url==null){
//                    Toast.makeText(getApplicationContext(), "Url not found", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    StoreImage(uri);
//                }
//            }
//        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebaseDatabase(v);
            }
        });
    }

    public void openAudioFile(View v){
        Intent myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("audio/*");
        startActivityForResult(myFileIntent,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data.getData() != null) {

            byte [] art;
            audiouri = data.getData();
            String filename = getFileName(audiouri);
            song_name.setText(filename);

            metadataRetriever = new MediaMetadataRetriever();
            metadataRetriever.setDataSource(this,audiouri);
            art = metadataRetriever.getEmbeddedPicture();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
//            song_image.setImageBitmap(bitmap);

            if (art != null){
                song_image.setImageBitmap(BitmapFactory.decodeByteArray(art,0,art.length));
                //metadataRetriever.release();
            }else {
                song_image.setImageResource(R.mipmap.ic_launcher);
            }

            if (metadataRetriever != null) {

                if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE) != null) {
                    tv_title.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                }

                if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM) != null) {
                    tv_album.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                }

                if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE) != null) {
                    tv_data.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
                }

                if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) != null) {
                    tv_duration.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
                }

                if (metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) != null) {
                    tv_artist.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                }
            }





        }
    }

    /*
    private void StoreImage(Uri uri) {
        id = String.valueOf(System.currentTimeMillis());
        StorageReference reference = storageReference.child("/"+id+".jpg");
        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        image_url = taskSnapshot.getMetadata().getPath();
                        taskSnapshot.getStorage().getDownloadUrl()
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = uri.toString();
                                        songModels = new SongModels(
                                                id,
                                                song_name.getText().toString(),
                                                artist_name.getText().toString(),
                                                "",
                                                url,
                                                category_sel,
                                                ""
                                        );
                                        uploadToDatabase(songModels);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    */

  /*  private void uploadToDatabase(SongModels songModels) {
        FirebaseDatabase
                .getInstance()
                .getReference("songs")
                .child(songModels.getId())
                .setValue(songModels)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, task.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }  */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String getFileName(Uri uri){
//        String result = null;
//        if (uri.getScheme().equals("content")){
//            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//
//            try {
//
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            }
//            finally {
//                cursor.close();
//            }
//        }
//        if (result == null){
//            result = uri.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut!= -1){
//                result = result.substring(cut+1);
//            }
//        }
//        return result;
        String result = null;
        String[] projection = {MediaStore.EXTRA_MEDIA_ALBUM,
                MediaStore.EXTRA_MEDIA_TITLE,
                MediaStore.EXTRA_MEDIA_GENRE,
                MediaStore.EXTRA_MEDIA_ARTIST};
        ContentResolver cr = getContentResolver();
        Cursor metaCursor = cr.query(uri, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    result = metaCursor.getString(0);
                }
            } finally {
                metaCursor.close();
            }
        }
        return result;
    }

    public void uploadToFirebaseDatabase(View v){
        if (storageTask != null && storageTask.isInProgress()){
            Toast.makeText(this, "Song upload is in process", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.show();
            uploadFiles();
        }
    }

    private String getFileExtension(Uri audiouri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audiouri));
    }

    private void uploadFiles() {
       /* if (audiouri != null){
            Toast.makeText(this, "Uploading Please wait!", Toast.LENGTH_SHORT).show();

            final String filename1 = System.currentTimeMillis()+"."+getFileExtension(audiouri);
            StorageReference storageReference1 = firebaseStorage.getReference();
            storageReference1.child("songFiles").child(filename1).putFile(audiouri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = uri.toString();
                            DatabaseReference reference = firebaseDatabase.getReference("test_song");
                            SongModels songModels1 = new SongModels(
                                    category_sel,
                                    tv_title.getText().toString(),
                                    tv_artist.getText().toString(),
                                    tv_duration.getText().toString(),
                                    image_url,
                                    tv_duration.getText().toString(),
                                    url
                            );
                            String uploadID = reference.push().getKey();
                            reference.child(uploadID).setValue(songModels1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                }
            });
        }else {
            Toast.makeText(this, "No file selected to upload!", Toast.LENGTH_SHORT).show();
        }*/
    }
}
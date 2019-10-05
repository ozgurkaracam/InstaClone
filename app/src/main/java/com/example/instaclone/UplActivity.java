package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instaclone.Data.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

public class UplActivity extends AppCompatActivity {
    ImageView imageView;
    Button buttunUpload;
    private StorageReference mStorageRef;
    ProgressDialog progressDialog;
    Snackbar snackbar;
    EditText commentText;

    Uri selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upl);
        imageView=findViewById(R.id.imageView);
        commentText=(EditText) findViewById(R.id.editTextComment);
        buttunUpload=findViewById(R.id.buttonUpload);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(UplActivity.this);
        if(selectedItem==null){
            buttunUpload.setEnabled(false);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes={"image/jpeg","image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,1);

            }
        });
        buttunUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttunUpload.setEnabled(false);
                mStorageRef = FirebaseStorage.getInstance().getReference();

                progressDialog.setMessage("Uploading Contuniue...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                mStorageRef.child("images/"+UUID.randomUUID().toString()+".jpg").putFile(selectedItem)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Snackbar.make(buttunUpload,"Successfull!",Snackbar.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                buttunUpload.setEnabled(true);
                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                                        Post post=new Post(uri.toString()
                                                , FirebaseAuth.getInstance().getCurrentUser().getEmail().toString()
                                                , commentText.getText().toString());
                                        databaseReference.child("posts").push().setValue(post);
                                        startActivity(new Intent(UplActivity.this,FeedActivity.class));
                                    }
                                });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UplActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                buttunUpload.setEnabled(true);
                            }
                        });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            buttunUpload.setEnabled(true);

            selectedItem=data.getData();
//            try {
//                Picasso.with(UplActivity.this).load(selectedItem).fit().centerCrop().into(imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            imageView.setImageURI(selectedItem);


        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(UplActivity.this,MainActivity.class));
            finish();
        }
        else if(item.getItemId()==R.id.action_resimyukle){
            startActivity(new Intent(UplActivity.this,UplActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

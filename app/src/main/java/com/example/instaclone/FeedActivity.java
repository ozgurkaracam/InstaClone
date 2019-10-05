package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.instaclone.Adapter.MyAdapter;
import com.example.instaclone.Data.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    TextView textView;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Post> posts;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(FeedActivity.this,MainActivity.class));
        }
        mRef=FirebaseDatabase.getInstance().getReference("posts");
        recyclerView=findViewById(R.id.recyclerView);
        posts=new ArrayList<>();
        getPosts();

        posts.add(new Post("https://firebasestorage.googleapis.com/v0/b/baboli-4f055.appspot.com/o/images%2F93844637-31e0-464e-be2a-89ea2ab23355.jpg?alt=media&token=44d52808-cf33-4197-a48d-6ce1efd087cf"
                ,firebaseUser.getEmail()
                ,"Hoşgeldiniz"));

        MyAdapter myAdapter=new MyAdapter(posts,FeedActivity.this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
            startActivity(new Intent(FeedActivity.this,MainActivity.class));
            finish();
        }
        else if(item.getItemId()==R.id.action_resimyukle){
            startActivity(new Intent(FeedActivity.this,UplActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    private void getPosts(){

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot post:dataSnapshot.getChildren()){

                        if(post.child("email").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            Log.e("sss",post.getValue().toString());
                            posts.add(new Post(
                                    post.child("imageUrl").getValue().toString()
                                    ,post.child("email").getValue().toString()
                                    , post.child("comment").getValue().toString()
                            ));
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}

package com.enescansabuncu.javainstagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.enescansabuncu.javainstagramclone.databinding.ActivityFeedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
private FirebaseAuth auth;
private FirebaseFirestore firebaseFirestore;
ArrayList<Post> postArrayList;
private ActivityFeedBinding binding;
PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
      binding=ActivityFeedBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.commentText), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            postArrayList=new ArrayList<>();
            auth=FirebaseAuth.getInstance();
            firebaseFirestore=FirebaseFirestore.getInstance();
            getData();
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            postAdapter=new PostAdapter(postArrayList);
            binding.recyclerView.setAdapter(postAdapter);
            return insets;
        });
    }
    private void getData(){
firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if(error!=null){
            Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } else if (value !=null) {
         for(DocumentSnapshot documentSnapshot: value.getDocuments()){
             Map<String, Object> data=documentSnapshot.getData();
             String userEamil=(String)data.get("usereamil");
             String comment=(String)data.get("comment");
             String url=(String) data.get("url");
             Post post =new Post(userEamil,comment,url);
             postArrayList.add(post);

         }
         postAdapter.notifyDataSetChanged();
        }
    }
});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
           Intent intent=new Intent(FeedActivity.this,Upload.class);
           startActivity(intent);


        } else if (item.getItemId()==R.id.sign_out) {
            Intent intent=new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intent);
            auth.signOut();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
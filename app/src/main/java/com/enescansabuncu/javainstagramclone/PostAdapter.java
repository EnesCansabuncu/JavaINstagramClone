package com.enescansabuncu.javainstagramclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enescansabuncu.javainstagramclone.databinding.RecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    ArrayList<Post>arrayList=new ArrayList<>();
    public PostAdapter(ArrayList<Post> arrayList){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
RecyclerRowBinding recyclerRowBinding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
return new PostHolder(recyclerRowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.binding.recyclerViewText.setText(arrayList.get(position).email);
        holder.binding.recyclerViewText.setText(arrayList.get(position).comment);
        Picasso.get().load(arrayList.get(position).url).into(holder.binding.recyclerVievImageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }

    class PostHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;

        public PostHolder(@NonNull RecyclerRowBinding binding) {

            super(binding.getRoot());
            this.binding=binding;
        }
    }
}

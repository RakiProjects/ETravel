package com.example.e_travel.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_travel.PanoramaCommentsActivity;
import com.example.e_travel.R;
import com.example.e_travel.model.Comment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    ArrayList<Comment> commentList;
    int userId ;
    Listener listener;
    AlertDialog.Builder alertDialogBuilder;
    Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> commentList, int userId, Listener listener) {
        this.context = context;
        this.commentList = commentList;
        this.userId = userId;
        this.listener = listener;

    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        CommentsViewHolder vh = new CommentsViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsViewHolder holder,  int position) {

        holder.userName.setText(commentList.get(position).getUserName());

        holder.commentTime.setText(commentList.get(position).getTime());

        holder.commentText.setText(commentList.get(position).getComment());


        //Log.v("PanoramaAdapter", String.valueOf(commentList.get(position).getUserId()));
        if(userId == commentList.get(position).getUserId()){
            holder.trash.setVisibility(View.VISIBLE);

            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Delete the comment?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listener.onDeleteClick(commentList.get(holder.getAdapterPosition()).getId());
                                    commentList.remove(holder.getAdapterPosition());
                                    view.setEnabled(false);
                                    notifyItemRemoved(holder.getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void updateCommentList(List<Comment> list){
        commentList.clear();
        commentList.addAll(list);
        notifyDataSetChanged();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView commentTime;
        TextView commentText;
        ImageView trash;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.comment_user_name);
            commentTime = itemView.findViewById(R.id.comment_time);
            commentText = itemView.findViewById(R.id.comment_text);
            trash = itemView.findViewById(R.id.trash);
        }
    }

    public interface Listener {
        void onDeleteClick(int commentId);
    }
}

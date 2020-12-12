package com.ps.webskitterassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.response.user_list_pojo.UserItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    Context mContext;
    List<UserItem> userList = new ArrayList<>();
    UserListClickListener userListClickListener;

    public UserListAdapter(Context mContext, List<UserItem> userList,UserListClickListener userListClickListener) {
        this.mContext = mContext;
        this.userList = userList;
        this.userListClickListener = userListClickListener;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_user_list, parent, false);
        return new UserListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        holder.tvNameUserListRow.setText(userList.get(position).getFirstName()+" "+userList.get(position).getLastName());
        holder.tvMailUserListRow.setText(userList.get(position).getEmail());
        Glide.with(mContext).load(userList.get(position).getAvatar())
                .placeholder(R.drawable.dummy_circle_profile).into(holder.ivPicUserListRow);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicUserListRow;
        TextView tvNameUserListRow,tvMailUserListRow;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicUserListRow=itemView.findViewById(R.id.ivPicUserListRow);
            tvNameUserListRow=itemView.findViewById(R.id.tvNameUserListRow);
            tvMailUserListRow=itemView.findViewById(R.id.tvMailUserListRow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userListClickListener.itemListClick(getAdapterPosition());
                }
            });
        }
    }

    public interface UserListClickListener {
        public void itemListClick(int position);
    }
}

package com.hisayan.fbase.user_interface.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hisayan.fbase.R;
import com.hisayan.fbase.objects.User;

import java.util.ArrayList;

/**
 * Created by hisayan on 10/29/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    //fields
    private Context context;

    private ArrayList<User> users;


    //constructor
    public UserListAdapter(Context context, ArrayList<User> users) {

        this.context = context;

        this.users = users;

    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_info,parent,false);

        return new UserViewHolder(view,context);
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        holder.bind(users.get(position));

    }


    @Override
    public int getItemCount() {
        return users.size();
    }




    class UserViewHolder extends RecyclerView.ViewHolder{

        //widgets
        private ImageView ivProfilePhoto;

        private TextView tvUsername;

        //fields
        private Context context;

        //contructor
        public UserViewHolder(View itemView,Context context) {
            super(itemView);

            ivProfilePhoto = (ImageView) itemView.findViewById(R.id.iv_profile_photo_item_user_info);

            tvUsername = (TextView) itemView.findViewById(R.id.tv_username_item_user_info);

            this.context = context;

        }


        //binds the user's fields to their corresponding widgets
        public void bind(User user){

            Glide.with(context).load(Uri.parse(user.getImage())).placeholder(R.mipmap.ic_launcher).into(ivProfilePhoto);

            tvUsername.setText(user.getDisplayName());

        }

    }

}

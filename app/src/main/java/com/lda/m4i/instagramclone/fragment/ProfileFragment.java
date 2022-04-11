package com.lda.m4i.instagramclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.lda.m4i.instagramclone.R;
import com.lda.m4i.instagramclone.activity.EditProfileActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public GridView gridViewProfile;
    private ProgressBar progressBar;
    private CircleImageView profileImage;
    private TextView tvPosts, tvFollowers, tvFollowing;
    private Button btnEditProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Initial configs
        gridViewProfile = view.findViewById(R.id.fragment_profile_gridViewProfile);
        progressBar = view.findViewById(R.id.fragment_profile_progressBarProfile);
        profileImage = view.findViewById(R.id.fragment_profile_iv_profilePhoto);
        tvPosts = view.findViewById(R.id.fragment_profile_tv_posts);
        tvFollowers = view.findViewById(R.id.fragment_profile_tv_followers);
        tvFollowing = view.findViewById(R.id.fragment_profile_tv_following);
        btnEditProfile = view.findViewById(R.id.fragment_profile_btn_editProfile);

        //open profile edit
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
package com.example.aluno2017.login;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class ProfileActivity extends Fragment {

    ImageView imageView;
    EditText editText;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_home, container, false);

        editText = rootView.findViewById(R.id.UserName);
        imageView = rootView.findViewById(R.id.UserPhoto);

        loadUserInformation();

        return rootView;
    }





    private void loadUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user.getPhotoUrl() != null) {
            Glide.with(ProfileActivity.this)
                    .load(user.getPhotoUrl().toString())
                    .into(imageView);
        }

        if (user.getDisplayName() != null) {
            editText.setText(user.getDisplayName());
        }

    }
}

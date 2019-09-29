package com.rbysoft.myovertimebd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Fragment_profile extends Fragment {

    View rootview;
    ImageView userimg;
    TextView emailtext,nameText;
    Button loginbutton;

    RadioGroup radioGroup;
    RadioButton banla,endlish;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_profile, container, false);
        userimg=rootview.findViewById(R.id.imageView);
        emailtext=rootview.findViewById(R.id.userEmailid);
        nameText=rootview.findViewById(R.id.userNameid);
        loginbutton=rootview.findViewById(R.id.loginbutton);
        radioGroup=rootview.findViewById(R.id.radiogroup);
        banla=rootview.findViewById(R.id.banglabutton);
        endlish=rootview.findViewById(R.id.englishbutton);




        String lang= Saving.getAString(getActivity(),"Language");
        if (lang.equals("bn")){
            banla.setChecked(true);

        }else{
            endlish.setChecked(true);
        }







       loginbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getActivity(),LoginSignup.class));
           }
       });




      radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup radioGroup, int i) {
              Toast.makeText(getActivity(), "Language Changed!!", Toast.LENGTH_SHORT).show();

              if (i==R.id.banglabutton){
                  Saving.saveAString(getActivity(),"Language","bn");
                  getActivity().recreate();

              }
              else if (i==R.id.englishbutton){
                  Saving.saveAString(getActivity(),"Language","en");
                  getActivity().recreate();


              }
          }
      });



        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            userimg.setVisibility(View.GONE);
            emailtext.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
            loginbutton.setVisibility(View.VISIBLE);

        }else{
            userimg.setVisibility(View.VISIBLE);
            emailtext.setVisibility(View.VISIBLE);
            nameText.setVisibility(View.VISIBLE);
            loginbutton.setVisibility(View.GONE);


            Picasso.with(getActivity()).load(user.getPhotoUrl()).fit().into(userimg);
            emailtext.setText("Email: "+user.getEmail());
            nameText.setText("Name: "+user.getDisplayName());


        }


        return rootview;

    }


}
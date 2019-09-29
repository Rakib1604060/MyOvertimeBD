package com.rbysoft.myovertimebd.Layout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rbysoft.myovertimebd.LoginSignup;
import com.rbysoft.myovertimebd.MyMainActivity;
import com.rbysoft.myovertimebd.R;
import com.rbysoft.myovertimebd.Saving;

public class SplashScrn extends AppCompatActivity {
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_scrn);
        iv=findViewById(R.id.iv);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();



//
//        Animation mine= AnimationUtils.loadAnimation(this,R.anim.transition);
//        iv.startAnimation(mine);
        ObjectAnimator animation = ObjectAnimator.ofFloat(iv, "translationY", -300f);
        animation.setDuration(1000);
        animation.start();

        boolean notFirsttime=Saving.getAboolean(this,"isFirstTime");
         final Intent i;

       if (user==null&& notFirsttime==false){
            Saving.SaveABoolean(this,"isFirstTime",true);
            i=new Intent(this, LoginSignup.class);
       }else {
            i=new Intent(this,MyMainActivity.class);
       }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(i);
                finish();
            }
        },1500);

    }
}

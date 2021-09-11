package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;

public class IntentReciver extends AppCompatActivity {

    ImageView imageView;
    boolean show = false;
    ConstraintLayout cc1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_reciver);

        cc1 = findViewById(R.id.cc1);

        imageView = findViewById(R.id.backgroundImage);

        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show)
                    revertAnimation();
                else
                    showAnimation();
            }
        });*/
        showAnimation();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        showAnimation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        revertAnimation();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        showAnimation();

    }

    private void showAnimation() {
        show = true;

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this, R.layout.activity_intent_reciver2);

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(1200);

        TransitionManager.beginDelayedTransition(cc1, transition);
        constraintSet.applyTo(cc1);
    }

    private void revertAnimation() {
        show = false;

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this, R.layout.activity_intent_reciver);

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateInterpolator(1.0f));
        transition.setDuration(1200);

        TransitionManager.beginDelayedTransition(cc1, transition);
        constraintSet.applyTo(cc1);

    }


    public void onClickImage(View view) {
        if (show)
            revertAnimation();
        else
            showAnimation();
    }

}

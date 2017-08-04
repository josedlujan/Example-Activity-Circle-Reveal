package circular.reveal.ejemplo.com.ejemplotransitionrevealcircular;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_CIRCULAR_REVEAL_X =
            "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y =
            "EXTRA_CIRCULAR_REVEAL_Y";
    View layout;
    private int revealX,revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent intent = getIntent();
        layout = findViewById(R.id.layout);


        if (savedInstanceState == null &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                        intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X,0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y,0);

            ViewTreeObserver viewTreeObserver = layout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            layout.setVisibility(View.VISIBLE);
        }

    }


    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(layout.getWidth(), layout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(layout, x, y, 0, finalRadius);
            circularReveal.setDuration(4000);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            layout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }


    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(layout.getWidth(), layout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    layout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unRevealActivity();
    }
}

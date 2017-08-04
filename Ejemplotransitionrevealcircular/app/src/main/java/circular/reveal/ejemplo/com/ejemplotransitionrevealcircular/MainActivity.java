package circular.reveal.ejemplo.com.ejemplotransitionrevealcircular;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(view);
            }
        });
    }



    void openActivity(View view) {
        // previously invisible view
        //final View myView = findViewById(R.id.myview);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,"transition");
        // get the center for the clipping circle
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;




        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_X,cx);
        intent.putExtra(SecondActivity.EXTRA_CIRCULAR_REVEAL_Y,cy);
        ActivityCompat.startActivity(this,intent,optionsCompat.toBundle());


    }
}

package cs477.amathew8.finalproject;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;





public class Tutorials extends AppCompatActivity{


    public final static String LINK = "cs477.amathew8.finalproject.LINK";
    public final static String TYPE = "cs477.amathew8.finalproject.TYPE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

    }

    public void onContrastButtonClicked(View v){
        Intent intent = new Intent(this,Video.class);
        String link = "E-8NvY43h2Y";
        intent.putExtra(LINK,link);
        intent.putExtra(TYPE,"CONTRAST");
        startActivity(intent);
    }

    public void onSaturationButtonClicked(View v){
        Intent intent = new Intent(this,Video.class);
        String link = "CWUYG4BqNoQ";
        intent.putExtra(LINK,link);
        intent.putExtra(TYPE,"SATURATION");
        startActivity(intent);
    }
    public void onFilterButtonClicked(View v){
        Intent intent = new Intent(this,Video.class);
        String link = "byHFmHEhAnE";
        intent.putExtra(LINK,link);
        intent.putExtra(TYPE,"FILTERS");
        startActivity(intent);
    }
    public void onVignetteButtonClicked(View v){
        Intent intent = new Intent(this,Video.class);
        String link = "j5ltepMQ8I4";
        intent.putExtra(LINK,link);
        intent.putExtra(TYPE,"VIGNETTE");
        startActivity(intent);
    }
}

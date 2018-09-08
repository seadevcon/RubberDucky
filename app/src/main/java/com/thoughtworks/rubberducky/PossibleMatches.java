package com.thoughtworks.rubberducky;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.camerafragment.widgets.RecordButton;

public class PossibleMatches extends AppCompatActivity {




//    You can go back to the previous activity by just calling finish() in the activity you are on. Note any code after the finish() call will be run - you can just do a return after calling finish() to fix this.
//
//    If you want to return results to activity one then when starting activity two you need:
//
//    startActivityForResults(myIntent, MY_REQUEST_CODE);
//
//    Inside your called activity you can then get the Intent from the onCreate() parameter or used
//
//    getIntent();
//
//    To set return a result to activity one then in activity two do
//
//    setResult(Activity.RESULT_OK, MyIntentToReturn);
//
//    If you have no intent to return then just say
//
//    setResult(Activity.RESULT_OK);
//
//    If the the activity has bad results you can use Activity.RESULT_CANCELED (this is used by default). Then in activity one you do
//
//    onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Handle the logic for the requestCode, resultCode and data returned...
//    }
//
//    To finish activity two use the same methods with finish() as described above with your results already set.
//
//


    Button yesButton;
    Button noButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_possible_matches);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();


        yesButton = findViewById(R.id.match);
        noButton = findViewById(R.id.no_match);

        yesButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", true);
                returnIntent.putExtra("going_back", true);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        noButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", false);
                returnIntent.putExtra("going_back", true);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}

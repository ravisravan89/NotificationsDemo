package com.ravisravan.wearable.notifications;

import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message = (TextView) findViewById(R.id.message);
        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
        String textmessage = "";
        if (remoteInput != null) {
            textmessage = "Your reply was : "+remoteInput.getCharSequence("MESSAGE").toString();

        } else {
            textmessage = getIntent().getStringExtra("MESSAGE");
        }
        message.setText("Hurray!! it worked! \n"+textmessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the ic_action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ic_action bar item clicks here. The ic_action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

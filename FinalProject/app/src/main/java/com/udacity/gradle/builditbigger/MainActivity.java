package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.GamesMetadata;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.leontheprofessional.backend.myApi.MyApi;

import java.io.IOException;
// import com.leontheprofessional.JokeTellerJavaLib;
// import com.leontheprofessional.joketeller.AndroidLibJokeTellerActivity;


public class MainActivity extends AppCompatActivity {

    private static final String LOG = MainActivity.class.getSimpleName();

    private AdView adView = null;
    private static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        // Tell a joke from a Java library
        /*
        JokeTellerJavaLib jokeTellerJavaLib = new JokeTellerJavaLib();
        Toast.makeText(this, jokeTellerJavaLib.TellAJokeFromJavaLib(), Toast.LENGTH_SHORT).show();
        */

        // Tell a joke from an Android library
        /*
        Intent jokeIntent = new Intent(MainActivity.this, AndroidLibJokeTellerActivity.class);
        jokeIntent.putExtra("This is a joke sent via Extra", "joke");
        startActivity(jokeIntent);
        */


        if (BuildConfig.FLAVOR == "paid") {
            adView = (AdView) findViewById(R.id.adView);
            adView.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
    }

    public void tellAJokeRemotely(View view){
        progressBar.setVisibility(View.VISIBLE);
        new EndpointAsyncTask().execute(new Pair<Context, String>(this, "Leon"));
    }


    public static class EndpointAsyncTask extends AsyncTask<Pair<Context, String>, Integer, String> {

        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {   // Only do this once
            /*
            MyApi.Builder builder =new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
                    */
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://elated-bus-127217.appspot.com/_ah/api/");

                // end options for devappserver
                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException exception) {
                return exception.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            Log.v(LOG, "values[0]: " + values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}

package com.example.whambuoy.internetconnect;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whambuoy.internetconnect.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        // TODO (2) Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        // TODO (3) Surround the call to getResponseFromHttpUrl with a try / catch block to catch an IO Exception
        String githubSearchResults = null;

        // TODO (8) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new GithubQuerryTask().execute(githubSearchUrl);
        // TODO (7) Delete the try and catch blocks
        /*
        try {
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
            mSearchResultsTextView.setText(githubSearchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    // TODO (4) Create a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
    public class GithubQuerryTask extends AsyncTask <URL, Void, String>{

        // TODO (5) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)

        @Override
        protected String doInBackground(URL...urls){
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try{
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        // TODO (6) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String s){
            if (s!= null && !s.equals("")){
                mSearchResultsTextView.setText(s);
            }
        }

    }
    //To create the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    //Handle menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search){
            //makeGithubSearchQuery when the search menu item is clicked
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.whambuoy.internetconnect;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whambuoy.internetconnect.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;
    // TODO (14) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageDisplay;

    // TODO (20) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        // TODO (15) Get a reference to the error TextView using findViewById
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (21) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

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
    // TODO (16) Create a method called showJsonDataView to show the data and hide the error
    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    // TODO (17) Create a method called showErrorMessage to show the error and hide the data
    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // First, hide the currently visible data
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    // TODO (4) Create a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
    public class GithubQuerryTask extends AsyncTask <URL, Void, String>{

        // TODO (22) Override onPreExecute to set the loading indicator to visible
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

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

            // TODO (23) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            
            if (s!= null && !s.equals("")){
                // TODO (19) Call showJsonDataView if we have valid, non-null results
                showJsonDataView();
                mSearchResultsTextView.setText(s);
            } else {
                // TODO (18) Call showErrorMessage if the result is null in onPostExecute
                showErrorMessage();
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

package com.rajesh.popularmovies.details;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import com.rajesh.popularmovies.R;
import com.rajesh.popularmovies.network.UnauthorizedException;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class FetchMovieElements extends AsyncTask<Integer, Void, Map<String, String>> {
    protected Activity mActivity;
    protected DataSetUpdateListener listener;
    private ProgressDialog pd;
    private boolean unauthorizedExceptionOccurred = false;

    public FetchMovieElements(Activity activity, DataSetUpdateListener listener) {
        mActivity = activity;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(mActivity);
        pd.setTitle("Loading elements...");
        pd.setMessage(mActivity.getString(R.string.dialog_progress_message));
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
    }

    @Override
    protected Map<String, String> doInBackground(Integer... params) {
        MovieServerConnector connector = setServerConnector(params);
        Map<String, String> trailers;
        try {
            trailers = connector.getElements();
        } catch (IOException | JSONException e) {
            // TODO: Display error message
            Log.e("", "Error occurred while parsing data...: " + e.toString());
            return new HashMap<>();
        } catch (UnauthorizedException e) {
            unauthorizedExceptionOccurred = true;
            return new HashMap<>();
        }

        return trailers;
    }

    @NonNull
    protected abstract MovieServerConnector setServerConnector(Integer[] params);

    @Override
    protected void onPostExecute(Map<String, String> trailers) {
        if (unauthorizedExceptionOccurred) {
            final EditText apiKeyEditText = new EditText(mActivity);

            apiKeyEditText.setHint(mActivity.getString(R.string.dialog_authorization_hint));

            new AlertDialog.Builder(mActivity)
                    .setTitle(mActivity.getString(R.string.dialog_authorization_title))
                    .setMessage(mActivity.getString(R.string.dialog_authorization_body))
                    .setView(apiKeyEditText)
                    .setPositiveButton(mActivity.getString(R.string.dialog_authorization_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String apiKey = apiKeyEditText.getText().toString();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("api-key", apiKey).commit();
                            editor.commit();
                            mActivity.finish();
                            mActivity.startActivity(mActivity.getIntent());
                        }
                    })
                    .setNegativeButton(mActivity.getString(R.string.dialog_authorization_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mActivity.finish();
                            mActivity.startActivity(mActivity.getIntent());
                        }
                    })
                    .show();
        }
        listener.onDataSetUpdated(trailers);
        if (pd != null) {
            pd.dismiss();
        }
    }
}

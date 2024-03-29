package com.excilys.formation.parlezvous;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.excilys.formation.parlezvous.utils.InputStreamToString;

public class ParlezVousActivity extends Activity {

	public static final String SERVER = "192.168.1.20";

	private final String TAG = ParlezVousActivity.class.getSimpleName();

	private EditText usernameField;
	private EditText passwordField;
	private Button cleanButton;
	private Button sendButton;
	private ProgressBar loading;
	private TextView errorMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate!");
		setContentView(R.layout.activity_main);

		usernameField = (EditText) findViewById(R.id.username_field);
		passwordField = (EditText) findViewById(R.id.password_field);
		cleanButton = (Button) findViewById(R.id.clean_button);
		sendButton = (Button) findViewById(R.id.send_button);
		loading = (ProgressBar) findViewById(R.id.loading);
		errorMessage = (TextView) findViewById(R.id.error_message);

		cleanButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				usernameField.setText("");
				passwordField.setText("");
			}
		});

		sendButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (hasEmptyFields()) {
					errorMessage.setVisibility(View.VISIBLE);
				} else {
					String username = usernameField.getText().toString();
					String password = passwordField.getText().toString();
					new ParlezVousTask().execute(username, password);
				}
			}
		});

	}

	private boolean hasEmptyFields() {
		return TextUtils.isEmpty(usernameField.getText()) || TextUtils.isEmpty(passwordField.getText());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause!");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart!");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(TAG, "onRestoreInstanceState!");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume!");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i(TAG, "onSaveInstanceState!");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy!");
	}

	private class ParlezVousTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPreExecute() {
			loading.setVisibility(View.VISIBLE);
		}

		@Override
		protected Boolean doInBackground(String... params) {

			String username = params[0];
			String password = params[1];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://" + SERVER + ":9000/connect/" + username + "/" + password);

			String content = null;
			try {
				HttpResponse response = client.execute(httpGet);
				content = InputStreamToString.convert(response.getEntity().getContent());
				publishProgress(content);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return Boolean.valueOf(content);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			loading.setVisibility(View.INVISIBLE);
			String message;
			if (result) {
				message = "Utilisateur connecté!";
				Intent intent = new Intent(ParlezVousActivity.this, ParlezVousActivity2.class);
				startActivity(intent);
			} else {
				message = "Vous n'êtes pas connecté!";
			}
			Toast.makeText(ParlezVousActivity.this, message, Toast.LENGTH_SHORT).show();
		}

	}

}
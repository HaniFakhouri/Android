package com.example.privatelecturemp3player;

import com.example.privatelecturemp3player.AudioService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Hani Fakhouri
 *
 */

public class MainActivity extends Activity {

	private Button btnPrevious, btnPlay, btnNext, btnStop, btnPause;
	private TextView txtTrackName;
	
	private AudioService audioService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnPrevious = (Button)findViewById(R.id.btnPrev);
		btnPlay = (Button)findViewById(R.id.btnPlay);
		btnNext = (Button)findViewById(R.id.btnNext);
		btnStop = (Button)findViewById(R.id.btnStop);
		btnPause = (Button)findViewById(R.id.btnPause);
		
		txtTrackName = (TextView)findViewById(R.id.txtTrackName);
		txtTrackName.setText("");
		
		btnPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioService.play();
				txtTrackName.setText("Track: " + 
						audioService.getPlayingTrackName());
				btnPlay.setEnabled(false);
				btnPrevious.setEnabled(true);
				btnNext.setEnabled(true);
				btnStop.setEnabled(true);
				btnPause.setEnabled(true);
			}
		});
		
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioService.previous();
				txtTrackName.setText("Track: " + 
						audioService.getPlayingTrackName());
			}
		});
		
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioService.next();
				txtTrackName.setText("Track: " + 
						audioService.getPlayingTrackName());
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioService.stop();
				txtTrackName.setText("Track: " + 
						audioService.getPlayingTrackName());
				initButtonsState();
			}
		});
		
		btnPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				audioService.pause();
				txtTrackName.setText("Track: " + 
						audioService.getPlayingTrackName());
				btnPlay.setEnabled(true);
				btnPrevious.setEnabled(false);
				btnNext.setEnabled(false);
				btnStop.setEnabled(true);
				btnPause.setEnabled(false);
			}
		});
		
		initButtonsState();
		
	}
	
	private void initButtonsState() {
		btnPlay.setEnabled(true);
		btnPrevious.setEnabled(false);
		btnNext.setEnabled(false);
		btnStop.setEnabled(false);
		btnPause.setEnabled(false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(this, AudioService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LocalBinder binder = (LocalBinder) service;
            audioService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };
	
}

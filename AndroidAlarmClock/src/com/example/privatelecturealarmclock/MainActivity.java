package com.example.privatelecturealarmclock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

/**
 * 
 * @author Hani Fakhouri
 *
 */

public class MainActivity extends Activity {

	private TextView txtTime;
	private TimePicker timePicker;
	private Button btnSetAlarm;
	private ListView lview;
    private List<AlarmTime> alarms;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		alarms = new ArrayList<AlarmTime>();		
		
		txtTime = (TextView)findViewById(R.id.txtCurrentTime);
		timePicker = (TimePicker)findViewById(R.id.timePicker);
		btnSetAlarm = (Button)findViewById(R.id.btnSetAlarm);
		lview = (ListView)findViewById(R.id.list);
		
		ArrayAdapter<AlarmTime> adapter = new ArrayAdapter<AlarmTime>(
				this, 
				android.R.layout.simple_list_item_1,
				alarms);
		lview.setAdapter(adapter);
		
		timePicker.setIs24HourView(true);
		
		Calendar c = Calendar.getInstance();
		int minutes = c.get(Calendar.MINUTE);
		int hours = c.get(Calendar.HOUR_OF_DAY);
		
		timePicker.setCurrentMinute(minutes);
		timePicker.setCurrentHour(hours);
		
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
			}
		});
		
		btnSetAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlarmTime newAlarm = 
						new AlarmTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
				
				if (!alarms.contains(newAlarm))
					alarms.add(newAlarm);
				
				// list the times ascending
				Collections.sort(alarms);
				
				// Update the list view
				((BaseAdapter) lview.getAdapter()).notifyDataSetChanged();
				
				Toast.makeText(getApplicationContext(), "Alarm Set. Click on the list to remove it.", Toast.LENGTH_SHORT).show();
			}
		});
		
		lview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				alarms.remove(position);
				// Update the list view
				((BaseAdapter) lview.getAdapter()).notifyDataSetChanged();
			}
		});
		
		updateTime();
		
		Timer mTimer = new Timer(true);
		mTimer.schedule(new MyTimerTask(), 0, 1000);
		
	}
	
	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			txtTime.post(new Runnable() {
				@Override
				public void run() {
					updateTime();
				}
			});
		}
	}

	private void updateTime() {
		Calendar c = Calendar.getInstance();
		int seconds = c.get(Calendar.SECOND);
		int minutes = c.get(Calendar.MINUTE);
		int hours = c.get(Calendar.HOUR_OF_DAY);
		
		String sSec = String.valueOf(seconds);
		String sMin = String.valueOf(minutes);
		String sHr = String.valueOf(hours);
		
		if (seconds < 10)
			sSec = "0" + seconds;
		if (minutes < 10)
			sMin = "0" + minutes;
		if (hours < 10)
			sHr = "0" + sHr;
		
		txtTime.setText(sHr + " : " + sMin + " : " + sSec);
		
		if (checkAlarm(hours, minutes)) {
			Toast.makeText(this, "IT IS TIME!", Toast.LENGTH_SHORT).show();
			NotificationCompat.Builder builder =  
			        new NotificationCompat.Builder(this)  
			        .setSmallIcon(R.drawable.ic_launcher)  
			        .setContentTitle("Alarm " + hours + ":" + minutes)  
			        .setContentText("Time to wake up!");
			
			Intent notificationIntent = new Intent(this, MainActivity.class);  

			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,   
			        PendingIntent.FLAG_UPDATE_CURRENT);  

			builder.setContentIntent(contentIntent);  
			builder.setAutoCancel(true);
			builder.setLights(Color.BLUE, 500, 500);
			// Vibration pattern
			long[] pattern = {
					500,500,500,500,500,500,500,500,500,
					500,500,500,500,500,500,500,500,500};
			builder.setVibrate(pattern);
			
			builder.setStyle(new NotificationCompat.InboxStyle());
			Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			builder.setSound(alarmSound);
			
			NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
			manager.notify(1, builder.build());
			
			alarms.remove(0);
			// Update the list view
			((BaseAdapter) lview.getAdapter()).notifyDataSetChanged();
			
		}
		
	}
	
	private boolean checkAlarm(int hours, int minutes) {
		for (AlarmTime a : alarms) {
			if (a.getHours() == hours && a.getMinutes() == minutes)
				return true;
			if (a.getHours() > hours)
				return false;
			if (a.getHours() == hours)
				if (a.getMinutes() > minutes)
					return false;
		}
		return false;
	}
	
	private class AlarmTime implements Comparable<AlarmTime> {
		private int hours, minutes;
		public AlarmTime(int hours, int minutes) {
			this.hours = hours;
			this.minutes = minutes;
		}
		public int getHours() {
			return hours;
		}
		public int getMinutes() {
			return minutes;
		}

		public int compare(AlarmTime lhs, AlarmTime rhs) {
			if (lhs.getHours()==rhs.getHours() && lhs.getMinutes()==rhs.getMinutes())
				return 0;
			if (lhs.getHours()==rhs.getHours())
				if (lhs.getMinutes() > rhs.getMinutes())
					return 1;
				else
					return -1;
			if (lhs.getHours()>rhs.getHours())
				return 1;
			else
				return -1;
		}
		@Override
		public int hashCode() {
			String s = String.valueOf(hours) + String.valueOf(minutes);
			int h = 0;
			for (char c : s.toCharArray())
				h += (int)c;
			return h;
		}
		@Override
		public int compareTo(AlarmTime another) {
			return compare(this, another);
		}
		@Override
		public String toString() {
			String sHours = String.valueOf(hours);
			String sMinutes = String.valueOf(minutes);
			if (hours < 10)
				sHours = "0" + hours;
			if (minutes < 10)
				sMinutes = "0" + minutes;
			return sHours + " : " + sMinutes;
		}
		public boolean equals(Object another) {
			return this.compareTo((AlarmTime)another) == 0;
		}
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
	
}

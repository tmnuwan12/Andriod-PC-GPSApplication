package com.example.appxgpsmodule;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.example.appxgpsmodule.storage.ExternalStorageReader;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Start extends Activity {

	private ServerSocket server = null;
	private Handler serverHandler = null;
	private String serverStatus = "OFF";
	private final static int SERVER_PORT = 38000;
	
	private final static String EOD_FLAG = "END" + "\n";
	
//	private final String FILE_LOCATION = "/sdcard/GPSLogger/20140827.txt";
	
	private final static String FILE_LOCATION = "/sdcard/GPSLogger/";
	private final static String FILE_EXTENSION = ".txt";

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_gpscoordinates);
		serverHandler = new Handler();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publish_gpscoordinates, menu);
		return true;
	}

	/**
	 * @param view
	 */
	public void publishGpsCoordinates(View view) {

		/*
		 * Intent intent = new Intent(this, ServerSocket_.class);
		 * 
		 * startActivity(intent);
		 */
		new Thread(initializeServerSocket).start();

	}

	private Runnable initializeServerSocket = new Thread() {

		@Override
		public void run() {

			Socket serverChannel = null;

			try {
				server = new ServerSocket(SERVER_PORT);
				//server.setSoTimeout(1000 * 10);
				
				for(;;){
					serverChannel = server.accept();
					
					
					
					// Globals. serverChannel.getInputStream()
					Globals.socketIn = new Scanner(serverChannel.getInputStream());
					
					Globals.socketOut = new PrintWriter(
							serverChannel.getOutputStream());
					
					/*
					String out = "This is test data" + "\n";
					Globals.socketOut.print(out);
					Globals.socketOut.print(EOD_FLAG);
					Globals.socketOut.flush();
					
					*/
					
					
					ExternalStorageReader.readExStorage(new File(getFileName()), Start.this);
					
					//TODO: Read GPS data from the sd card and push it over usb cable.
					
					
					serverStatus = "Served client";
					serverHandler.post(showToast);
				}

			} catch (Exception e) {

				try {
					if(server !=null) {server.close();}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				serverStatus = "Error occured, shutting down the server";
				serverHandler.post(showToast);
			}

		}

	};
	
	
	
	/**
	 * @return - Current date.
	 */
	private static String getFileName(){
		Calendar today = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return FILE_LOCATION.intern() + dateFormat.format(today.getTime()) + FILE_EXTENSION.intern();
	}
	
	

	private Runnable showToast = new Thread() {

		@Override
		public void run() {

			Toast.makeText(Start.this, serverStatus,
					Toast.LENGTH_SHORT).show();
		}

	};
	

}

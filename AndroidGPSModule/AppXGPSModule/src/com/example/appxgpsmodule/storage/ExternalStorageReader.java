/**
 * 
 */
package com.example.appxgpsmodule.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.example.appxgpsmodule.Globals;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

/**
 * @author NThusitha
 * @Date 21-August-2014
 * 
 */
public class ExternalStorageReader {

	private final static String EOD_FLAG = "END" + "\n";

	/**
	 * Read file from the Android external storage.
	 * 
	 * @param file
	 */
	public static void readExStorage(File file, Context ctx) {

		if (isMediaStorageReadable()) {

			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));

				String line;
				try {
					while ((line = reader.readLine()) != null) {
						Globals.socketOut.print(line);
						Globals.socketOut.print("\n");
					}
					Globals.socketOut.print(EOD_FLAG);
					Globals.socketOut.flush();

				} catch (IOException e) {

					Toast.makeText(ctx,
							"error occured reading file from external storage",
							Toast.LENGTH_SHORT).show();
				}

			} catch (FileNotFoundException e) {

				Toast.makeText(ctx,
						"error occured reading from external storage",
						Toast.LENGTH_SHORT).show();
			}

		}else{
			Toast.makeText(ctx,
					"media storage not readable",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * @return - true if and only media has read/write capability.
	 */
	public static boolean isMediaStorageReadWritable() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;

	}

	/**
	 * @return - true if and only media storage is readable (only readable).
	 */
	public static boolean isMediaStorageReadable() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) || Environment.MEDIA_MOUNTED.equals(state)) {

			return true;
		}
		return false;
		

	}

}

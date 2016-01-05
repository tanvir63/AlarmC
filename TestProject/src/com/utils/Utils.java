package com.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

public class Utils {
	//play music file just like chidiya .mp3 file
	public static final void playDefaultNotificationSound(Context context) {
		Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		MediaPlayer mediaPlayer = new MediaPlayer();

		try {
			mediaPlayer.setDataSource(context, defaultRingtoneUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		try {
			return Patterns.EMAIL_ADDRESS.matcher(target).matches();
		} catch (NullPointerException exception) {
			return false;
		}
	}

	public static final String getVersion(Context context) throws NameNotFoundException {
		String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		return version;
	}

	public static final ProgressDialog getProgressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Deleting ...");
		progressDialog.setMessage("Please wait...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(true);
		progressDialog.setIndeterminate(true);
		return progressDialog;
	}

	public static final ProgressDialog getprogressDialog(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Processing ...");
		progressDialog.setMessage("Please wait ...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(true);
		progressDialog.setIndeterminate(true);
		return progressDialog;
	}

	private static String ROOT_DIRECTORY = "/sdcard/";

	public static void showAlertMessage(Context context, String message, int RId) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false).setPositiveButton(
				context.getResources().getString(RId), new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {

					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static void showAppExit(final Activity context, String title, String message, int RId) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message).setCancelable(false).setPositiveButton(
				context.getResources().getString(RId), new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.dismiss();
						context.finish();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(ROOT_DIRECTORY, fileName);
		return file.exists() && file.isFile();
	}

	public static void showAlertMessage(Context context, String title, int icon, String message, int RId) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(icon);
		builder.setMessage(message).setCancelable(false).setPositiveButton(
				context.getResources().getString(RId), new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.setTitle(title);
		alert.show();
	}

	public static AlertDialog.Builder getAlertDialog(Context context, String title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		return builder;
	}

	public static final String[] getUserNames(String text) {
		String[] ret = null;
		if (text != null) {
			Pattern p = Pattern.compile("@([_a-zA-Z0-9^]*):");
			Matcher m = p.matcher(text);
			List<String> ls = new ArrayList<String>();
			while (m.find()) {
				ls.add(m.group(1));
			}
			ret = new String[ls.size()];
			ls.toArray(ret);
		} else {
			ret = new String[0];
		}
		return ret;
	}

	public static String[] getLRLS(String text) {
		String[] ret = null;
		if (text != null) {
			Pattern p = Pattern.compile("\\b((https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])");
			Matcher m = p.matcher(text);
			List<String> ls = new ArrayList<String>();
			while (m.find()) {
				ls.add(m.group(1));
			}
			ret = new String[ls.size()];
			ls.toArray(ret);
		} else {
			ret = new String[0];
		}
		return ret;
	}

	public static byte[] getUTF8Data(Context context, String fileName) {
		byte[] ret = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			byte[] data = new byte[2048];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read;
			while ((read = is.read(data)) != -1) {
				baos.write(data, 0, read);
			}
			is.close();
			ret = baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getFileData(Context context, String fileName) {
		String output = "";
		try {
			InputStream is = context.getAssets().open(fileName);
			output = convertStreamToString(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static final boolean putBoolValue(Context context, String key, boolean value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).commit();
	}

	public static final boolean getBoolValue(Context context, String key, boolean defaultValue) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
	}

	public static final boolean putValue(Context context, String key, String value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).commit();
	}

	public static final String getValue(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
	}

	public static final List<String> getIDValues(Context context, String key) {
		List<String> ids = new ArrayList<String>();
		String values = PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
		if (!TextUtils.isEmpty(values)) {
			ids = Arrays.asList(values.split("\\s*,\\s*"));
		}
		return ids;
	}

	public static final boolean putLong(Context context, String key, long value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).commit();
	}

	public static final long getLong(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
	}

	public static final long getLongTime(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, System.currentTimeMillis());
	}

	public static final boolean putFirstTime(Context context, String key, boolean value) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).commit();
	}

	public static final boolean isFirstTime(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, true);
	}

	public static boolean isDeviceOnline(Context context) {
		boolean ret = true;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = cm.getActiveNetworkInfo();
		if (i == null || (!i.isConnected()) || (!i.isAvailable())) {
			ret = false;
		}
		return ret;
	}

	public static boolean isAblePhoneCall(Context context) {
		boolean isAble;
		TelephonyManager telephonyManager1 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager1.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			isAble = false;
		} else {
			isAble = true;
		}
		return isAble;
	}

	public static void loadSiteURL(Context context, String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		context.startActivity(i);
	}

	public static Bitmap getResizedBitmap(Context context, int resId, int newHeight, int newWidth) {
		BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resId);
		Bitmap bm = drawable.getBitmap();
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;

	}

	public static Bitmap getResizeBitmap(Context context, int res, int newHeight, int newWidth) {
		Options options = new BitmapFactory.Options();
		options.inScaled = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), res, options);
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	public static File downloadFile(String Url, String fileName) {
		File retFile = null;
		try {
			URL url = new URL(Url);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			urlConnection.connect();
			File SDCardRoot = Environment.getExternalStorageDirectory();
			File file = new File(SDCardRoot, fileName);

			if (file.createNewFile()) {
				file.createNewFile();
			}

			FileOutputStream fileOutput = new FileOutputStream(file);

			InputStream inputStream = urlConnection.getInputStream();

			int totalSize = urlConnection.getContentLength();
			int downloadedSize = 0;
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // used to store a temporary size of the
			// buffer

			while ((bufferLength = inputStream.read(buffer)) != -1) {
				fileOutput.write(buffer, 0, bufferLength);
				downloadedSize += bufferLength;
			}
			fileOutput.close();
			if (downloadedSize == totalSize) {
				retFile = file;
			}

		} catch (Exception e) {
			retFile = null;
			e.printStackTrace();
		}
		return retFile;

	}

	public static Bitmap loadBitmapFromView(View v, int width, int height) {
		Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
		v.draw(c);
		return b;
	}

	public static Uri getImageUri(Context context, Bitmap bitmap) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
		return Uri.parse(path);
	}

	public static File getScreenShot(View v) throws IOException {
		Bitmap bitmap;
		v.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v.getDrawingCache());
		return getFile(bitmap);
	}

	public static File getSessionScreenShot(View v) throws IOException {
		Bitmap bitmap;
		v.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v.getDrawingCache());
		// bitmap=getResizedBitmap(bitmap, (int)(bitmap.getHeight()*0.9),
		// (int)(bitmap.getWidth()*0.9));

		return getFile(bitmap);
	}

	public static File getFile(Bitmap bm) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + "screenshot.jpg");
		file.createNewFile();
		// write the bytes in file
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(bytes.toByteArray());

		return file;
	}

	public static File getFile(Bitmap bm, String fileName) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		// you can create a new file name "test.jpg" in sdcard folder.
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
		file.createNewFile();
		// write the bytes in file
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(bytes.toByteArray());

		return file;
	}

	public static final File ROOT = Environment.getExternalStorageDirectory();

	public static final String getFilePath(String fileName) {
		File ret = null;
		// String fileName = "orsys_pdf.pdf";
		try {
			File file = new File(ROOT, "NCTBBooks/");
			if (!file.exists()) {
				file.mkdirs();
			}
			ret = new File(file, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.getAbsolutePath();
	}

	public static String readData(Context context, String inFile) {
		String tContents = "";

		try {
			InputStream stream = context.getAssets().open(inFile);

			int size = stream.available();
			byte[] buffer = new byte[size];
			stream.read(buffer);
			stream.close();
			tContents = new String(buffer);
		} catch (IOException e) {
			// Handle exceptions here
		}

		return tContents;
	}

	public static void writeIntoTextFile(String data) {
		try {

			File myFile = null;
			String fileName = "session_data.txt";
			File file = new File(ROOT, "NCTBBooks/");
			if (!file.exists()) {
				file.mkdirs();
			}
			myFile = new File(file, fileName);

			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(data);
			myOutWriter.close();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getUniqueArray(List<String> input) {
		Set<String> tempArray = new HashSet<String>(input);
		String[] uniqueArray = tempArray.toArray(new String[tempArray.size()]);
		Arrays.sort(uniqueArray);
		return uniqueArray;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;

	}

	public static String getCenterData(Context context, String fileName) {
		String output = "";
		try {
			InputStream is = context.getAssets().open(fileName);
			output = convertStreamToString(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		Writer writer = new StringWriter();

		char[] buffer = new char[2048];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} finally {
			is.close();
		}
		String text = writer.toString();
		return text;
	}

	public static Bitmap readAssetsBitmap(Context context, String filename) throws IOException {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.inSampleSize = 4;
			Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(filename), null, options);
			if (bitmap == null) {
				throw new IOException("File cannot be opened: It's value is null");
			} else {
				return bitmap;
			}
		} catch (IOException e) {
			throw new IOException("File cannot be opened: " + e.getMessage());
		}

	}

	public static String[] getFileList(Context context, String path) {
		AssetManager assetManager = context.getAssets();
		String[] files = null;
		try {
			files = assetManager.list(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return files;
	}


	static long OFFSET = 2 * 60 * 60 * 1000;

	public static final String getLocalTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date(time * 1000 - OFFSET);
		return sdf.format(date);
	}

	public static final String getLocalDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
		// sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date(time * 1000 - OFFSET);
		return sdf.format(date);
	}

	private static long getSaudiArabiaTime(String timeStr) {
		long time = -1;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date date = sdf.parse(timeStr);
			time = date.getTime() - 3 * 3600 * 1000;
			Log.i("DREG", "Time =" + time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return time;
	}

	private static int getID(String timeStr) {
		int id = 0;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(getSaudiArabiaTime(timeStr));

			int day = cal.get(Calendar.DAY_OF_YEAR);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			id = day * 24 * 60 + hour * 60 + minute;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public static String getDeviceID(Context context) {
		String deviceID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if (TextUtils.isEmpty(deviceID)) {
			deviceID = "";
		}
		Log.i("DREG", "Device ID=" + deviceID);
		return deviceID;
	}

}

package com.trs.template.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trs.template.dao.DaoUtils;

public class NetUtils {

	protected static final String TAG = NetUtils.class.getSimpleName();

	Context context;
	DaoUtils daoUtils;
	RequestQueue requestQueue;
	Gson gson;

	public static final String DOMAIN = "http://sensoro-mocha.chinacloudapp.cn:9217";
	public static final String URL_REQUEST_BEACON = "/interface/beacons/list";
	public static final String URL_REQUEST_TIMESTAMP = "/interface/beacons/timestamp";

	public NetUtils(Context context, DaoUtils daoUtils) {
		this.context = context;
		this.daoUtils = daoUtils;
		gson = new Gson();
		requestQueue = Volley.newRequestQueue(context.getApplicationContext());
		requestQueue.start();

	}

//	public SightBeacon requestBeacon(String uuid, Integer major, Integer minor) {
//		if (uuid == null || major == null || minor == null) {
//			return null;
//		}
//		// search database first.
//		SightBeacon sightBeacon = daoUtils.querySightBeacon(uuid, major, minor);
//		if (sightBeacon != null) {
//			return sightBeacon;
//		}
//		// if no beacon in database, net request.in demo,just search beacon in
//		// database.
//		return null;
//	}

	private String getUrl(String api) {

		String url = DOMAIN + api;

		return url;
	}

	/**
	 * request all beacon config
	 * @param onAllBeaconListener result callback
	 * @return
	 */
	public boolean requestAllBeacons(final OnAllBeaconListener onAllBeaconListener) {
		if (onAllBeaconListener == null) {
			return false;
		}

		String url = getUrl(URL_REQUEST_BEACON);
		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Type type = new TypeToken<ArrayList<NetBeacon>>() {
				}.getType();
				ArrayList<NetBeacon> netBeacons = gson.fromJson(arg0, type);
				onAllBeaconListener.onAllBeacons(0, netBeacons);
			}
		};
		ErrorListener errorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				printLog("e", TAG, arg0.getMessage());
				onAllBeaconListener.onAllBeacons(1, null);
			}
		};
		requestQueue.add(new StringRequest(Method.GET, url, listener, errorListener));

		return true;
	}

	/**
	 * request timestamp
	 * @param sightID sight id
	 * @param onTimestampListener result callback
	 * @return true
	 */
	public boolean requestTimestamp(int sightID, final OnTimestampListener onTimestampListener) {
		if (onTimestampListener == null) {
			return false;
		}

		String url = getUrl(URL_REQUEST_TIMESTAMP);
		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				TimestampResponse timestampResponse = gson.fromJson(arg0, TimestampResponse.class);
				onTimestampListener.onTimestamp(0, timestampResponse);
			}
		};
		ErrorListener errorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				printLog("e", TAG, arg0.getMessage());
				onTimestampListener.onTimestamp(1, null);
			}
		};
		requestQueue.add(new StringRequest(Method.GET, url, listener, errorListener));

		return true;
	}

	/**
	 * request all beacon callback.
	 * @param  
	 * @return  
	 * @date 2015-10-14
	 * @author trs
	 */
	public interface OnAllBeaconListener {
		public void onAllBeacons(int status, List<NetBeacon> netBeacons);
	}

	/**
	 * request timestamp callback.
	 * @param  
	 * @return  
	 * @date 2015-10-14
	 * @author trs
	 */
	public interface OnTimestampListener {
		public void onTimestamp(int status, TimestampResponse timestampResponse);
	}

	public void printLog(String level, String tag, String msg) {
		if (msg == null) {
			Log.e(tag, "msg is null");
			return;
		}
		if (level.equals("i")) {
			Log.i(tag, msg);
		} else if (level.equals("e")) {
			Log.e(tag, msg);
		}
	}
}

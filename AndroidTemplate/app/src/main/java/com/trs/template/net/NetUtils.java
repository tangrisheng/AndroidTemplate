package com.trs.template.net;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import com.trs.template.dao.DaoUtils;

public class NetUtils {

    protected static final String TAG = NetUtils.class.getSimpleName();

    Context context;
    DaoUtils daoUtils;
    RequestQueue requestQueue;
    Gson gson;

    public static final String DOMAIN = "http://192.168.0.146:3000";
    public static final String URL_REQUEST_LOGIN = "/interface/user";

    private static NetUtils netUtils;

    private NetUtils(Context context, DaoUtils daoUtils) {
        this.context = context;
        this.daoUtils = daoUtils;
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.start();

    }

    public static NetUtils getInstance(Context context, DaoUtils daoUtils) {
        if (netUtils == null) {
            synchronized (NetUtils.class) {
                if (netUtils == null) {
                    netUtils = new NetUtils(context, daoUtils);
                }
            }
        }
        return netUtils;
    }

    private String getUrl(String api) {

        String url = DOMAIN + api;

        return url;
    }


    public boolean requestLogin(final String tel, final String password, final OnLoginListener onLoginListener) {

        if (tel == null || password == null) {
            return false;
        }

        String url = getUrl(URL_REQUEST_LOGIN);

        Listener<String> listener = new Listener<String>() {

            @Override
            public void onResponse(String arg0) {

                LoginResponse loginResponse = gson.fromJson(arg0, LoginResponse.class);
                onLoginListener.onLogin(0, loginResponse);
            }
        };
        ErrorListener errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                printLog("e", TAG, arg0.getMessage());
                onLoginListener.onLogin(1, null);
            }
        };
        StringRequest request = new StringRequest(Method.PUT, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tel", tel);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return  params;
            }

        };
        requestQueue.add(request);

        return true;
    }


    /**
     * request all beacon callback.
     *
     * @param
     * @author trs
     * @return
     * @date 2015-10-14
     */
    public interface OnAllBeaconListener {
        public void onAllBeacons(int status, List<NetBeacon> netBeacons);
    }

    public interface OnLoginListener {
        public void onLogin(int status, LoginResponse loginResponse);
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

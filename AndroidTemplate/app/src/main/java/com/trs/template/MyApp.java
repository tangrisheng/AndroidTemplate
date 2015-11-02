package com.trs.template;

import android.app.Application;
import android.content.Intent;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.beacon.kit.SensoroBeaconManager;
import com.trs.template.dao.DaoUtils;
import com.trs.template.net.NetUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/29.
 */

public class MyApp extends Application {

    // 0 管理;1 快递员
    public static final int APP_KIND = 1;

    public DaoUtils daoUtils;
    public NetUtils netUtils;
    SensoroBeaconManager sensoroBeaconManager;

    @Override
    public void onCreate() {
        daoUtils = new DaoUtils(getApplicationContext());
        netUtils = NetUtils.getInstance(this, daoUtils);
        initBeacon();
        super.onCreate();
    }

    private void initBeacon() {
        sensoroBeaconManager = SensoroBeaconManager.getInstance(this);
        sensoroBeaconManager.setBackgroundScanPeriod(5 * 1000);
        sensoroBeaconManager.setBackgroundBetweenScanPeriod(5 * 1000);
        sensoroBeaconManager.setForegroundScanPeriod(5 * 1000);
        sensoroBeaconManager.setForegroundBetweenScanPeriod(5 * 1000);
        sensoroBeaconManager.setBeaconManagerListener(new MyBeaconManagerListener());
    }

    class MyBeaconManagerListener implements BeaconManagerListener {

        @Override
        public void onNewBeacon(Beacon beacon) {

        }

        @Override
        public void onGoneBeacon(Beacon beacon) {

        }

        @Override
        public void onUpdateBeacon(ArrayList<Beacon> arrayList) {

        }
    }
}

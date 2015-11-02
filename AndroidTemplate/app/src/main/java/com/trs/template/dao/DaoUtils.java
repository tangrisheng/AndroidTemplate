package com.trs.template.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.sensoro.beacon.kit.Beacon;
import com.trs.template.dao.greendao.DaoMaster;
import com.trs.template.dao.greendao.DaoSession;
import com.trs.template.dao.greendao.User;
import com.trs.template.dao.greendao.UserDao;
import com.trs.template.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class DaoUtils {
    private static final String TAG = DaoUtils.class.getSimpleName();
    Gson gson;
    UserDao userDao;
    DaoMaster.OpenHelper openHelper;
    Context context;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;

    public DaoUtils(Context context) {
        this.context = context;
        gson = new Gson();
        openHelper = new DaoMaster.OpenHelper(context, "sight-demo", null) {

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    /**
     * delete all and restore beacons
     * @param
     * @return
     * @date 2015-10-13
     * @author trs
     */
    public boolean storeUser(List<User> netBeacons) {

//        if (netBeacons == null) {
//            return false;
//        }
//        // delete all.
//        userDao.deleteAll();
//        if (netBeacons.size() == 0) {
//            return true;
//        }
//        ArrayList<Beacon> dbBeacons = new ArrayList<Beacon>();
//        for (NetBeacon netBeacon : netBeacons) {
//            Beacon dbBeacon = new Beacon();
//            byte[] iconBytes = Base64Utils.decode(netBeacon.getIcon());
//            dbBeacon.setIcon(iconBytes);
//            Utils.generateUMM(netBeacon.getUmm(), dbBeacon);
//            dbBeacon.setTitle(netBeacon.getTitle());
//            dbBeacon.setContent(netBeacon.getContent());
//            dbBeacons.add(dbBeacon);
//            dbBeacon.setUrl(netBeacon.getUrl());
//        }
//        try {
//            userDao.insertInTx(dbBeacons);
//        } catch (Exception e) {
//            return false;
//        }

        return true;
    }

    /**
     * query beacons
     * @param
     * @return
     * @date 2015年10月13日
     * @author trs
     */
    public User queryUser(String name) {


        return null;
    }

    public boolean storeBeacon(List<Beacon> beacons, String user_id) {
        if (beacons == null) {
            return false;
        }



        return  true;
    }
}
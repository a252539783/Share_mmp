package com.our_company.iqiyi.Player;

import android.app.Application;
import android.content.Intent;

import com.our_company.iqiyi.R;
import com.our_company.iqiyi.Remote.Communicate.CurrentUserSettings;
import com.our_company.iqiyi.Remote.Communicate.WorkerThread;
import com.our_company.iqiyi.bean.ThemeInfo;

import java.util.Random;

import xiyou.mobile.User;

/**
 * Created by user on 2017/6/1.
 */

public class App extends Application {
    private WorkerThread mWorkerThread;

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(getApplicationContext());
            mWorkerThread.start();

            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }

    public static final CurrentUserSettings mAudioSettings = new CurrentUserSettings();
    @Override
    public void onCreate() {
        super.onCreate();
        initWorkerThread();
        startService(new Intent(this,ShareService.class));
        int []colorId={R.color.redPrimary,R.color.redPrimaryDark,
                R.color.pinkPrimary,R.color.pinkPrimaryDark,
                R.color.purplePrimary,R.color.purplePrimary,
                R.color.darkBluePrimary,R.color.darkBluePrimaryDark,
                R.color.lightBluePrimary,R.color.lightBluePrimaryDark,
                R.color.cyanPrimary,R.color.cyanPrimaryDark,
                R.color.tealPrimary,R.color.tealPrimaryDark,
                R.color.greenPrimary,R.color.greenPrimaryDark,
                R.color.yellowPrimary,R.color.yellowPrimaryDark,
                R.color.orangePrimary,R.color.orangePrimaryDark,
                R.color.deepOrangePrimary,R.color.deepOrangePrimaryDark,
                R.color.brownPrimary,R.color.brownPrimaryDark,
                R.color.greyPrimary,R.color.greyPrimaryDark,
                R.color.blueGreyPrimary,R.color.blueGreyPrimaryDark,
                R.color.blackPrimary,R.color.blackPrimary,
                R.color.lightGreenPrimary,R.color.lightGreenPrimaryDark};
        Random random=new Random();
        int i=Math.abs(random.nextInt()%(colorId.length/2));
        if(i%2==0)
        {
            ThemeInfo.getThemeInfo().setPrimaryColor(getResources().getColor(colorId[i]));
            ThemeInfo.getThemeInfo().setStatusBarColor(getResources().getColor(colorId[i+1]));
        }else {
            ThemeInfo.getThemeInfo().setPrimaryColor(getResources().getColor(colorId[i-1]));
            ThemeInfo.getThemeInfo().setStatusBarColor(getResources().getColor(colorId[i]));
        }
    }
}

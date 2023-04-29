package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.aidlserver.IMyAidlInterface;
import java.util.Timer;
import java.util.TimerTask;

// TimerService.java
public class TimerService extends Service {
    private static final String TAG = "TimerService:xwg";
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Timer mTimer;
    private int mCount;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return mBinder;
    }

     ITimerService.Stub mBinder = new ITimerService.Stub() {

        @Override
        public String sendMsg(){
            Log.i(TAG,"func-----------");
            return "fuck me";
        }

        @Override
        public void startTimer(final int interval, final int count) throws RemoteException {
            mTimer = new Timer();
            mCount = 0;
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCount++;
                            if (mCount > count) {
                                mTimer.cancel();
                            } else {
                                onTimerTick(mCount);
                                Log.i(TAG,"mCount:" + mCount);
                            }
                        }
                    });
                }
            }, 0, interval);
        }

        @Override
        public void stopTimer() throws RemoteException {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            mCount = 0;
        }

        @Override
        public int getCount() throws RemoteException {
            return mCount;
        }
    };

    private void onTimerTick(int count) {
        // 处理定时器事件
    }
}

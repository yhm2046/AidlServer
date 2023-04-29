package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数据服务器类
 */
public class DataService extends Service {
    private static final String TAG = "server-DataService:xwg";
    private Handler mHandler = new Handler();
    private List<IDataServiceCallback> mCallbacks = new CopyOnWriteArrayList<>();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() { //2s 执行一次
            String message = "485date-" + System.currentTimeMillis();
            Log.i(TAG,"mCallbacks size:" + mCallbacks.size());
            for (IDataServiceCallback callback : mCallbacks) {
                try {
                    callback.onMessageReceived(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mHandler.postDelayed(this, 2 * 1000);
        }
    };

    private final IDataService.Stub mBinder = new IDataService.Stub() { //返回给客户端
        @Override
        public void sendMessage(String message) {
            Log.d(TAG, "server send Message : " + message);
        }

        @Override
        public void registerCallback(IDataServiceCallback callback) {
            if (callback != null) {
                mCallbacks.add(callback);   //添加回调对象
            }
        }

        @Override
        public void unregisterCallback(IDataServiceCallback callback) {
            if (callback != null) {
                mCallbacks.remove(callback);    //删除回调对象
            }
        }
    };  //end IDataService.Stub mBinder

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mHandler.post(mRunnable);
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}


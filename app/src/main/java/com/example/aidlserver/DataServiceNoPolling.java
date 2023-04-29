package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 服务端被动接收
 */
public class DataServiceNoPolling extends Service {
    private static final String TAG = "sever-DataServiceNoPolling:xwg";
    private List<IDataServiceCallback> mCallbacks = new CopyOnWriteArrayList<>();
    private IDataService.Stub mBinder = new IDataService.Stub() {
        @Override
        public void sendMessage(String message) {
            Log.d(TAG, "Received message from client: " + message);
            for (IDataServiceCallback callback : mCallbacks) {
                try {
                    callback.onMessageReceived("I had received:" + message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void registerCallback(IDataServiceCallback callback) {
            mCallbacks.add(callback);
        }

        @Override
        public void unregisterCallback(IDataServiceCallback callback) {
            mCallbacks.remove(callback);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}


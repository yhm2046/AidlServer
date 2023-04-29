package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class TimerServiceTest extends Service {
    private static final String TAG = "TImerServiceTest:xwg";
    public TimerServiceTest() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    static class MyBinder extends ITimerService.Stub{
        @Override
        public void startTimer(int interval, int count) throws RemoteException {

        }

        @Override
        public void stopTimer() throws RemoteException {

        }

        @Override
        public int getCount() throws RemoteException {
            return 0;
        }

        @Override
        public String sendMsg() throws RemoteException {
            return "fuck you!";
        }
    }
}
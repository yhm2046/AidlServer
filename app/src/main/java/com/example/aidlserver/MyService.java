package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private static final String TAG = "MyService:xwg";
    public MyService() {
    }

    /**
     * 重载绑定，记得必须要返回MyBinder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind..");
       return new MyBinder();
    }
    class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public String get15RandomString() throws RemoteException {
            int length = 15;
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) { // 从字符集中随机选择一个字符，并将其添加到生成的字符串中
                sb.append(characters.charAt(random.nextInt(characters.length())));
            } return sb.toString();
        }

        /**
         * 生成15位数随机数
         * @param length    15
         * @param chaStr    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+"
         * @return  随机数
         * @throws RemoteException
         */
        @Override
        public String get15RandomStringPhrase(int length, String chaStr) throws RemoteException {
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) { // 从字符集中随机选择一个字符，并将其添加到生成的字符串中
                sb.append(chaStr.charAt(random.nextInt(chaStr.length())));
            }return sb.toString();
        }
//每隔2s发送485设备信息
        @Override
        public String sendMessage(){
            long countMsg = System.currentTimeMillis();
//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    Log.i(TAG,"System.currentTimeMillis():" + System.currentTimeMillis());
//                }
//            }, 0, 2 * 1000);
            return "send msg :" + countMsg;
        }//sendMessage

        private String scheduled485Message(){
            long date485msg = System.currentTimeMillis();
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG,"send 485 msg:" + System.currentTimeMillis());
                }
            }, 0, 2 * 1000);
            return "";
        }
    }
}
package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.security.SecureRandom;

public class MyService extends Service {
    public MyService() {
    }

    /**
     * 重载绑定，记得必须要返回MyBinder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
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


    }
}
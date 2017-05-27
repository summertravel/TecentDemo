package com.example.kxt.myapplication;

import android.util.Log;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by KXT on 2017/5/25.
 */

class MyReceviceMessageListener implements RongIMClient.OnReceiveMessageListener {
    private static final String TAG = "MyReceviceMessageListen";
    @Override
    public boolean onReceived(Message message, int i) {
        Log.e(TAG, "onReceived: Content ===" + message.getContent() );
        return true;
    }
}

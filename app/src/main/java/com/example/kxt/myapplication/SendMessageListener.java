package com.example.kxt.myapplication;

import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Message;

/**
 * Created by KXT on 2017/5/25.
 */

public class SendMessageListener implements RongIM.OnSendMessageListener {
    private static final String TAG = "SendMessageListener";
    @Override
    public Message onSend(Message message) {
        Log.e(TAG, "onSend: UId ==== "+message.getSenderUserId()+"TargetId===="+message.getTargetId());
        return message;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        return false;
    }
}

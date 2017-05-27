package com.example.kxt.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String uid;

    public List<UserBean> userBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userBeanList.add(new UserBean("1001", "真机", "http://img1.imgtn.bdimg.com/it/u=2771486430,3728602793&fm=15&gp=0.jpg"));
        userBeanList.add(new UserBean("1002", "模拟器", "http://img4.imgtn.bdimg.com/it/u=2614882483,1418045115&fm=15&gp=0.jpg"));

//        RongIM.setOnReceiveMessageListener(new MyReceviceMessageListener());
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                for (UserBean userbean : userBeanList) {
                    if (userbean.id.equals(s)) {
                        Log.e(TAG, "getUserInfo: =====" + Uri.parse(userbean.uri));
                        return new UserInfo(userbean.id, userbean.name, Uri.parse(userbean.uri));
                    }

                }
                return null;
            }
        }, true);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(Contants.TOKEN);
            }
        });
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(Contants.TOKEN2);
            }
        });
        findViewById(R.id.btn_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
//                    RongIM.getInstance().setSendMessageListener(new SendMessageListener());
                    if (uid != null) {

                        RongIM.getInstance().startPrivateChat(MainActivity.this, uid.equals("1001") ? "1002" : "1001", "hello");
                    }

                }
            }
        });
    }

    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "onSuccess: " + "Token 错误");

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                uid = userid;
                // 必须在连接成功以后调用
                RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
                RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
                Log.e(TAG, "onSuccess: " + userid);

            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "onError: " + errorCode);
                Toast.makeText(MainActivity.this, "onError: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

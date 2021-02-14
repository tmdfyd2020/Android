package org.techtown.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";

    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {  // SMS를 받으면 onReceive() 함수가 자동으로 호출된다.

        Log.i(TAG, "onReceive() 메서드 호출됨.");  // onReceive() 함수가 호출되었는지 알 수 있도록 로그 메시지를 출력

        Bundle bundle = intent.getExtras();  // Intent 객체 안에 들어 있는 Bundle 객체를 getExtras() 함수로 참조한다.
        // Bundle 객체 안에는 부가 데이터가 들어 있다.
        SmsMessage[] messages = parseSmsMessage(bundle);  // parseSmsMessage() 함수 호출하기

        if (messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();  // getOriginatingAddress() : 보낸 사람의 전화번호
            Log.i(TAG, "SMS sender : " + sender);

            String contents = messages[0].getMessageBody();  // getMessageBody() : 문자 내용을 확인
            Log.i(TAG, "SMS contents : " + contents);

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "SMS received date : " + receivedDate.toString());

            sendToActivity(context, sender, contents, receivedDate);  // 새로운 화면을 띄우기 위한 함수 호출하기 from SmsActivity.java
              // sendToActivity는 SmsActivity로 Intent를 보내려고 만든 함수이다.
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {  // 한 번 입력해놓으면 다른 앱 만들 때도 재사용할 수 있음

        // Bundle 객체에 들어가 있는 부가 데이터 중에서 pdus 가져오기
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCount = objs.length;
        for (int i = 0; i < smsCount; i++) {

            // 단말 OS 버전에 따라 다른 방식으로 메서드 호출하기
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Build.VERSION.SDK_INT : 단말의 OS 버전을 확인할 때 사용한다.
                // 안드로이드 OS 버전이 마시멜로(M) 버전과 같거나 그 이후 버전일 때 중괄호 안의 코드를 실행
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
                 // Intent 객체 안에 부가 데이터로 들어 있는 SMS 데이터를 확인하려면 SmsMessage 클래스의 createFromPdu() 함수를 사용하여 SmsMessage 객체로 변환
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }

    private void sendToActivity(Context context, String sender, String contents, Date receivedDate) {
        Intent myIntent = new Intent(context, SmsActivity.class);
        // Intent 객체를 만들 때 두 번째 인수로 SmsActivity 객체를 전달했으므로,
        // startActivity() 함수를 사용해 이 Intent를 시스템으로 전달하면 시스템이 Intent를 SmsActivity 쪽으로 전달한다.

        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Intent에 플래그 추가하기
        // 브로드캐스트 수신자는 화면이 없으므로 Intent의 Flag로 FLAG_ACTIVITY_NEW_TASK를 추가해야 한다.
        // 이미 메모리에 만든 SmsActivity가 있을 때 액티비티를 중복 생성하지 않도록 FLAG_ACTIVITY_SINGLE_TOP 플래그도 추가한다.

        myIntent.putExtra("sender", sender);
        myIntent.putExtra("contents", contents);
        myIntent.putExtra("receivedDate", format.format(receivedDate));

        context.startActivity(myIntent);
    }

}

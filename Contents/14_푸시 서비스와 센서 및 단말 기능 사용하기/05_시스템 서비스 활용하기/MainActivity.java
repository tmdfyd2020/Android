package org.techtown.manager;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);  // 프로세스 리스트 버튼
        // ActivityManager가 관리하는 앱의 프로세스 리스트를 확인하여 출력한다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServiceList();
            }
        });

        Button button2 = findViewById(R.id.button2);  // 현재 액티비티 버튼
        // ActivityManager를 통해 현재 액티비티에 대한 정보를 출력한다.
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentActivity();
            }
        });

        Button button3 = findViewById(R.id.button3);  // 앱 리스트 버튼
        // PackageManager가 관리하는 앱 리스트를 확인하도록 한다.
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppList();
            }
        });

        Button button4 = findViewById(R.id.button4);  // 액티비티 찾기 버튼
        // 지정한 액티비티가 설치되어 있는지 확인한다.
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findActivity();
            }
        });

        Button button5 = findViewById(R.id.button5);  // 알림 설정 버튼
        // AlarmManager를 이용해 1분 후 알림을 받을 수 있도록 한다.
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

    }

    public void getServiceList() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();  // 실행 중인 프로세스 확인을 위한 메서드 호출하기
        // getRunningAppProcesses()를 호출하면 앱 프로세스 목록을 확인할 수 있으며 각각의 정보를 아래쪽에 출력한다.

        for (int i = 0; i < processInfoList.size(); i++) {
            ActivityManager.RunningAppProcessInfo info = processInfoList.get(i);
            println("#" + i + " -> " + info.pid + ", " + info.processName);
        }
    }

    public void getCurrentActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(1);
        // getRunningTasks() : 이 메서드를 호출했을 때 반환되는 RunningTaskInfo 객체를 확인하면 액티비티 스택에 들어있는 액티비티 정보 중 가장 최상위 정보를 확인할 수 있다.
        // 다만 이 메서드는 API 버전 21부터는 정상적으로 동작하지 않을 수 있다.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityManager.RunningTaskInfo info = taskList.get(0);
            println("Running Task -> " + info.topActivity.toString());
        }
    }

    public void getAppList() {
        PackageManager manager = getPackageManager();
        List<ApplicationInfo> appInfoList = manager.getInstalledApplications(PackageManager.GET_META_DATA);
        // getInstalledApplications()를 호출했을 때 반환되는 ApplicationInfo 객체를 확인하면 어떤 앱이 설치되어 있는지 확인할 수 있다.

        for (int i = 0; i < appInfoList.size(); i++) {
            ApplicationInfo info = appInfoList.get(i);
            println("#" + i + " -> " + info.loadLabel(manager).toString() + ", " + info.packageName);
        }
    }

    public void findActivity() {
        // 지정한 인텐트 객체를 이용해 이 인텐트 객체로 실행할 액티비티가 있는지 확인한다.
        PackageManager manager = getPackageManager();

        Intent intent = new Intent(this, MainActivity.class);
        List<ResolveInfo> activityInfoList = manager.queryIntentActivities(intent, 0);

        for (int i = 0; i < activityInfoList.size(); i++) {
            ResolveInfo info = activityInfoList.get(i);
            println("#" + i + " -> " + info.activityInfo.applicationInfo.packageName);
        }
    }

    public void setAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 60000, pendingIntent);  // AlarmManager에 알람 등록하기

    }

    public void println(String data) {
        textView.append(data + "\n");
    }

}


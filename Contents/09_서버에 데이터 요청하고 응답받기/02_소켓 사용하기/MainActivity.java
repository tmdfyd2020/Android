package org.techtown.socket;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    TextView textView;
    TextView textView2;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = editText.getText().toString();

                /* 스레드 안에서 send() 메서드 호출하기 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        send(data);
                    }
                }).start();

            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 스레드 안에서 startServer() 메서드 호출하기 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();

            }
        });
    }

    public void send(String data) {
        try {
            int portNumber = 5001;  // 여기에서는 서버와 클라이언트가 5001번 포트를 사용하도록 한다.

            /* 소켓 객체 만들기 */
            Socket sock = new Socket("localhost", portNumber);  // 소켓은 IP주소와 포트 번호를 파라미터로 전달 받는다.
            printClientLog("소켓 연결함.");  // (2)

            /* 소켓 객체로 데이터 보내기 */
            ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());  // 문자열을 객체 그대로 보내기 위해 ObjectOutputStream 사용
              // But, 실제 앱을 만들 때는 DataInputStream과 DataOutputStream을 많이 사용한다.
            outstream.writeObject(data);
            outstream.flush();
            printClientLog("데이터 전송함.");  // (3)

            ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
            printClientLog("서버로부터 받음 : " + instream.readObject());
            sock.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startServer() {
        try {
            int portNumber = 5001;  // 포트 번호는 클라이언트에서 접속할 5001번을 그대로 사용한다.

            /* 소켓 서버 객체 만들기 */
            ServerSocket server = new ServerSocket(portNumber);
            printServerLog("서버 시작함 : " + portNumber);  // (1)

            while(true) {
                // while 구문을 사용해서 클라이언트의 접속을 기다리다가 클라이언트의 접속 요청이 왔을 때
                //   accept() 메서드를 통해 소켓 객체가 반환되므로 클라이언트 소켓의 연결 정보를 확인할 수 있다.

                /* 클라이언트가 접속했을 때 만들어지는 소켓 객체 참조하기 */
                Socket sock = server.accept();
                InetAddress clientHost = sock.getLocalAddress();
                int clientPort = sock.getPort();
                printServerLog("클라이언트 연결됨 : " + clientHost + " : " + clientPort);  // (4)

                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                Object obj = instream.readObject();
                printServerLog("데이터 받음 : " + obj);  // (5)

                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                outstream.writeObject(obj + " from Server.");
                outstream.flush();
                printServerLog("데이터 보냄.");  // (6)

                sock.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /* 클라이언트 쪽 로그를 화면에 있는 텍스트뷰에 출력하기 위해 핸들러 사용하기 */
    public void printClientLog(final String data) {
                            // append() 메서드로 전달될 파라미터는 printClientLog() 메서드로 전달되는 파라미터가 그대로 전달되어야 하므로 final로 정의한다.
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });

    }

    /* 서버 쪽 로그를 화면에 있는 텍스트뷰에 출력하기 위해 핸들러 사용하기 */
    public void printServerLog(final String data) {
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView2.append(data + "\n");
            }
        });
    }

}

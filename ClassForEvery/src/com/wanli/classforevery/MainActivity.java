package com.wanli.classforevery;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	//��������ϵ������ı���
	EditText input, show;
	//��������ϵ�һ����ť
	Button send;
	//��������������������д����
	static OutputStream os;
	//�ͻ����û��ǳ�
	private String nickName;
	//��¼�豸��imei��
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Context context = this.getBaseContext();
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		
		input = (EditText) findViewById(R.id.input);
		send = (Button) findViewById(R.id.send);
		show = (EditText) findViewById(R.id.show);
		
//		show.setText(imei);
		new Thread(new StartSocket(show, imei)).start();
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try {
					//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
					StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			        StrictMode.setThreadPolicy(policy);
			        //���û����ı��������������д������
					os.write(("1111," + imei + "," + input.getText().toString() + "\r\n").getBytes());
					//���input�ı���
					input.setText("");
				} catch (Exception e) {
					e.printStackTrace();
					Log.i("ioexception2", "ioexception2");
				}
			}
		});
	}

}

//���ͻ����û���¼��������������豸�������Ϣ
class SendImeiToServer implements Runnable {

	private String imei;
	
	public SendImeiToServer(String imei) {
		this.imei = imei;
	}
	
	@Override
	public void run() {
		if (MainActivity.os != null) {
			try {
				//���û����ı��������������д������
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
				StrictMode.setThreadPolicy(policy);
				MainActivity.os.write(("1111," + imei + "\r\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

class StartSocket implements Runnable {

	//����Socket�����������˽���ͨ��
	Socket s;
	EditText text;
	String imei;
	
	public StartSocket(EditText text, String imei) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.imei = imei;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			s = new Socket("192.168.191.1", 30000);
			//�ͻ�������ClientThread�̲߳��϶�ȡ���Է�����������
			new Thread(new ClientThread(s, text)).start();
			MainActivity.os = s.getOutputStream();
			new Thread(new SendImeiToServer(imei)).start();
		} catch (Exception e1) {
			e1.printStackTrace();
			Log.i("ioexception1", "ioexception1");
		}
	}
	
}

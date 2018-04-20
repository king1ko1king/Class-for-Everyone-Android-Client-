package com.wanli.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import android.util.Log;

public class ConnectionSocket implements Runnable {
	
	private String gateway;//���ص�IP��ַ
	public static Socket connSocket;//����Socket�����ںͷ���˽�������
	
	//��������������������˴�������
	public static OutputStream os;
	
	//�������������������շ���˵�����
	public static BufferedReader br;

	public ConnectionSocket(String gateway) {
		this.gateway = gateway;
	}

	@Override
	public void run() {
		try {	
			connSocket = new Socket(gateway, 30000);
			os = connSocket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("ioexception", "ioexcepiton2");
		}
	}
}

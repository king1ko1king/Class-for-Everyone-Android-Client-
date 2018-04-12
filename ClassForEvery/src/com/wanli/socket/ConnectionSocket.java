package com.wanli.socket;

import java.net.Socket;

import com.wanli.page.Login;

import android.util.Log;

public class ConnectionSocket implements Runnable {
	
	String gateway;//���ص�IP��ַ
	Socket connSocket;//����Socket�����ںͷ���˽�������
	
	public ConnectionSocket(String gateway) {
		this.gateway = gateway;
	}

	@Override
	public void run() {
		try {	
			connSocket = new Socket(gateway, 30000);
			Login.os = connSocket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("ioexception", "ioexcepiton2");
		}
	}
}

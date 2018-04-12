package com.wanli.page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract.FullNameStyle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class ClientThread implements Runnable {

	//���̸߳������Socket
	private Socket s;
	private EditText text;
	//���߳��������Socket����Ӧ��������
	BufferedReader br = null;
	
	public ClientThread(Socket s, EditText text) throws IOException {
		this.s = s;
		this.text = text;
	}
	
	@Override
	public void run() {
		
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String content = null;
			//���϶�ȡ���������͹�������Ϣ
			while ((content = br.readLine()) != null) {
				new SetText().execute("");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("ioexception3", "ioexception3");
		}
		
	}
	//�����߳����޸������״̬��Ҫʹ��AsyncTask��
	private class SetText extends AsyncTask<String, Object, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			text.setText("��������");
		}
		
	}

}

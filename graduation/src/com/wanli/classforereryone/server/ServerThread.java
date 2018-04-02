package com.wanli.classforereryone.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import com.wanli.swing.entities.OnlineUser;
import com.wanli.swing.frame.MainFrame;

public class ServerThread implements Runnable {

	//���嵱ǰ�߳��������Socket
	Socket s = null;
	//���߳��������Socket����Ӧ��������
	BufferedReader br = null;
	//���������Ϸ���˵Ŀͻ���imei��
	private static String ipAddress = "";
	public static String quitSocket = "";
	
	public ServerThread(Socket s) throws IOException {
		this.s = s;
		//��ʼ����Socket��Ӧ��������
		br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
	}
	
	@Override
	public void run() {
		
		String content = null;
		//����ѭ�����ϴ�Socket�ж�ȡ�ͻ��˷��͹���������
		while ((content = readFromClient()) != null) {
			String[] info = content.split(",");
			MyServer.users.get(s.getInetAddress().toString().substring(1)).setInetAddress(s.getInetAddress().toString().substring(1));
			MyServer.users.get(s.getInetAddress().toString().substring(1)).setUsername(info[0]);
			MyServer.users.get(s.getInetAddress().toString().substring(1)).setImei(info[1]);
			ipAddress = s.getInetAddress().toString().substring(1);
			System.out.println(ipAddress);
			if (info.length == 3) {
				MyServer.users.get(s.getInetAddress().toString().substring(1)).setContent(info[2]);
				System.out.println(info[0] + "," + info[1] + "," + info[2]);				
			}
		}
		//����ѭ����������Socket��Ӧ�Ŀͻ����Ѿ��ر�
		//ɾ����Socket
		quitSocket = s.getInetAddress().toString().substring(1);
		MyServer.users.remove(s.getInetAddress().toString().substring(1));
	}

	//�����ȡ�ͻ������ݵķ���
	private String readFromClient() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ͻ��˷�����Ϣ
	public static void sendToClient(String msg) {
		for (Map.Entry<String, OnlineUser> user: MyServer.users.entrySet()) {
			try {
				System.out.println(msg);
				OnlineUser val = user.getValue(); 
                PrintWriter pw =val.getPw();  
                pw.println(msg);  
                pw.flush();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
		}
	}
	
	public static String getIpAddress() {
		return ipAddress;
	}
	
}

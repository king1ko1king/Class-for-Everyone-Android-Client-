package com.wanli.page;

import java.io.OutputStream;

import com.wanli.classforevery.R;
import com.wanli.socket.ConnectionSocket;
import com.wanli.socket.GetWiFiGateway;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//��¼����
public class Login extends Activity {
	
	//ѡ�м�ס�����洢�˺ź������String
	private String userNameValue;
	private String passwordValue;
	
	//��¼�����Ͽؼ�
	private EditText lAccount;//1.�˺�
	private EditText lPassword;//2.����
	private CheckBox lRememberPwd;//3.��ס����
	private TextView lChangePwd;//4.�޸�����
	private Button lBtnLogin;//5.��¼
	private Button lBtnCancel;//6.ȡ��
	private Button lBtnRegister;//7.ע��
	
	//��������������������˴�������
	public static OutputStream os;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		//ͨ��id�ҵ���Ӧ�Ŀؼ�
		lAccount = (EditText) findViewById(R.id.userEditText);//1
		lPassword = (EditText) findViewById(R.id.pwdEditText);//2
		lRememberPwd = (CheckBox) findViewById(R.id.Login_Remember);//3
		lChangePwd = (TextView) findViewById(R.id.login_text_change_pwd);//4
		lBtnLogin = (Button) findViewById(R.id.bnLogin);//5
		lBtnCancel = (Button) findViewById(R.id.bnCancel);//6
		lBtnRegister = (Button) findViewById(R.id.bnRegister);//7
		
		//��ȡWiFi����IP��ַ
		GetWiFiGateway getWiFiGateway = new GetWiFiGateway(this);
		String gateway = getWiFiGateway.getGateway();
		
//�����ȡ��Gateway��IP��ַ
Toast.makeText(this, gateway, Toast.LENGTH_SHORT).show();

		//�������ӷ���˵��߳�
		new Thread(new ConnectionSocket(gateway)).start();
		
		lBtnLogin.setOnClickListener(lListener);
		lBtnCancel.setOnClickListener(lListener);
		lBtnRegister.setOnClickListener(lListener);
	}
	
	//��ͬ��ť���µļ����¼�ѡ��
	OnClickListener lListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bnLogin:
				login();
				break;
				
			case R.id.bnCancel:
				break;
				
			case R.id.bnRegister:
				break;
				
			case R.id.login_text_change_pwd:
				break;
				
			}
		}
	};
	
	//��¼
	public void login() {
		//У���˺ź�����
		if (checkUsernameAndPwd()) {
			String userName = getAccount();
			String userPassword = getPassword();
			
			try {
				//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);

		        String sendData = "2," + userName + "," + userPassword + "\r\n";
		      //���˺����봫�������
				os.write((sendData).getBytes());
				os.flush(); 
//������͵�����˵��˺�����
Toast.makeText(this, sendData, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("ioexception2", "ioexception1");
			}
		}
	}
	
	//У���˺ź�����
	public boolean checkUsernameAndPwd() {
		String userName = lAccount.getText().toString().trim();
		String userPassword = lPassword.getText().toString().trim();
		
		if (userName.equals("")) { //�˺�Ϊ��
			Toast.makeText(this, getString(R.string.account_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		}  else if (userPassword.equals("")) { //����Ϊ��
			Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		} else if (userPassword.length() < 6 || userPassword.length() > 16) { //���볤�ȴ���
			lPassword.setText("");
			Toast.makeText(this, getString(R.string.password_length_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		}
				
		return true;
	}
	
	//��ȡ��ǰ��¼������˺ź�����
	public String getAccount() {
		String account = lAccount.getText().toString().trim();
		
		return account;
	}
	public String getPassword() {
		String password = lPassword.getText().toString().trim();
		
		return password;
	}
}

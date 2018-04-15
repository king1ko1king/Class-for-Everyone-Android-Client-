package com.wanli.page;

import java.io.OutputStream;

import com.wanli.classforevery.R;
import com.wanli.socket.ConnectionSocket;
import com.wanli.socket.GetWiFiGateway;
import com.wanli.verification.VerifyEmail;
import com.wanli.verification.VerifyPhone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
	private Button lBtnRegister;//6.ע��
	private ToggleButton lTogglePwd;//7.��ʾ����
	
	
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
		lBtnRegister = (Button) findViewById(R.id.bnRegister);//6
		lTogglePwd = (ToggleButton) findViewById(R.id.loginTogglePwd);//7
		
		//��ȡWiFi����IP��ַ
		GetWiFiGateway getWiFiGateway = new GetWiFiGateway(this);
		String gateway = getWiFiGateway.getGateway();
		
//�����ȡ��Gateway��IP��ַ
Toast.makeText(this, gateway, Toast.LENGTH_SHORT).show();

		//�������ӷ���˵��߳�
		new Thread(new ConnectionSocket(gateway)).start();
		
		//��һ�����и����������CheckBox
		firstRunAPP();
		
		//Ϊ��ťע�������
		lBtnLogin.setOnClickListener(lListener);
		lBtnRegister.setOnClickListener(lListener);
		lTogglePwd.setOnClickListener(lListener);
		lRememberPwd.setOnClickListener(lListener);
	}
	
	//��ͬ��ť���µļ����¼�ѡ��
	private OnClickListener lListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bnLogin:
				login();
				break;
				
			case R.id.bnRegister:
				register();
				break;
				
			case R.id.Login_Remember:
				remember_password();
				break;
				
			case R.id.login_text_change_pwd:
				break;
			
			case R.id.loginTogglePwd:
				if(lTogglePwd.isChecked()){
					/* ��ʾ���� */
					lPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else{
		    	   /* �������� */
					lPassword.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
		        }
				break;
				
			}
		}
	};
	
	//��¼
	private void login() {
		//У���˺ź�����
		if (checkUsernameAndPwd()) {
			String userName = getAccount();
			String userPassword = getPassword();
			
			try {
				//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		        StrictMode.setThreadPolicy(policy);

		        String sendLoginData = "2," + userName + "," + userPassword + "\r\n";
		      //���˺����봫�������
				ConnectionSocket.os.write((sendLoginData).getBytes());
				ConnectionSocket.os.flush(); 
//������͵�����˵��˺�����
Toast.makeText(this, sendLoginData, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("ioexception2", "ioexception1");
			}
		}
	}
	
	//У���˺ź�����
	private boolean checkUsernameAndPwd() {
		
		String userName = getAccount();
		String userPassword = getPassword();
		
		if (userName.equals("")) { //�˺�Ϊ��
			Toast.makeText(this, getString(R.string.account_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		}  else if ( !verifyAccount(userName) ) { //�˺Ÿ�ʽ����
			lAccount.setText("");
			Toast.makeText(this, getString(R.string.account_wrong), Toast.LENGTH_SHORT).show();
					
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
	private String getAccount() {
		return lAccount.getText().toString().trim();
	}
	private String getPassword() {
		return lPassword.getText().toString().trim();
	}
	
	//���ע�ᰴť�������¼�
	private void register() {
		Intent intent = new Intent(Login.this, Register.class);
		startActivity(intent);
		finish();
	}
	
	//��ס��¼�˺ź�����
	private void remember_password() {
		SharedPreferences userInformation = getSharedPreferences("remember_password", 0);
		
		if (lRememberPwd.isChecked()) {
			userInformation.edit().putString("judgeCheckBox", "yes")
						   .putString("userName", getAccount())
						   .putString("password", getPassword())
						   .commit();
			Toast.makeText(Login.this, "��ס�˺ź�����", Toast.LENGTH_SHORT).show();
		} else {
			userInformation.edit().putString("judgeCheckBox", "no")
						   .putString("userName", "")
						   .putString("password", "")
						   .commit();
			Toast.makeText(Login.this, "����ס�˺ź�����", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * ��һ�����и����
	 * ����CheckBox
	 */
	private void firstRunAPP() {  
        //�������ļ���ȡ�û�������ļ�ֵ��  
        //����һ���У���ȡ���ļ�ֵ��Ϊ�����õ�Ĭ��ֵ  
        SharedPreferences userInformation = getSharedPreferences("remember_password", 0);  
        String strJudge = userInformation.getString("judgeCheckBox", "no");// ѡ��״̬  
        String strUserName = userInformation.getString("userName", "");// �û���  
        String strPassword = userInformation.getString("password", "");// ����  
        if (strJudge.equals("yes")) {  
        	lRememberPwd.setChecked(true);  
        	lAccount.setText(strUserName);  
            lPassword.setText(strPassword);  
        } else {  
        	lRememberPwd.setChecked(false);  
            lAccount.setText("");  
            lPassword.setText("");  
        }  
    }  
	
	/**
	 * ��֤�˺����ֻ��Ż�������
	 */
	private boolean verifyAccount(String userName) {
		
		//�����ж��˺����ֻ��Ż�������
		if (userName.contains("@")) {
			//�������䣬���������ж�
			if (!VerifyEmail.isEmail(userName)) {
				//�����ʽ����ȷ
				return false;
			}
		} else {
			//�����ֻ��ţ������ֻ����ж�
			if (!VerifyPhone.isMobileNO(userName)) {
				//�ֻ��Ÿ�ʽ����ȷ
				return false;
			}
		}
		
		return true;
	}

}

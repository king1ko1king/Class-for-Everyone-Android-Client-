package com.wanli.page;

import com.wanli.classforevery.R;
import com.wanli.socket.ConnectionSocket;
import com.wanli.socket.GetWiFiGateway;
import com.wanli.verification.VerifyEmail;
import com.wanli.verification.VerifyPhone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Register extends Activity {
	
	//ע�����Ŀؼ�
	private EditText rAccount;//1.�˺�
	private EditText rPasswordOnce;//2.����
	private ToggleButton rTogglePassword;//3.������ʾ
	private EditText rPasswordSecond;//4.ȷ������
	private ToggleButton rConfirmTogglePassword;//5.ȷ��������ʾ
	private EditText rEmail;//6.����
	private EditText rPhone;//7.�ֻ���
	private Button rBtnRegister;//8.ע��
	private Button rBtnBack;//9.����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		//��ȡWiFi����IP��ַ
		GetWiFiGateway getWiFiGateway = new GetWiFiGateway(this);
		String gateway = getWiFiGateway.getGateway();
				
//�����ȡ��Gateway��IP��ַ
Toast.makeText(this, gateway, Toast.LENGTH_SHORT).show();

		//�������ӷ���˵��߳�
		new Thread(new ConnectionSocket(gateway)).start();
		
		//ͨ��id�ҵ���Ӧ�Ŀؼ�
		rAccount = (EditText) findViewById(R.id.register_username); 
		rPasswordOnce = (EditText) findViewById(R.id.register_password_once);
		rTogglePassword = (ToggleButton) findViewById(R.id.registerTogglePwd); 
		rPasswordSecond = (EditText) findViewById(R.id.register_password_second);
		rConfirmTogglePassword = (ToggleButton) findViewById(R.id.registerConfirmTogglePwd);
		rEmail = (EditText) findViewById(R.id.register_email);
		rPhone = (EditText) findViewById(R.id.register_phone);
		rBtnRegister = (Button) findViewById(R.id.buttonRegister);
		rBtnBack = (Button) findViewById(R.id.buttonBack);
		
		//Ϊ��ťע�������
		rBtnRegister.setOnClickListener(rListener);
		rBtnBack.setOnClickListener(rListener);
		rTogglePassword.setOnClickListener(rListener);
		rConfirmTogglePassword.setOnClickListener(rListener);
	}
	
	//��ͬ��ť���µĵ�������¼�ѡ��
	private OnClickListener rListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.buttonRegister:
					register();
					break;

				case R.id.buttonBack:
					back();
					break;
					
				case R.id.registerTogglePwd:
					if(rTogglePassword.isChecked()){
						/* ��ʾ���� */
						rPasswordOnce.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					} else{
			    	   /* �������� */
			    	   rPasswordOnce.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
			        }
					break;
					
				case R.id.registerConfirmTogglePwd:
					if(rConfirmTogglePassword.isChecked()){
						/* ��ʾ���� */
						rPasswordSecond.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					} else{
			    	   /* �������� */
						rPasswordSecond.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
			        }
					break;
					
			}
		}
	};
		
	//ע�ᰴť����
	private void register() {
		//У���˺ź�����
		if (checkTheInput()) {
			String userName = getAccount();
			String userPassword = getPassword();
			String userEmail = getEmail();
			String userPhone = getPhone();
					
			try {
				//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
				StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);

				String sendRegisterData = "1," + userName + "," + userPassword + "," + userEmail + "," + userPhone + "," + "\r\n";
				//���˺����봫�������
				ConnectionSocket.os.write((sendRegisterData).getBytes());
				ConnectionSocket.os.flush(); 
//������͵�����˵��˺�����
Toast.makeText(this, sendRegisterData, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
				Log.i("ioexception2", "ioexception1");
			}
		}
	}
	
	//У��ע�������������Ƿ���ȷ
	private boolean checkTheInput() {
		
		String userName = getAccount();
		String userPassword = getPassword();
		String userConfirmPassword = getConfirmPassword();
		String userEmail = getEmail();
		String userPhone = getPhone();
		
		if (userName.equals("")) { //�˺�Ϊ��
			Toast.makeText(this, getString(R.string.account_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		}  else if (userPassword.equals("")) { //����Ϊ��
			Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		} else if (userPassword.length() < 6 || userPassword.length() > 16) { //���볤�ȴ���
			rPasswordOnce.setText("");
			rPasswordSecond.setText("");
			Toast.makeText(this, getString(R.string.password_length_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (!userConfirmPassword.equals(userPassword)) {
			rPasswordOnce.setText("");
			rPasswordSecond.setText("");
			Toast.makeText(this, getString(R.string.password_equal_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (userEmail.equals("")) {
			Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (!VerifyEmail.isEmail(userEmail)) {
			rEmail.setText("");
			Toast.makeText(this, getString(R.string.email_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (userPhone.equals("")) {
			Toast.makeText(this, getString(R.string.phone_empty), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (!VerifyPhone.isMobileNO(userPhone)) {
			rPhone.setText("");
			Toast.makeText(this, getString(R.string.phone_length_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		}
		
		return true;
	}
	
	//��ȡ��ǰע�������˺�
	private String getAccount() {
		return rAccount.getText().toString().trim();
	}
	//��ȡ��ǰע����������
	private String getPassword() {
		return rPasswordOnce.getText().toString().trim();
	}
	//��ȡ��ǰע������ȷ������
	private String getConfirmPassword() {
		return rPasswordSecond.getText().toString().trim();
	}
	//��ȡ��ǰע����������
	private String getEmail() {
		return rEmail.getText().toString().trim();
	}
	//��ȡ��ǰע�������ֻ���
	private String getPhone() {
		return rPhone.getText().toString().trim();
	}
	
	//���ذ�ť����
	private void back() {
		Intent intent = new Intent(Register.this, Login.class);
		startActivity(intent);
		finish();
	}
	
}

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ModifyPassword extends Activity {
	
	//ע�����Ŀؼ�
		private TextView mAccount;//1.�˺�
		private EditText mPasswordOnce;//2.����
		private ToggleButton mTogglePassword;//3.������ʾ
		private EditText mPasswordSecond;//4.ȷ������
		private ToggleButton mConfirmTogglePassword;//5.ȷ��������ʾ
		private Button mBtnModify;//6.�ύ
		private Button mBtnBack;//7.����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_password);
		
		//��ȡ����������洫�����˺���Ϣ
		Intent intent = getIntent();
		String str = intent.getStringExtra("Account");
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
		
		
		//��ȡWiFi����IP��ַ
		GetWiFiGateway getWiFiGateway = new GetWiFiGateway(this);
		String gateway = getWiFiGateway.getGateway();
						
//�����ȡ��Gateway��IP��ַ
Toast.makeText(this, gateway, Toast.LENGTH_SHORT).show();

		//�������ӷ���˵��߳�
		new Thread(new ConnectionSocket(gateway)).start();
				
		//ͨ��id�ҵ���Ӧ�Ŀؼ�
		mAccount = (TextView) findViewById(R.id.userAccount); //1
		mPasswordOnce = (EditText) findViewById(R.id.modify_password_once); //2
		mTogglePassword = (ToggleButton) findViewById(R.id.modifyTogglePwd); //3 
		mPasswordSecond = (EditText) findViewById(R.id.modify_password_second); //4
		mConfirmTogglePassword = (ToggleButton) findViewById(R.id.modifyConfirmTogglePwd); //5
		mBtnModify = (Button) findViewById(R.id.modifyConfirmButton); //6
		mBtnBack = (Button) findViewById(R.id.modifyBackButton);//7.����
		
		//���޸�������˺���ʾ����
		mAccount.setText(str);
		
		//Ϊ��ťע�������
		mTogglePassword.setOnClickListener(mListener);
		mConfirmTogglePassword.setOnClickListener(mListener);
		mBtnModify.setOnClickListener(mListener);
		mBtnBack.setOnClickListener(mListener);
	}
	
	
	//��ͬ��ť���µĵ�������¼�ѡ��
	private OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.modifyTogglePwd:
					if(mTogglePassword.isChecked()){
						/* ��ʾ���� */
						mPasswordOnce.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					} else{
			    	   /* �������� */
			    	   mPasswordOnce.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
			        }
					break;

				case R.id.modifyConfirmTogglePwd:
					if(mConfirmTogglePassword.isChecked()){
						/* ��ʾ���� */
						mPasswordSecond.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
					} else{
			    	   /* �������� */
						mPasswordSecond.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
			        }
					break;
						
				case R.id.modifyConfirmButton:
					submit();
					break;
					
				case R.id.modifyBackButton:
					back();
					break;
			
			}
		}
	};
	
	//�ύ��ť�¼�
	private void submit() {
		//У���˺ź�����
		if (checkTheInput()) {
			String userName = getAccount();
			String userPassword = getPassword();
					
			try {
				//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);

				String sendRegisterData = "5," + userName + "," + userPassword + "\r\n";
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

		
		if (userPassword.equals("")) { //����Ϊ��
			Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show();
					
			return false;
		} else if (userPassword.length() < 6 || userPassword.length() > 16) { //���볤�ȴ���
			mPasswordOnce.setText("");
			mPasswordSecond.setText("");
			Toast.makeText(this, getString(R.string.password_length_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		} else if (!userConfirmPassword.equals(userPassword)) {
			mPasswordOnce.setText("");
			mPasswordSecond.setText("");
			Toast.makeText(this, getString(R.string.password_equal_wrong), Toast.LENGTH_SHORT).show();
			
			return false;
		}
		
		return true;
	}
	
	
	//��ȡ��ǰע�������˺�
	private String getAccount() {
		return mAccount.getText().toString().trim();
	}
	//��ȡ��ǰע����������
	private String getPassword() {
		return mPasswordOnce.getText().toString().trim();
	}
	//��ȡ��ǰע������ȷ������
	private String getConfirmPassword() {
		return mPasswordSecond.getText().toString().trim();
	}
	
	//���ذ�ť����
	private void back() {
		Intent intent = new Intent(ModifyPassword.this, Login.class);
		startActivity(intent);
		finish();
	}
}

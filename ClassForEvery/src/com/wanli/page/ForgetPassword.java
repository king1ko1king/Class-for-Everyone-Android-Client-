package com.wanli.page;

import com.wanli.classforevery.R;
import com.wanli.socket.ConnectionSocket;
import com.wanli.socket.GetWiFiGateway;
import com.wanli.verification.VerifyEmail;
import com.wanli.verification.VerifyPhone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ForgetPassword extends Activity {
	
	//������������Ͽؼ�
	private TextView fPrevious;//1.��һ��
	private TextView fNext;//2.��һ��
	private EditText fAccount;//3.�˺�
	private EditText fInputCode;//4.������֤��
	private Button fSendCode;//5.��ȡ��֤��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_password);
		
		//��ȡWiFi����IP��ַ
		GetWiFiGateway getWiFiGateway = new GetWiFiGateway(this);
		String gateway = getWiFiGateway.getGateway();
						
//�����ȡ��Gateway��IP��ַ
Toast.makeText(this, gateway, Toast.LENGTH_SHORT).show();

		//�������ӷ���˵��߳�
		new Thread(new ConnectionSocket(gateway)).start();
		
		//��ȡ�ؼ���id
		fPrevious = (TextView) findViewById(R.id.forget_password_previous);//1
		fNext = (TextView) findViewById(R.id.forget_password_next);//2
		fAccount = (EditText) findViewById(R.id.forget_password_account);//3
		fInputCode = (EditText) findViewById(R.id.input_verify_code);//4
		fSendCode = (Button) findViewById(R.id.send_verify_code);//5
		
		//ע������¼�
		fPrevious.setOnClickListener(fListener);
		fNext.setOnClickListener(fListener);
		fSendCode.setOnClickListener(fListener);
	}
	
	//��ͬ��ť���µļ����¼�ѡ��
	private OnClickListener fListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.forget_password_previous:
				previous();
				break;
					
			case R.id.forget_password_next:
				next();
				break;
					
			case R.id.send_verify_code:
				
				if ( accountIsExist() ) {
					sendCode();
				} else {
					outputInformation();
				}
				break;

			}
		}
	};
	
	//ȡ���¼�
	private void previous() {
		Intent intent = new Intent(ForgetPassword.this, Login.class);
		startActivity(intent);
		finish();
	}
	
	//��һ���¼�
	private void next() {
		
		if ( codeIsTrue( getCode() ) ) {
			Intent intent = new Intent(ForgetPassword.this, ModifyPassword.class);
			intent.putExtra("Account",getAccount());
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(this, getString(R.string.code_wrong), Toast.LENGTH_SHORT).show();
		}
	}
	
	//�ж�������˺ţ�������Ƿ����
	private boolean accountIsExist() {
		String userName = getAccount();
		
		try {
			//ʹ�á��Ͽ�ģʽ�����̲߳��ԣ�����߳��еĲ���
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);

			String sendForgetPasswordData = "5," + userName + "," + "null" + "\r\n";
			//���˺����봫�������
			ConnectionSocket.os.write((sendForgetPasswordData).getBytes());
			ConnectionSocket.os.flush(); 
//������͵�����˵��˺�����
Toast.makeText(this, sendForgetPasswordData, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("ioexception2", "ioexception1");
		}
		
		return true;
	}
	
	//��ȡ��֤���¼�
	private void sendCode() {
		String userName = getAccount();
		
		if ( VerifyEmail.isEmail(userName) ) {
			
			Toast.makeText(this, getString(R.string.email_reveice), Toast.LENGTH_SHORT).show();
			timer.start();
			
			//�����ȡ��֤�뺯��requestEmailCode();
		} else if ( VerifyPhone.isMobileNO(userName) ) {
			
			Toast.makeText(this, getString(R.string.message_reveice), Toast.LENGTH_SHORT).show();
			timer.start();
			
			//���Ż�ȡ��֤�뺯��requestPhoneCode();
		} else {
			Toast.makeText(this, getString(R.string.account_wrong), Toast.LENGTH_SHORT).show();
		}
	}
	
	//
	private void outputInformation() {
		Toast.makeText(this, getString(R.string.account_not_exist), Toast.LENGTH_SHORT).show();
	}
	
	//�ж���֤���Ƿ���ȷ
	private boolean codeIsTrue(String code) {
		
		return true;
	}
	
	/**
	 * ��ȡ��֤��ļ�ʱ������������ʱ
	 * ����ʱΪ60�룬ÿ1��ˢ��һ��
	 */
	private CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {  
		  
	    @Override  
	    public void onTick(long millisUntilFinished) {  
	    	fSendCode.setEnabled(false);  
	    	fSendCode.setText((millisUntilFinished / 1000) + "�����ط�");  
	    }  
	  
	    @Override  
	    public void onFinish() {  
	    	fSendCode.setEnabled(true);  
	    	fSendCode.setText(R.string.send_code);  
	    }  
	};  
	
	//��ȡ�˺�
	private String getAccount() {
		return fAccount.getText().toString().trim();
	}
	
	//��ȡ��֤��
	private String getCode() {
		return fInputCode.getText().toString().trim();
	}
	
}

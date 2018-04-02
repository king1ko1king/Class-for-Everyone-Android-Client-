package com.wanli.swing.frame.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class ComboListener implements SelectionListener {

	private Properties userProp;//��ȡ����ĵ�¼��Ϣ�����������б�����ʾ
	private Properties saveProp;//��ȡ����ĵ�¼��Ϣ��������ʾ��һ���˳�ʱ���ʺ�
	private FileInputStream inStream;//����������ȡ��Ϣ
	private File userFile;//��ȡ�����û���Ϣ���ļ�
	private File saveFile;//��ȡ������һ���˳�ʱ�ʺŵ���Ϣ
	private Combo comboUser;//������
	private Button bRememberMe;//��ס���븴ѡ��

	public ComboListener(Combo comboUser, Button bRememberMe) {
		this.comboUser = comboUser;
		this.bRememberMe = bRememberMe;
		userProp = new Properties();
		saveProp = new Properties();
		userFile = new File("F:\\��ҵ���\\graduation\\users.properties");
		saveFile = new File("F:\\��ҵ���\\graduation\\savecount.properties");
		try {
			inStream = new FileInputStream(userFile);
			userProp.load(inStream);
			inStream = new FileInputStream(saveFile);
			saveProp.load(inStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		String key = comboUser.getText();
		if (userProp.getProperty(key) != null) {
			bRememberMe.setSelection(true);
		} else {
			bRememberMe.setSelection(false);
		}
	}

}
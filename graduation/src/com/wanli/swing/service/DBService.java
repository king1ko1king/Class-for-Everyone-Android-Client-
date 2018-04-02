package com.wanli.swing.service;

import java.util.List;

import com.wanli.swing.dao.DBDao;

public class DBService {

	private DBDao dbDao = null;
	
	public DBService() {
		dbDao = new DBDao();
	}
	
	/**
	 * ������
	 * @param num��ָ������
	 * @param tableName��ָ������
	 */
	public void createTable(int num, String tableName) {
		dbDao.createTable(num, tableName);
	}
	
	/**
	 * ��ȡ�ɼ�����
	 * @param tableName������
	 * @return ���ػ�ȡ�ĳɼ�����
	 */
	public List<String[]> getScoreData(String tableName) {
		return dbDao.getScoreData(tableName);
	}
	
	/**
	 * ��ȡ���������
	 * @param tableName������
	 * @return ����
	 */
	public int getTableColumn(String tableName) {
		return dbDao.getTableColumn(tableName);
	}
}

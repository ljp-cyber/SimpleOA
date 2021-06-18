package com.ljp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

	/**
	 * ���л�����
	 * 
	 * @param fileUrl
	 *            ����λ��
	 * @param obj
	 *            Ҫ����Ķ���
	 * @return �ɹ�����true��ʧ�ܷ���false
	 * @throws Exception
	 *             IO�쳣��
	 */
	public static <T> boolean saveObj(String fileUrl, T obj) {
		if (obj == null) {
			System.out.println("����Ϊ��,�����б���");
			return false;
		}
		File file = new File(fileUrl);
		System.out.println("����λ��Ϊ��" + file.getAbsolutePath());
		
		ObjectOutputStream objectOutputStream = null;
		try {
			if (!file.exists()) {
				System.out.println("�ļ������ڣ������ļ�");
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(objectOutputStream!=null) {
					objectOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("�ļ�����ɹ�");
		return true;
	}

	/**
	 * �����л�����
	 * 
	 * @param fileUrl
	 *            �ļ�λ��
	 * @return ʧ�ܷ��ؿգ��ɹ����ط��Ͷ���
	 * @throws Exception
	 *             IO�쳣��
	 */
	@SuppressWarnings("unchecked")
	public static <T> T loadObj(String fileUrl) {
		File file = new File(fileUrl);
		System.out.println("��ȡλ��Ϊ��" + file.getAbsolutePath());
		
		T obj = null;
		
		ObjectInputStream objectOutputStream = null;
		try {
			if (!file.exists()) {
				System.out.println("�ļ�������");
				return null;
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			objectOutputStream = new ObjectInputStream(fileInputStream);
			obj = (T) objectOutputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(objectOutputStream!=null) {
					objectOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("�ļ���ȡ�ɹ�");
		return obj;
	}

}

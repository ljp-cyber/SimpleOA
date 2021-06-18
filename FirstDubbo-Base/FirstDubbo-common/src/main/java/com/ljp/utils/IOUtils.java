package com.ljp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

	/**
	 * 序列化对象
	 * 
	 * @param fileUrl
	 *            保存位置
	 * @param obj
	 *            要保存的对象
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 *             IO异常等
	 */
	public static <T> boolean saveObj(String fileUrl, T obj) {
		if (obj == null) {
			System.out.println("对象为空,不进行保存");
			return false;
		}
		File file = new File(fileUrl);
		System.out.println("保存位置为：" + file.getAbsolutePath());
		
		ObjectOutputStream objectOutputStream = null;
		try {
			if (!file.exists()) {
				System.out.println("文件不存在，创建文件");
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
		System.out.println("文件保存成功");
		return true;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param fileUrl
	 *            文件位置
	 * @return 失败返回空，成功返回泛型对象
	 * @throws Exception
	 *             IO异常等
	 */
	@SuppressWarnings("unchecked")
	public static <T> T loadObj(String fileUrl) {
		File file = new File(fileUrl);
		System.out.println("读取位置为：" + file.getAbsolutePath());
		
		T obj = null;
		
		ObjectInputStream objectOutputStream = null;
		try {
			if (!file.exists()) {
				System.out.println("文件不存在");
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
		System.out.println("文件读取成功");
		return obj;
	}

}

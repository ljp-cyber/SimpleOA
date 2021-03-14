package com.ljp.simpleoa.service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BackupService {
	@Autowired
	private DataSource dataSource;
	public void resetDataBase(String filePath) {
		Connection connection = null;
		ScriptRunner runner = null;
		try {
			// 获取指定数据源连接
			connection = dataSource.getConnection();
			runner = new ScriptRunner(connection);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			runner.runScript(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
}

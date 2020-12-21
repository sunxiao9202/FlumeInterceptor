package com.zhihuishu.flume.utils.MysqlSink;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Collections;
import java.util.StringTokenizer;

public class MysqlDruid {

	private String username;
	private String password;
	private String url;
	private volatile  static DruidDataSource dataSource;

	MysqlDruid(String ip, String uname, String pwd){
		url = ip; username=uname;password=pwd;
	}
	public  DruidDataSource getDataSource(){
		if(dataSource==null || dataSource.isClosed()) {
			dataSource = initDataSource( );
		}
		return dataSource;
	};
	private DruidDataSource initDataSource(){
		DruidDataSource dataSource=new DruidDataSource();
		try {
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl(this.url);
			dataSource.setUsername(this.username);
			dataSource.setPassword(this.password);
			dataSource.setFilters("stat,slf4j");
			dataSource.setInitialSize(3);
			dataSource.setMinIdle(3);
			dataSource.setMaxActive(100);
			dataSource.setMaxWait(60000);
			dataSource.setTimeBetweenEvictionRunsMillis(60000);
			dataSource.setMinEvictableIdleTimeMillis(300000);
			dataSource.setDefaultAutoCommit(true);
			dataSource.setTestOnBorrow(false);
			dataSource.setTestOnReturn(false);
			//dataSource.setUseUnfairLock(true);
			dataSource.setTestWhileIdle(true);
			dataSource.setValidationQuery("SELECT 1");
			//dataSource.setRemoveAbandoned(false);
			//dataSource.setRemoveAbandonedTimeout(8 * 3600);
			dataSource.setQueryTimeout(3000);
			StringTokenizer tokenizer = new StringTokenizer("set names utf8mb4;set @@session.innodb_lock_wait_timeout=50;", ";");
			dataSource.setConnectionInitSqls(Collections.list(tokenizer));
			dataSource.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}



}

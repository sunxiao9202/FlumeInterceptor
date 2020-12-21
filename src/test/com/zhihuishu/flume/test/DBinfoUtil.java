package com.zhihuishu.flume.test;

import java.util.List;

import static com.zhihuishu.flume.uitls.DatabaseUtil.*;

/**
 * @author: lihua
 * @date: 2020/12/16 16:02
 * @Description:
 */
public class DBinfoUtil {
		public static void main(String[] args) {
			List<String> tableNames = getTableNames();
			//System.out.println("tableNames:" + tableNames);
			for (String tableName : tableNames) {
				if(tableName.equals("tbl_user")) {
					System.out.println("ColumnNames:" + getColumnNames(tableName));
					System.out.println("ColumnTypes:" + getColumnTypes(tableName));
					System.out.println("ColumnComments:" + getColumnComments(tableName));
				}
			}
		}
}

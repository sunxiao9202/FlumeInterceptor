package com.zhihuishu.flume.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lihua
 * @date: 2020/12/16 16:52
 * @Description:
 */
public class MysqlInsertUtil {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        String url="jdbc:mysql://121.43.42.157:3306/test?useUnicode=true&characterEncoding=UTF-8";
        String username="chensz";
        String password="chensz";
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stat=conn.createStatement();

        List<String> lists = new ArrayList<String>();
        lists.add("INSERT into tbl_user(id,name,sex,age,classNo,create_time) values('12','张三','男','26','12','2020-12-16 00:12:18')");
        lists.add("INSERT into tbl_user(id,name,sex,age,classNo,create_time) values('13','张三','男','26','12','2020-12-16 00:12:18')");
        lists.add("INSERT into tbl_user(id,name,sex,age,classNo,create_time) values('14','张三','男','26','12','2020-12-16 00:12:18')");
        lists.add("INSERT into tbl_user(id,name,sex,age,classNo,create_time) values('15','张三','男','26','12','2020-12-16 00:12:18')");
        lists.add("INSERT into tbl_user(id,name,sex,age,classNo,create_time) values('16','张三','男','26','12','2020-12-16 00:12:18')");
        if (lists != null && lists.size() > 0) {
            //preparedStatement.clearBatch();
            for (String sql : lists) {
                stat.addBatch(sql);
            }
           stat.executeBatch();
        }
    }
}

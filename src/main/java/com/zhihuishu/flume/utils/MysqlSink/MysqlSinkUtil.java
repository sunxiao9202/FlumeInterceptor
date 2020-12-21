package com.zhihuishu.flume.utils.MysqlSink;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author: lihua
 * @date: 2020/12/16 14:23
 * @Description:
 */
public class MysqlSinkUtil extends AbstractSink implements Configurable {
    private Logger LOG = LoggerFactory.getLogger(MysqlSinkUtil.class);
    private String url;
    private String username;
    private String password;
    //    private Statement stat;
//    private Connection conn;
    private int batchSize; //每次提交的批次大小
    private static DruidDataSource dataSource;

    public MysqlSinkUtil() {
        LOG.info("MysqlSink start...");
    }

    /**
     * 实现Configurable接口中的方法：可获取配置文件中的属性
     */
    @Override
    public void configure(Context context) {
        url = context.getString("url");
        Preconditions.checkNotNull(url, "url must be set!!");
        username = context.getString("username");
        Preconditions.checkNotNull(username, "user must be set!!");
        password = context.getString("password");
        Preconditions.checkNotNull(password, "password must be set!!");
        batchSize = context.getInteger("batchSize", 100);
        Preconditions.checkNotNull(batchSize > 0, "batchSize must be a positive number!!");
    }

    /**
     * 在整个sink开始时执行一遍，用来初始化数据库连接
     */
    @Override
    public void start() {
        LOG.info("start exec ...");
        dataSource = new MysqlDruid(url, username, password).getDataSource();
//        try {
//            conn= dataSource.getConnection();
//            stat=conn.createStatement();
//            conn.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }

    }

    @Override
    public void stop() {
        LOG.info("stop exec ...");
        super.stop();
//        if (stat != null) {
//            try {
//               stat.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event;
        String content;

        transaction.begin();
        List<String> lists = Lists.newArrayList();
        Connection conn = null;
        Statement stat = null;
        try {
            conn = dataSource.getConnection();
            stat = conn.createStatement();
            conn.setAutoCommit(false);

            for (int i = 0; i < batchSize; i++) {
                event = channel.take();
                if (event != null) {
                    content = new String(event.getBody(), Charsets.UTF_8);
                    lists.add(content);
                } else {
                    result = Status.BACKOFF;
                    break;
                }
            }
            if (lists.size() > 0) {
                stat.clearBatch();
                for (String sql : lists) {
                    stat.addBatch(sql);
                }
                stat.executeBatch();
                conn.commit();
            }
            transaction.commit();
        } catch (Throwable e) {
            try {
                transaction.rollback();
            } catch (Exception e2) {
                LOG.error("Exception in rollback. Rollback might not have been successful.", e2);
            }
            LOG.error("Failed to commit transaction . Transaction rolled back.", e);
            for (String sql : lists) {
                LOG.info("Exception Sql: {}", sql);
            }

            Throwables.propagate(e);
        } finally {
            transaction.close();
            try {
                if (stat != null) {
                    stat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

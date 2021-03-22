package pers.cc.spring.core.util.database;

import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.core.exception.DatabaseRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc操作数据库工具类
 *
 * @author chengce
 * @version 2018-04-30 17:37
 */
@Slf4j
public class JdbcUtils {
    private final String driver;

    private final String url;

    private final String username;

    private final String password;

    private final String ERROR_EXECUTE = "SQL执行出错";

    private String error;

    private List<String> sqlList;


    public static final String DRIVER_MYSQL = "com.mysql.jdbc.Driver";

    public static final String URL_DEFAULT = "jdbc:mysql://localhost";

    private JdbcUtils(Builder builder) {
        this.driver = builder.driver;
        this.url = builder.url;
        this.username = builder.username;
        this.password = builder.password;
        this.sqlList = new ArrayList<>();
    }

    public static JdbcUtils builder(String username,
                                    String password) {
        return new JdbcUtils(new Builder(DRIVER_MYSQL, URL_DEFAULT, username, password));
    }

    public static JdbcUtils builder(String driver,
                                    String url,
                                    String username,
                                    String password) {
        return new Builder(driver, url, username, password).build();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.error + " \n" +
                "driver: " + driver +
                "url: " + url +
                "username: " + username +
                "password: " + password;
    }

    private static class Builder {
        private String driver;

        private String url;

        private String username;

        private String password;

        public Builder(String driver,
                       String url,
                       String username,
                       String password) {
            this.driver = driver;
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public JdbcUtils build() {
            return new JdbcUtils(this);
        }
    }

    private void setError(String error) {
        this.error = error;
        log.error(this.toString());
    }

    /**
     * 创建数据库,默认UTF8字符集
     *
     * @param database 数据库名
     * @return 实例
     */
    public JdbcUtils createDatabase(String database) {
        String databaseSQL = "CREATE DATABASE " + database + " default charset utf8 COLLATE utf8_general_ci;";
        this.sqlList.add(databaseSQL);
        return this;
    }

    /**
     * 删除数据库，如果存在
     *
     * @param database 数据库名
     * @return 实例
     */
    public JdbcUtils removeDatabase(String database) {
        String databaseSQL = "DROP DATABASE IF EXISTS " + database + ";";
        this.sqlList.add(databaseSQL);
        return this;
    }

    /**
     * 执行SQL
     */
    public void execute() throws DatabaseRuntimeException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            Class.forName(driver);
            for (String sql : sqlList) {
                statement.executeUpdate(sql);
            }
            this.sqlList.clear();
            log.debug("SQL执行成功" + this.sqlList.toString());
        } catch (SQLException | ClassNotFoundException e) {
            this.setError(ERROR_EXECUTE + e.getMessage());
            throw new DatabaseRuntimeException(this.error);
        }
    }
}

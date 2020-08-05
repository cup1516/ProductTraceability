package com.pt.company_id.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

public class InsertData  {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    public static void CreateSql(String uid, String url, char client_type) throws IOException {

        String sqlName = "output_test";
        String filename = sqlName + ".sql";
        String directory = "D:\\BaiduNetdiskDownload\\ProductTraceability\\pt\\pt-company_id\\src\\main\\resources\\sql";
        File f = new File(directory, filename);
        //FileOutputStream fos1=new FileOutputStream(f);
        //OutputStreamWriter dos1=new OutputStreamWriter(fos1);

        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
        BufferedWriter writer = new BufferedWriter(write);


        //写sql语句，将新公司管理员用户写入各个表中
        //总语句
        String sql = "";
        //system_user表
        String user_sql = "INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)\n" +
                "VALUES ('1','" + uid + "','admin', '" + ENCODER.encode("123456") + "');\n";
        //system_role表
        String role_sql = "INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)\n" +
                "VALUES ('1','" + uid + "','管理员','ADMIN','0');\n";
        //system_user_role表
        String user_role_sql = "INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)\n" +
                "VALUES ('1', '1', '" + uid + "');\n";
        //system_post表
        String post_sql = "INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)\n" +
                "VALUES ('1', '" + uid + "', 'ceo', '董事长', '0', '0');\n";
        //system_user_post表
        String user_post_sql = "INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)\n" +
                "VALUES ('1', '1', '" + uid + "');\n";

        //system_menu表 insert into B(a,b,c,d)
        //select a,b,c,d from A where+查询条件，
        /*String menu_sql = "INSERT INTO SYSTEM_MENU (menu_id, company_id, menu_name)\n" +
                "VALUES('888', '"+ uid + "', '系统管理');\n";*/

        //-----------------------------------------------------------------------------------
        //system_role_menu表
        String role_menu_sql = "INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)\n" +
                "select '1' as 'role_id', '" + uid + "' as 'company_id', menu_id from system_menu where type = '0' and client_type != '8' or client_type = '" + client_type+ "';\n";
        //+ "VALUES('888', '"+ uid + "', '99');\n";
        //system_dept表
        String dept_sql = "INSERT INTO SYSTEM_DEPT (dept_id, company_id)\n" +
                "VALUES('1', '"+ uid + "');\n";
        sql = user_sql + role_sql + user_role_sql + post_sql + user_post_sql + role_menu_sql + dept_sql;
        writer.write(sql);
        writer.close();
        //Thread.sleep(1000*3);   // 休眠3秒,保证文件被保存
    }
    public static void ExecuteSql(){

        Connection conn;
        //jdbc:mysql://localhost:3306/pt?
        String data_url="JDBC:mysql://localhost:3306/pt-v2?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username="root";
        String password="root";
        try {
           /* Configuration configuration = new PropertiesConfiguration(ScriptRunnerExecSql.class.getClassLoader().getResource("")
                    + "META-INF/spring/db.properties");*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(data_url, username, password);
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            runner.runScript(Resources.getResourceAsReader("sql/output_test.sql"));//在resouse中新建一个文件夹：然后放入sql文件
            //runner.runScript(Resources.getResourceAsReader("sql/CC21-01.sql"));
            runner.closeConnection();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

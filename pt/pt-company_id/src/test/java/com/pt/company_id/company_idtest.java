package com.pt.company_id;

import com.pt.company_id.entity.CompanyId;
import com.pt.company_id.entity.SystemUser;
import com.pt.company_id.repository.CompanyIdRepository;
import com.pt.company_id.repository.SystemUserRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;


@SpringBootTest
public class company_idtest {
    @Autowired
    private CompanyIdRepository companyIdRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;


    @Test
    void contextLoads() {
        CompanyId c0 = new CompanyId();
        //CompanyId c0 = companyIdRepository.findByCompanyId("asd12345");
        //CompanyId c1 = companyIdRepository.findByCompanyName("水果公司1");
        //CompanyId c2 = companyIdRepository.findByUrl("/asd12345");
        //c0.setCompanyId("asd12345");
        c0.setCompanyName("果蔬企业121");
        c0.setType('0');
        c0.setUrl("/gs121");
        CompanyId c1 = companyIdRepository.save(c0);
        //System.out.println(c0);
        System.out.println(c1);
        //System.out.println(c2);
    }

    @Test
    void contextLoadsSystemUser() {
        CompanyId c1 = companyIdRepository.findByCompanyName("仓储0");
        SystemUser s1 = new SystemUser();
        s1.setUserId("asd12313czx");
        s1.setCompanyId(c1.getCompanyId());
        s1.setUserName("admin0");
        systemUserRepository.save(s1);
        System.out.println(s1);
    }

    @Test
    void fileTest() throws IOException {
        String sqlName = "output4";
        String filename = sqlName + ".sql";
        String directory = "D:\\program\\vue\\pt7-11\\ProductTraceability-master(1)\\ProductTraceability-master\\pt\\pt-company_id\\src\\main\\resources\\sql";
        File f = new File(directory, filename);
        //File f=new File("./output3.sql");
        FileOutputStream fos1=new FileOutputStream(f);
        OutputStreamWriter dos1=new OutputStreamWriter(fos1);
        dos1.write("INSERT INTO SYSTEM_USER (user_id, company_id, user_name, PASSWORD)\n" +
                "VALUES ('7','asd777','admin','123456');");
        dos1.close();
    }

    @Test
    void sqlTest() {
        Connection conn;
        //jdbc:mysql://localhost:3306/pt?
        String url="JDBC:mysql://localhost:3306/pt-v2?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username="root";
        String password="root";
        try {
           /* Configuration configuration = new PropertiesConfiguration(ScriptRunnerExecSql.class.getClassLoader().getResource("")
                    + "META-INF/spring/db.properties");*/
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            //在resouse中新建一个文件夹：然后放入sql文件
            runner.runScript(Resources.getResourceAsReader("sql/output_test.sql"));
            //runner.runScript(Resources.getResourceAsReader("sql/CC21-01.sql"));
            runner.closeConnection();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

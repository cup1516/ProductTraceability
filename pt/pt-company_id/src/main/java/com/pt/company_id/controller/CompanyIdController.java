package com.pt.company_id.controller;

import com.pt.company_id.entity.CompanyId;
import com.pt.company_id.entity.SystemUser;
import com.pt.company_id.repository.CompanyIdRepository;
import com.pt.company_id.repository.SystemUserRepository;
import com.pt.company_id.service.InsertData;
import com.pt.ptcommoncore.util.IdUtils;
import com.pt.ptcommoncore.util.UUID;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("/company_id")
public class CompanyIdController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private CompanyIdRepository companyIdRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @GetMapping("/findCompanyIdById/{id}")
    public CompanyId findCompanyById(@PathVariable("id") String id) {
        CompanyId c1 = companyIdRepository.findByCompanyId(id);
        return c1;
    }
    @GetMapping("/findCompanyIdByUrl/{url}")
    public String findCompanyByUrl(@PathVariable("url") String url) {
        return companyIdRepository.findByUrl(url).getCompanyId();
    }

    @PostMapping("/insert/{url}")
    public String insert(@PathVariable("url") String url) {
        InsertData.ExecuteSql();
        return "success!";
    }

    @PostMapping("/save")
    public String save(@RequestBody CompanyId companyId) throws IOException, InterruptedException {
        if (companyIdRepository.findByUrl(companyId.getUrl()) !=null)
        {
            return "该地址已被使用";
        }
        String uid = IdUtils.simpleUUID();//用uuid生成company_id
        String url0 =companyId.getUrl();
        companyId.setCompanyId(uid);
        CompanyId result = companyIdRepository.save(companyId);
        InsertData.CreateSql(uid,url0);

       /* String sqlName = "output8";
        String filename = sqlName + ".sql";
        String directory = "D:\\program\\vue\\pt7-11\\ProductTraceability-master(1)\\ProductTraceability-master\\pt\\pt-company_id\\src\\main\\resources\\sql";
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
                "VALUES ('88','" + uid + "','admin', '" + ENCODER.encode("123456") + "');\n";
        //system_role表
        String role_sql = "INSERT INTO SYSTEM_ROLE (role_id, company_id, role_name, role_code, role_sort)\n" +
                "VALUES ('88','" + uid + "','管理员','ADMIN','0');\n";
        //system_user_role表
        String user_role_sql = "INSERT INTO SYSTEM_USER_ROLE (user_id, role_id, company_id)\n" +
                "VALUES ('88', '88', '" + uid + "');\n";
        //system_post表
        String post_sql = "INSERT INTO SYSTEM_POST (post_id, company_id, post_code, post_name, post_sort, status)\n" +
                "VALUES ('88', '" + uid + "', 'ceo', '董事长', '1', '0');\n";
        //system_user_post表
        String user_post_sql = "INSERT INTO SYSTEM_USER_POST (user_id, post_id, company_id)\n" +
                "VALUES ('88', '88', '" + uid + "');\n";
        //system_menu表
        String menu_sql = "INSERT INTO SYSTEM_MENU (menu_id, company_id, menu_name)\n" +
                "VALUES('88', '"+ uid + "', '系统管理');\n";
        //system_role_menu表
        String role_menu_sql = "INSERT INTO SYSTEM_ROLE_MENU (role_id, company_id, menu_id)\n" +
                "VALUES('88', '"+ uid + "', '88');\n";
        String dept_sql = "INSERT INTO SYSTEM_DEPT (dept_id, company_id)\n" +
                "VALUES('88', '"+ uid + "');\n";
        sql = user_sql + role_sql + user_role_sql + post_sql + user_post_sql + menu_sql + role_menu_sql + dept_sql;
        writer.write(sql);
        writer.close();

        Thread.sleep(1000*3);   // 休眠3秒,保证文件被保存

        Connection conn;
        //jdbc:mysql://localhost:3306/pt?
        String url="JDBC:mysql://localhost:3306/pt?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username="root";
        String password="123456";
        try {
           *//* Configuration configuration = new PropertiesConfiguration(ScriptRunnerExecSql.class.getClassLoader().getResource("")
                    + "META-INF/spring/db.properties");*//*
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, username, password);
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            runner.runScript(Resources.getResourceAsReader("sql/output8.sql"));//在resouse中新建一个文件夹：然后放入sql文件
            //runner.runScript(Resources.getResourceAsReader("sql/CC21-01.sql"));
            runner.closeConnection();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*SystemUser s1 = new SystemUser();
        s1.setUserName("admin");
        s1.setPassword(ENCODER.encode("123456"));
        s1.setCompanyId(uid);
        s1.setUserId(IdUtils.simpleUUID());
        systemUserRepository.save(s1);*/
        if (result != null) {
            return "success";
        } else {
            return "error!";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody CompanyId companyId) {
        CompanyId c0 = companyIdRepository.findByCompanyId(companyId.getCompanyId());
        c0.setUrl(companyId.getUrl());
        c0.setCompanyName(companyId.getCompanyName());
        c0.setType(companyId.getType());
        CompanyId result = companyIdRepository.save(c0);
        if (result != null) {
            return "success";
        } else {
            return "error!";
        }
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<CompanyId> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return companyIdRepository.findAll(request);
    }
}

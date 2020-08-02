package com.pt.center;


import com.pt.center.newentity.*;
import com.pt.center.repository.*;
import com.pt.center.service.UsernameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WjApplicationTests {

	@Autowired
	private  ConsumerRepository consumerRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CodeRepository codeRepository;
	@Autowired
    CompanyAccountRepository companyAccountRepository;
	@Autowired
	UsernameService usernameService;
	@Autowired
	private OrderRepository orderRepository;
	@Test
	public void orderTest() {//order测试
		/*Order or1 = orderRepository.findByNumber("asd123");
		if(or1==null)
			System.out.println("不存在订单");
		else System.out.println(or1);*/
		orderRepository.deleteByNumber("asd123");
		Order order = new Order();
		order.setId(2);
		order.setAuditor("lisi");
		order.setCompany_code("6541");
		order.setCompany_name("果蔬企业A");
		order.setCreate_time("2020年6月15日");
		order.setNumber("sdw56");
		order.setProduct("菠萝");
		order.setSingleprice("0.01");
		order.setQuantity("20000");
		order.setTotalprice("200");
		order.setState("0");
		Order order1 = orderRepository.save(order);
		System.out.println(order1);
	}
	@Test
	public void contextLoads() {
		/*CompanyAccount c1 = companyAccountRepository.findByCode("720");
		Company c0 = companyRepository.findByNumber("123");
		if(c1==null)
			System.out.println("不存在公司");
		else System.out.println(c1);*/
		String s1,s2,s3;
		s1 = usernameService.randomGenerate(5);
		s2 = usernameService.randomUsername(8);
		s3 = usernameService.randomWebsite(9);
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
	}

	@Test
	public void saveConsumer(){
		Consumer consumer = new Consumer();
		consumer.setId(2);
		consumer.setName("李四");
		consumer.setPhone("1823");
		consumer.setPoints("0");
		consumer.setProduct("苹果");
		consumer.setProduct_company("APPLE COMPANY");
		consumer.setCode("a2asd1");
		consumer.setQuery_time("20200102");
		consumer.setAddress("北京");
		Consumer consumer1 = consumerRepository.save(consumer);
		System.out.println(consumer1);
		//Company company1 = companyRepository.save(company);
		//System.out.println(company1);
	}
	@Test
	public void saveCompanyAccount(){
		CompanyAccount companyAccount = new CompanyAccount();
		companyAccount.setId(2);
		companyAccount.setCode("7410");
		companyAccount.setName("水果测试1");
		companyAccount.setValidity("1");
		companyAccount.setWebsite("www.baidu.com");
		CompanyAccount com2 = companyAccountRepository.save(companyAccount);
		System.out.println(com2);
		//Company company1 = companyRepository.save(company);
		//System.out.println(company1);
	}

	@Test
	public void saveCompany(){
		Company company = new Company();
		company.setId(21);
		company.setName("阿发水果店");
		company.setPerson("阿发");
		company.setCode("3210");
		company.setNumber("112233");
		company.setAddress("北京");
		Company company1 = companyRepository.save(company);
		System.out.println(company1);
	}
	@Test
	public void saveCode(){
		Code code0 = new Code();
		code0.setId(2);
		code0.setCode("ead");
		code0.setTimes("0");
		code0.setCompany("企业B");
		code0.setProduct("香蕉");
		//codeRepository.save(code0);
		Code code1 = codeRepository.save(code0);
		System.out.println(code1);
	}
	@Test
	public void findCompany(){
		String str = "3210";
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(str);
		if (m.matches()) {//有数字 查找统一社会信用码
			Company c1 = companyRepository.findByCode(str);
			if(c1 == null)
				System.out.println("查无此公司");
			else {
				String c1Name = c1.getName();
				String c1Code = c1.getCode();
				System.out.println("查询的公司:"+c1Name+" 统一社会信用码："+c1Code);
			}
		}
		else{//没有数字 找名字
			Company c2 = companyRepository.findByName(str);
			if(c2 == null)
				System.out.println("查无此公司");
			else {
				String c2Name = c2.getName();
				String c2Code = c2.getCode();
				System.out.println("查询的公司:"+c2Name+" 统一社会信用码："+c2Code);
			}
		}
	}
	@Test
	public void findCompanyByUsername(){
		String str = "admin";
		Company c1 = companyRepository.findByUsername(str);
		System.out.println(c1);
	}
	@Test
	public void deCompany(){
		companyRepository.deleteById(20);
	}

	@Test
	public void upCompany(){
		Company c0 = companyRepository.findByUsername("admin");
		c0.setAddress("合肥");
		companyRepository.save(c0);
	}

	@Test
	public void findById(){
		String testcode = "123+zxc";
		if(testcode.indexOf("+") == -1){//不包含加号，只有防伪码
			List<Code> res = codeRepository.findByCode(testcode);
			if(res.size() == 0) {
				System.out.println("您所查询的防伪码不存在，谨防假冒!");
			}
			else{
				String product = res.get(0).getProduct();
				String comp = res.get(0).getCompany();
				String times = res.get(0).getTimes();
				Integer tt = Integer.valueOf(times);
				tt = tt + 1;
				res.get(0).setTimes(String.valueOf(tt));//更新查询次数
				codeRepository.save(res.get(0));
				System.out.println("该产品为真品。产品:" + product + " 公司：" + comp + " 查询次数：" + tt);
			}
		}
		else{
			String[] split = testcode.split("\\+");
			List<Code> res = codeRepository.findByCode(split[1]);
			if(res.size() == 0) {
				System.out.println("您所查询的防伪码不存在，谨防假冒!");
			}
			else{
				String product = res.get(0).getProduct();
				String comp = res.get(0).getCompany();
				String times = res.get(0).getTimes();
				Integer tt = Integer.valueOf(times);//获取查询次数
				tt = tt + 1;
				res.get(0).setTimes(String.valueOf(tt));//更新查询次数
				codeRepository.save(res.get(0));
				System.out.println("该产品为真品。产品:" + product + " 公司：" + comp + " 查询次数：" + tt);

				Consumer consumer0 = consumerRepository.findByPhone(split[0]);//设置消费者查询防伪码的信息
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				consumer0.setQuery_time(df.format(new Date()));
				consumer0.setAddress("上海");
				consumer0.setCode(split[1]);
				consumer0.setProduct(product);
				consumer0.setProduct_company(comp);
				String points = consumer0.getPoints();//获取积分
				Integer t2 = Integer.valueOf(points);//化为整型数
				t2 = t2 + 1;

				if(times.equals("0"))//原产品查询次数为0才加消费者积分
					consumer0.setPoints(String.valueOf(t2));
				consumerRepository.save(consumer0);
			}
		}
	}

}

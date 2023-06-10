package com.school;

import com.alibaba.fastjson.JSON;
import com.school.dao.FoundDao;
import com.school.dao.LostDao;
import com.school.dao.LostFoundDao;
import com.school.dao.UserDao;
import com.school.entity.Found;
import com.school.entity.Lost;
import com.school.entity.Lostfoundtype;
import com.school.entity.User;
import com.school.services.impl.FoundDatailService;
import com.school.services.impl.LostDetailService;
import com.school.services.impl.LostFoundTypeService;
import com.school.services.impl.UserService;
import com.school.utils.DateUtil;
import com.school.utils.RedisService;
import com.school.utils.Util;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UnitTest {

    @Resource
    private LostFoundTypeService lostFoundTypeService;
    @Resource
    private LostDetailService lostDetailService;
    @Resource
    private UserService userService;
    @Resource
    private LostFoundDao lostFoundDao;
    @Resource
    private RedisService redisService;
    @Resource
    private FoundDao foundDao;
    @Resource
    private UserDao userDao;
    @Resource
    private Util util;
    @Resource
    private LostDao lostDao;
    @Resource
    private FoundDatailService foundDatailService;

    @Test
    public void type() {
        System.err.println(lostFoundTypeService.getAllType());
        System.err.println(lostFoundTypeService.getAllTypeByR());


    }

    @Test
    public void typeDetail() {
        System.err.println(lostDetailService.getLostDetailList(5));

    }

    @Test
    public void userNameId() {
        System.err.println(lostDetailService.getUserName(1));
    }

    @Test
    public void user() {
        String phone = "18682975515";
        String pwd = "a11111112";
//        if(userDao.resetPwd(phone,pwd)){
//            System.err.println("重置成功");
//        }else {
//            System.err.println("失败");
//        }
//        System.err.println(userService.resetPwd(phone, pwd));

        //   System.err.println(lostDao.getAllByIdLostList(3).toString());
    }

    @Test
    public void setLostFoundDao() {
        List<Lostfoundtype> lostfoundtypes = lostFoundDao.GetAll();
        String typeName = "数码设备";
        int typeId = 0;
        for (int i = 0; i < lostfoundtypes.size(); i++) {
            Lostfoundtype lostfoundtype = lostfoundtypes.get(i);
            if (lostfoundtype.getName().equals(typeName)) {
                typeId = lostfoundtype.getId();
                System.err.println(typeId);
                return;
            }
        }
    }

    @Test
    public void addLost() {
        Date date = new Date();
        Lost lost = new Lost("丢失一件外套", "https://i04piccdn.sogoucdn.com/517b824ba880cacd", date, "在教学楼丢失", "教学楼", "18682675515", "未找到", 0, 4, 3, "浩祥");
        String jsonStr = JSON.toJSONString(lost);
        System.err.println(lostDetailService.addLost(jsonStr));
        System.err.println(lostDetailService.getLost());

    }

    @Test
    public void redis() {
        redisService.set("test", "www.baidu.com");
        System.err.println(redisService.get("test"));
    }

    @Test
    public void sms() {
        String mobile_code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        System.err.println(mobile_code);
        redisService.del("code");
        redisService.set("code", mobile_code);
        System.err.println(redisService.get("code").toString());
    }

    @Test
    public void setFoundDao() {
        // System.err.println(foundDao.selectByTypeId(1).toString());
        // System.err.println(foundDao.searchUserNameId(1));
        Date date = new Date();
        Found found = new Found("丢失一件外套", "https://i04piccdn.sogoucdn.com/517b824ba880cacd", date, "在教学楼丢失", "教学楼", "18682675515", "未找到", 0, 4, 3, "浩祥");
        foundDao.addFound(found);
        System.err.println(foundDao.selectByTypeId(4).toString());

    }

    @SneakyThrows
    @Test
    public void util() {
        // 邮件服务器参数
        String host = "smtp.qq.com";
        int port = 587;
        String username = "3502777299@qq.com";
        String password = "fxtxioywllmrcjbd";

        // 收件人邮箱和发送内容
        String to = "a3502777299@outlook.com";
        String subject = "测试邮件";
        String text = "这是一封测试邮件，请勿回复。";

        // 创建Properties类用于记录相关配置
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        // 创建认证器
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // 创建Session对象
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session session = Session.getInstance(props, authenticator);

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        // 发送邮件
        Transport.send(message);
    }

    @SneakyThrows
    @Test
    public void addUser() {
        ArrayList<String> phoneNumbers = new ArrayList<>();
        // 生成50组11位电话号码
        for (int i = 0; i < 50; i++) {
            String phoneNumber = generatePhoneNumber();
            phoneNumbers.add(phoneNumber);
        }
        for (String phone : phoneNumbers) {
            Date time = DateUtil.getTime();//获取时间
            Integer bigDecimal = 1000;
            String nickname = Util.NickNameRandom();//随机获取昵称
            String photo = Util.ImageSearch("头像");//随机获取头像
            User user = new User(nickname, phone, photo, "男", bigDecimal, 100, time);
            userDao.register(phone, user);
            Integer userid = userDao.findUserByPhone(phone);


        }
    }

    @SneakyThrows
    @Test
    public void update() {
        List<Found> allList1 = foundDao.getAllList();
        for (Found found : allList1) {
            int min = 1;
            int max = 54;
            Random random = new Random();
            int randomNumber = random.nextInt(max - min + 1) + min;
            String imageSearch = Util.ImageSearch(found.getTitle());
            foundDao.updateFound(imageSearch, randomNumber, found.getId());
        }


    }

    private String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        sb.append("1"); // 手机号以1开头

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }

    @Test
    public void found() {
        List<User> getalll = userDao.getalll();
        for (User next : getalll) {
            foundDao.updatephone(next.getId(), next.getPhone());
        }
    }

    @Test
    public void lost() {
        System.err.println(foundDatailService.showFoundList(0));

    }
}

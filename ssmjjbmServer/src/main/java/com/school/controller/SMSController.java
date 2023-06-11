package com.school.controller;

import com.school.utils.RedisService;
import com.school.utils.ServerResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;

@Controller
public class SMSController {
    @Resource
    private RedisService redisService;

    /**
     * @param phone  手机号
     * @param codes  验证码
     * @param opCode 操作码，用于判断是发送验证码还是校验验证码  send发送短信，verify验证短信
     * @return
     */
    @ResponseBody
    @RequestMapping("/sms")
    public ServerResponse SMS(String phone, String codes, String opCode) {
        String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
        if (opCode.equals("send")) {
            Random random = new Random();
            String mobile_code = Integer.toString(random.nextInt(900000) + 100000);
            redisService.del("mobile_code");//清除历史缓存
            redisService.del("phone");//清除缓存
            redisService.set("phone", phone);//将手机号存入redis用于验证码校验
            redisService.set("mobile_code", mobile_code); //将验证码存入redis用于验证码校验
            String content = "您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。";
<<<<<<< HEAD
            //发送短信  参考网站 https://user.ihuyi.com/new/login.html
            NameValuePair[] data = {//提交短信
                    new NameValuePair("account", "你的账户"), //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                    new NameValuePair("password", "你的密码"), //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
=======
            //发送短信 api 网站 https://user.ihuyi.com/new/login.html
            NameValuePair[] data = {//提交短信
                    new NameValuePair("account", "你自己的用户名"), //查看用户名 登录用户中心->验证码通知短信>产品总览->API接口信息->APIID
                    new NameValuePair("password", "你自己的密码"), //查看密码 登录用户中心->验证码通知短信>产品总览->API接口信息->APIKEY
>>>>>>> 431ed57026ba4c31efb56236e2985971708f9c44
                    new NameValuePair("mobile", phone),
                    new NameValuePair("content", content),
            };
            method.setRequestBody(data);
            try {
                client.executeMethod(method);
                String SubmitResult = method.getResponseBodyAsString();
                Document doc = DocumentHelper.parseText(SubmitResult);
                Element root = doc.getRootElement();
                String code = root.elementText("code");
                String msg = root.elementText("msg");
                String smsid = root.elementText("smsid");
                System.err.println(code);
                System.err.println(msg);
                System.err.println(smsid);
                if ("2".equals(code)) {
                    return ServerResponse.createServerResponseBySuccess("验证码发送成功");
                } else {
                    return ServerResponse.createServerResponseByFail(1, "验证码发送失败");
                }

            } catch (IOException | DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (opCode.equals("verify")) {
            //从缓存中读取手机号和验证码
            String phone1 = String.valueOf(redisService.get("phone"));
            String mobile_code = String.valueOf(redisService.get("mobile_code"));
            if (codes.equals(mobile_code) && phone.equals(phone1)) {
                //验证成功
                return ServerResponse.createServerResponseBySuccess("手机号验证成功!");
            } else {
                //验证失败
                return ServerResponse.createServerResponseByFail(1, "手机号验证失败!");
            }
        }
        return ServerResponse.createServerResponseBySuccess("未知操作");
    }

}

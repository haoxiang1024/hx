package com.school.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Util {

    private static String imgUrl = "";//网络图片地址
    private static Lock globalCountLock = new ReentrantLock();//线程锁保证线程安全

    /**
     * @return 返回随机昵称
     */
    public static String NickNameRandom() {
        String[] TWO_LETTERS = {"阿宝", "蓝妹", "娃娃", "小花", "小丸", "皮蛋",
                "乐乐", "小兔", "茶茶", "奶糖", "亲亲", "小虎",
                "小猫", "小狗", "小鸟", "小羊", "小龙", "小鹿",
                "小雨", "银子", "小美", "小妞", "爱心", "乖乖",
                "小鱼", "欣欣", "笑笑", "小熊", "小娃", "蜜蜂",
                "糖糖", "大王", "小贝", "小太阳", "小米", "小蚂蚁",
                "小珍珠", "胖胖", "多多", "小瑜", "小丹", "小坤",
                "小辉", "小妮", "小艺", "小萌", "瑶瑶", "小菲",
                "小凡"};

        final String[] THREE_LETTERS = {"小草莓", "小蜜蜂", "小蚂蚁", "小熊猫", "小白兔", "小乖乖",
                "小可爱", "小甜甜", "小糖果", "小霸王", "小跳跳", "小小虎",
                "小土豆", "小豆豆", "小蘑菇", "小仙女", "小妞妞", "小宝贝",
                "小喵喵", "小汪汪", "小姐姐", "小妹妹", "小斑点", "小狮子",
                "小飞花", "小精灵", "小天使", "小懒虫", "小香蕉", "小葡萄",
                "小奶瓶", "小火腿", "小书虫", "小蛋糕", "小铃铛", "小卷毛",
                "小蜗牛", "小小猪", "小甜心", "小苹果", "小陀螺", "小海豚",
                "小小雅", "小呆呆", "小寿司", "小饼干", "小雪糕", "小水蜜桃",
                "小可爱茶", "小甜甜梦"};
        final Random rand = new Random();
        int type = rand.nextInt(2); // 随机选择两个或三个字的昵称
        if (type == 0) {
            return TWO_LETTERS[rand.nextInt(TWO_LETTERS.length)];
        } else {
            return THREE_LETTERS[rand.nextInt(THREE_LETTERS.length)];
        }
    }

    /**
     * @param oldPic 原图地址
     * @return 新图地址
     */
    public static String updatePic(String oldPic) {
        Pattern pattern = Pattern.compile(".*http.*"); // 此处使用的正则表达式是".*world.*"，匹配包含"http"的任何字符串。
        Matcher matcher = pattern.matcher(oldPic); // 将正则表达式应用到输入字符串上
        String newPic = "";
        if (!matcher.matches()) {
            //获取ip地址
            String ip = Ip.getIp();
            newPic = "http://" + ip + ":8082/images/" + oldPic;
           // newPic = "http://111.67.203.931:8082/loadPhoto/" + oldPic;
            return newPic;
        }
        return oldPic;
    }

    /**
     *
     * @return 返回随机图片地址从搜狗图片网站获取
     * @throws Exception 抛出异常
     */

    public static String ImageSearch(String query) throws Exception {
        String url = "https://pic.sogou.com/pics?query=" + URLEncoder.encode(query, "utf-8");
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        String pattern = "\"thumbUrl\":\"(.*?)\"";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(result.toString());

        ArrayList<String> imageUrlList = new ArrayList<>();
        while (m.find()) {
            String imageUrl = m.group(1);
            imageUrlList.add(imageUrl);
        }
        Random random = new Random();
        int index = random.nextInt(imageUrlList.size());
        return imageUrlList.get(index).replaceAll("\\\\u002F", "/");
    }
}

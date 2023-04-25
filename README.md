# TemplateAppProject

## 使用方式

[视频教程-如何使用模板工程](https://www.bilibili.com/video/av92348545)

1.克隆项目

```
git clone https://github.com/xuexiangjys/TemplateAppProject.git
```

2.修改项目名（文件夹名），并删除目录下的.git文件夹（隐藏文件）

3.使用AS打开项目，然后修改`包名`、`applicationId`和`app_name`

* 修改包名

![templateproject_1.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/templateproject/1.png)

![templateproject_2.png](https://raw.githubusercontent.com/xuexiangjys/Resource/maste
![templateproject_3.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/templateproject/3.png)

* 修改app_name

![templateproject_5.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/templateproject/5.png)

## 项目打包

1.修改工程根目录的`gradle.properties`中的`isNeedPackage=true`。

2.添加并配置keystore，在`versions.gradle`中修改`app_release`相关参数。

3.如果考虑使用友盟统计的话，在`local.properties`中设置应用的友盟ID:`APP_ID_UMENG`。

4.使用`./gradlew clean assembleReleaseChannels`进行多渠道打包。

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/pay/pay.png)

感谢下面小伙伴的打赏：

姓名 | 金额 | 方式
:-|:-|:-
myie9 | 100￥ | 微信
*鸥 | 10.24￥ | 微信
**家 | 10.24￥ | 支付宝
*寻 | 20.48￥ | 微信

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![](https://s1.ax1x.com/2022/04/27/LbGMJH.jpg)

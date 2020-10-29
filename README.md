# 将5E平台优先登陆的验证码放到其他平台查看（谷歌验证器）#

懒得打开手机看验证码所以有了此项目:D

演示站：xxx.com

# Usage
```
mvn install

java -jar 

API: http://127.0.0.1:8080/getAuth?acc=5e账号（必填，用于刷新token）&pswd=5e密码（同上）&device_code=APP里提取设备码

result:

{
    "code": 200,
    "msg": "成功",
    "data": {
        "code": "162072",//当前登录验证码
        "google_key": "MJ7UKL6XDHPKP2OI"//谷歌验证器密钥
    }
}


如何从APP里提取设备码
1.安装5e官方APP
2.进行人脸认证且保证手机上可以看到登陆验证码
3.覆盖安装 5E设备码提取.apk 注：如提示签名问题请卸载官方app后使用本项目提供的5E重签名版.apk，然后从第2步重新开始
4.不出意外 5E设备码提取的APP界面上会显示设备码
```
![](Screenshot_com.fiveplay.jpg)

## Windows

### Chrome 插件

https://chrome.google.com/webstore/detail/authenticator/bhghoamapcdpbohphigoooaddinpkbai

### 支持谷歌身份验证的 Winauth

https://winauth.github.io/winauth/download.html

![](doc.png)

TODO
- [x] API 获取密钥及当前验证码
- [ ] WEB界面
- [ ] 演示站
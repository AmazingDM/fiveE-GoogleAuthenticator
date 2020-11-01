package com.dm.fiveE.Controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dm.fiveE.GoogleAuthenticatorUtils;
import com.dm.fiveE.Result.Result;
import com.dm.fiveE.Result.ResultEnum;
import com.dm.fiveE.Result.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {

  @RequestMapping("/getAuth")
  public Result getAuth(String acc, String pswd, String device_code) {
    if (StrUtil.isAllEmpty(device_code)) {
      return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(), ResultEnum.DATA_IS_NULL.getMsg());
    }
    try {
      // 登陆5E获取Token
      Map<String, Object> login_map = new HashMap<>();
      login_map.put("account", acc);
      login_map.put("password", pswd);
      String login_result = HttpUtil.post("https://app.5eplay.com/api/user/login", login_map);
      JSONObject login_result_json = JSON.parseObject(login_result);
      if (!login_result_json.getBoolean("success")) {
        return ResultUtil.error(
            ResultEnum.UNKNOWN_ERROR.getCode(), login_result_json.getString("message"));
      }
      String token = JSON.parseObject(login_result).getJSONObject("data").getString("user_token");

      String getGoogleAuth_result =
          HttpRequest.get(
                  "https://app.5eplay.com/api/user/user_status_info?device_code=" + device_code)
              .header("token", token)
              .execute()
              .body();

      JSONObject getGoogleAuth_result_json = JSON.parseObject(getGoogleAuth_result);
      if (!getGoogleAuth_result_json.getBoolean("success")) {
        return ResultUtil.error(
            ResultEnum.UNKNOWN_ERROR.getCode(), getGoogleAuth_result_json.getString("message"));
      }

      String Google_Authenticator =
          JSON.parseObject(getGoogleAuth_result).getJSONObject("data").getString("asval");
      String code = GoogleAuthenticatorUtils.getTOTPCode(Google_Authenticator);

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("google_key", Google_Authenticator);
      jsonObject.put("code", code);
      return ResultUtil.success(jsonObject);
    } catch (Exception e) {
      e.printStackTrace();
      return ResultUtil.error(
          ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
    }
  }
}

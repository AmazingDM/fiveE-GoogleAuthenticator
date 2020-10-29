package com.dm.fiveE.fiveE.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dm.fiveE.fiveE.GoogleAuthenticatorUtils;
import com.dm.fiveE.fiveE.Result.Result;
import com.dm.fiveE.fiveE.Result.ResultEnum;
import com.dm.fiveE.fiveE.Result.ResultUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

@RestController
public class APIController {

	@RequestMapping("/getAuth")
	public Result getAuth(String acc, String pswd, String device_code, String token, Integer _type) {
		if (StrUtil.isAllEmpty(device_code)) {
			return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(), ResultEnum.DATA_IS_NULL.getMsg());
		}
		try {
			if (StrUtil.isBlank(token)) {
				// 登陆5E获取Token
				Map<String, Object> login_map = new HashMap<>();
				login_map.put("account", acc);
				login_map.put("password", pswd);
				String login_result = HttpUtil.post("https://app.5eplay.com/api/user/login", login_map);
				token = JSON.parseObject(login_result).getJSONObject("data").getString("user_token");
			}

			String getGoogleAuth_result = HttpRequest
					.get("https://app.5eplay.com/api/user/user_status_info?device_code=" + device_code)
					.header("token", token).execute().body();

			String Google_Authenticator = JSON.parseObject(getGoogleAuth_result).getJSONObject("data")
					.getString("asval");
			String code = GoogleAuthenticatorUtils.getTOTPCode(Google_Authenticator);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("google_key", Google_Authenticator);
			jsonObject.put("code", code);
			return ResultUtil.success(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
		}

	}

	public static void main(String[] args) {

	}
}

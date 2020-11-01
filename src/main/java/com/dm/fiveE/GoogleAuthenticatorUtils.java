package com.dm.fiveE;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

public class GoogleAuthenticatorUtils {

  /**
   * 根据密钥，计算出当前时间的动态口令 （30s会变化一次）
   *
   * @param secretKey
   * @return
   */
  public static String getTOTPCode(String secretKey) {
    Base32 base32 = new Base32();
    byte[] bytes = base32.decode(secretKey);
    String hexKey = Hex.encodeHexString(bytes);
    long time = (System.currentTimeMillis() / 1000) / 30;
    String hexTime = Long.toHexString(time);
    return TOTP.generateTOTP(hexKey, hexTime, "6");
  }
}

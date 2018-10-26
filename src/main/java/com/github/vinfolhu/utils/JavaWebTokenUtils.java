package com.github.vinfolhu.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class JavaWebTokenUtils {

	private static Logger log = LoggerFactory.getLogger(JavaWebTokenUtils.class);

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private static MessageDigest mdInst;
	
	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

	private static final Key signingKey = new SecretKeySpec("token".getBytes(), signatureAlgorithm.getJcaName());

	private static final JwtBuilder jwtBuilder = Jwts.builder().signWith(signatureAlgorithm, signingKey);

	private static final JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey);

	public synchronized static MessageDigest getMdDigest() {

		if (mdInst != null) {
			return mdInst;
		}
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return mdInst;
	}

	/**
	 * Encrypt the password using the MD5 algorithm
	 */
	public final static String getMD5Value(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			// 使用指定的字节更新摘要
			getMdDigest().update(btInput);
			// 获得密文
			byte[] md = getMdDigest().digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
	public static String createJavaWebToken(Map<String, Object> claims) {
		return jwtBuilder.setClaims(claims).compact();
	}

	// 解析Token，同时也能验证Token，当验证失败返回null
	public static Map<String, Object> parserJavaWebToken(String jwt) {
		try {
			return jwtParser.parseClaimsJws(jwt).getBody();
		} catch (Exception e) {
			log.error("json web token verify failed");
			return null;
		}
	}
}
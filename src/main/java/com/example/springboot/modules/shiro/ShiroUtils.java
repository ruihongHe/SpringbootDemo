package com.example.springboot.modules.shiro;

import com.example.springboot.common.constant.JwtConstant;
import com.example.springboot.common.exception.CmsException;
import com.example.springboot.common.util.JwtUtil;
import com.example.springboot.common.util.ResultCodeEnum;
import com.example.springboot.modules.entity.SysUser;
import com.example.springboot.modules.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Shiro工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class ShiroUtils {

	private final SysUserService sysUserService;


	/**  加密算法 */
	public final static String hashAlgorithmName = "SHA-256";
	/**  循环次数 */
	public final static int hashIterations = 16;

	public ShiroUtils(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}


	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	/**
	 * 获取当前登录用户
	 */
	public  SysUser getUserEntity() {
		String token = getToken();
		// 解密获得Account
		String username = JwtUtil.getClaim(token, JwtConstant.USERNAME);
		SysUser user =sysUserService.selectUserByName(username) ;
		// 用户是否存在
		if (user == null) {
			throw new CmsException(500,"该帐号不存在(The account does not exist.)");
		}
		return user;
	}
	/**
	 * 获取当前登录用户id
	 */
	public  Long getUserId() {
		return getUserEntity().getUserId();
	}

	/**
	 * 获取当前登录用户Token
	 */
	public static String getToken() {
		return getSubject().getPrincipal().toString();
	}

	/**
	 * 获取当前登录用户Username
	 */
	public static String getUsername() {
		String token =getSubject().getPrincipal().toString();
		// 解密获得Username
		return JwtUtil.getClaim(token, JwtConstant.USERNAME);
	}



	public static boolean isLogin() {
		return getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 获取验证码

	public static String getKaptcha(String key) throws Exception {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new CmsException(ResultCodeEnum.KAPTCHA_ERROR);
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}*/

}

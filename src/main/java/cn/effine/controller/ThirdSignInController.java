/**
 * @author effine
 * @Date 2015年10月19日  上午9:55:18
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 第三方登录
 */
@Controller
@RequestMapping("signin")
// TODO effine 第三方登录
public class ThirdSignInController {

	/**
	 * QQ登录
	 *
	 * @return
	 */
	@RequestMapping("qq")
	public String QQSignin() {
		return null;
	}

	/**
	 * 微信登录
	 *
	 * @return
	 */
	@RequestMapping("wechat")
	public String wechatSignin() {
		return null;
	}

	/**
	 * 新浪微博登录
	 *
	 * @return
	 */
	@RequestMapping("weibo")
	public String weiboSignin() {
		return null;
	}

	/**
	 * Github登录
	 *
	 * @return
	 */
	@RequestMapping("github")
	public String githubSignin() {
		return null;
	}
}

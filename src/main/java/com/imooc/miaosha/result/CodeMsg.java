package com.imooc.miaosha.result;

import lombok.Getter;
import sun.print.ServiceDialog;

@Getter
public class CodeMsg {



    private int code;
	private String msg;
	
	//通用异常
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常: %s");
	public static final CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102,"请求非法");

	//登录模块 5002XX
	public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500210,"手机号不存在");
	public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500211,"密码错误");
	public static final CodeMsg SESSION_ERROR = new CodeMsg(500212,"session不存在或已经失效");

	//商品模块 5003XX
	
	//订单模块 5004XX
	public static final CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400,"订单不存在");
	
	//秒杀模块 5005XX
	public static final CodeMsg MIAO_SHA_OVER = new CodeMsg(500500,"商品已经秒杀完毕");
	public static final CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");

	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public CodeMsg(String msg) {
		this.code = 500100;
		this.msg = msg;
	}

	public CodeMsg fillArgs(Object... args){
		int code = this.code;
		String massage = String.format(this.msg, args);
		return new CodeMsg(code,massage);
	}
}

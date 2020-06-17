package com.gxuwz.medical_2.exception;
/**
 * 角色不存在异常类
 * @author 演示
 *
 */
public class RoleNotFoundException extends Exception {

	public RoleNotFoundException(String message) {
		super(message);
	}

	public RoleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}

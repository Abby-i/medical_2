package com.gxuwz.medical_2.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符集编码过滤器，默认设置为UTF-8
 * 
 * @author 演示
 * 
 */
public class CharacterEncodingFilter implements Filter {

	private String encoding;

	private boolean forceRequestEncoding = false;

	private boolean forceResponseEncoding = false;
	
	private boolean forceEncoding=false;
	/**
	 * 加载配置参数
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		//加载web.xml文件中过滤器的配置参数
		encoding =filterConfig.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		String encoding = getEncoding();
		if (encoding != null) {
			if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(encoding);
			}
			if (isForceResponseEncoding()) {
				response.setCharacterEncoding(encoding);
			}
		}

		filterChain.doFilter(request, response);
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isForceRequestEncoding() {
		return forceRequestEncoding;
	}

	public void setForceRequestEncoding(boolean forceRequestEncoding) {
		this.forceRequestEncoding = forceRequestEncoding;
	}

	public boolean isForceResponseEncoding() {
		return forceResponseEncoding;
	}
	public boolean isForceEncoding() {
		return forceEncoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}
	public void setForceResponseEncoding(boolean forceResponseEncoding) {
		this.forceResponseEncoding = forceResponseEncoding;
	}

	public void destroy() {
	}

}

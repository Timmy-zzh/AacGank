package com.timmy.aacgank.bean.base;

/**
 * 支付宝数据-->可以转化为StringResult
 */
@SuppressWarnings("serial")
public class StringResult extends SupperResult {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}

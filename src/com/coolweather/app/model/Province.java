package com.coolweather.app.model;

public class Province {
	private int id;
	private String provinceName;
	private String provinceCode;
	
	public int getId() {
		return id;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}

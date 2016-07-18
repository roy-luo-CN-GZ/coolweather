package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/**
	 * ���ݿ���
	 */
	public static final String DB_NAME="cool_weather";
	
	/**
	 * ���ݿ�汾
	 */
	public static final int VERSION=1;
	
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * �����췽��˽�л�
	 * @param context
	 */
	private CoolWeatherDB(Context context) {
		// TODO �Զ����ɵĹ��캯�����
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	/**
	 * ��ȡCoolWeather��ʵ��
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null){
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;	
	}
	
	/**
	 * ��Provinceʵ�����浽���ݿ�
	 * @param province
	 */
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡȫ�����е�ʡ����Ϣ
	 * @return
	 */
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		while(cursor.moveToNext()){
			Province province=new Province();
			province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			list.add(province);
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	/**
	 * ��Cityʵ�����浽���ݿ�
	 * @param city
	 */
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
			
		}	
	}
	
	/**
	 * �����ݿ��ȡĳʡ�����еĳ�����Ϣ
	 * @param provinceId
	 * @return
	 */
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		while(cursor.moveToNext()){
			City city=new City();
			city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			city.setProvinceId(provinceId);
			list.add(city);
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
	/**
	 * ��Countyʵ�����浽���ݿ�
	 * @param county
	 */
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡĳ���������е�����Ϣ
	 * @param cityId
	 * @return
	 */
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		while(cursor.moveToNext()){
			County county=new County();
			county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			county.setCityId(cityId);
			list.add(county);
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
	
}
package com.sc.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigIO {

	
	/**
	 * @return 说说的数量
	 */
	public static int readShuoShuoNum() {
		Properties props = new Properties();
		try {
			InputStream in = new FileInputStream("/home/shenchao/workspace/config.properties");
			props.load(in);
			String value = props.getProperty("shuoshuo_num");
			return Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	
	/**
	 * @param value  说说的数量
	 */
	public static void writeShuoShuoNum(int value) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream("/home/shenchao/workspace/config.properties");
			prop.load(fis);
			OutputStream fos = new FileOutputStream("/home/shenchao/workspace/config.properties");
            prop.setProperty("shuoshuo_num", value+"");
			prop.store(fos,"OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] ar) {
		writeShuoShuoNum(185);
		System.out.println(readShuoShuoNum());
	}

}

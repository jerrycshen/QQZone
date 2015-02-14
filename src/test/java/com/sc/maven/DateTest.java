package com.sc.maven;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		// 时间戳转化为Sting或Date
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(Long.parseLong("1561592577000"));
		Date date = format.parse(d);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONTH)+1);
		System.out.println(calendar.get(Calendar.DATE));
//		System.out.println("Format To String(Date):" + d);
//		System.out.println("Format To Date:" + date);
	}

}

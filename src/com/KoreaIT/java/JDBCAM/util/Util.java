package com.KoreaIT.java.JDBCAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	/**
	 * 포맷팅 된 날짜/시각 반환 Str
	 * 
	 * @param localDateTime
	 */
	public static String getNowDate_TimeStr(LocalDateTime localDateTime) {

		String formatedNow = localDateTime.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));

		return formatedNow;
	}
}
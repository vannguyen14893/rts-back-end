package com.cmc.recruitment.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertHelper {
	public static Date toDate(String stringDate) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

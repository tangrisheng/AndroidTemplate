package com.trs.template.utils;

import android.util.Base64;

public class Base64Utils {
	public static String encode(byte[] source) {
		return Base64.encodeToString(source, Base64.DEFAULT);
	}

	public static byte[] decode(String data) {
		return Base64.decode(data, Base64.DEFAULT);
	}
}

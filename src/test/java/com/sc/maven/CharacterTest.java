package com.sc.maven;

import java.io.UnsupportedEncodingException;

public class CharacterTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String string = new String("ç”·".getBytes("iso-8859-1"), "utf-8");
		System.out.println(string);
	}

}

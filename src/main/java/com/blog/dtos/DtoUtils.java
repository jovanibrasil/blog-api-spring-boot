package com.blog.dtos;

import org.apache.commons.codec.binary.Base64;

public class DtoUtils {

	/**
	 * Encode a image represented in an array of octets (bytes) into a image represented with 
	 * a Base64 string.
	 * 
	 * @param banner
	 * @return
	 */
	public static String encodeBase64(byte[] banner) {
		if(banner == null) return "";
		if(banner.length == 0) return "";
		
		return "data:image/png;base64," + Base64.encodeBase64String(banner);
	}
	
	/**
	 * Decode a image represented in a Base64 string into a image represented with 
	 * an array of octets (bytes).
	 * 
	 * @param banner
	 * @return
	 */
	public static byte[] decodeBase64(String banner) {
		if(banner == null) return new byte[0];
		if(banner.length() == 0) return new byte[0];
		
		banner = banner.substring(banner.indexOf(',')+1);
		return Base64.decodeBase64(banner);
	}
	
}

package com.blog.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;

public class ImageUtils {

	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}
	
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
			
		} catch (DataFormatException e) {
			return data;
		}
		return outputStream.toByteArray();
	}
	
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

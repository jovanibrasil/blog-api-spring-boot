package com.blog.utils;

import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

public class ImageUtilsTest {
	
	private final byte[] compressedImageExpected = { 120, -100, 51, 48, 52, 48, 52, 64, 1, -122, 0, 35, -42, 3, -108 };
	private String rawImage = "0101000000000000001";
	
	@Test
	public void testCompressBytes() {
		byte[] compressedBytes = ImageUtils.compressBytes(rawImage.getBytes());
		assertTrue(Objects.deepEquals(compressedImageExpected, compressedBytes));
	}
	
	@Test
	public void testDecompressBytes() {
		byte[] decompressBytes = ImageUtils.decompressBytes(compressedImageExpected);
		assertTrue(Objects.deepEquals(rawImage.getBytes(), decompressBytes));
	}

}

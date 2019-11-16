package com.blog.models;

import java.io.IOException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Image {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column
	private String name;
	@Column
	private String type;
	@Lob
	@Column
	private byte[] pic;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;
	
	@Transient
	private MultipartFile multipartBanner;
	
	public Image() {}
	
	public Image(MultipartFile mtf) {
		try {
			this.name = mtf.getOriginalFilename();
			this.type = mtf.getContentType();
			this.pic = mtf.getBytes();
			this.multipartBanner = mtf;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "ImageModel [id=" + id + ", name=" + name + ", type=" + type + ", pic=" + Arrays.toString(pic) + "]";
	}

	public MultipartFile getMultipartBanner() {
		return multipartBanner;
	}
	
}

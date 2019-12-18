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

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
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

}

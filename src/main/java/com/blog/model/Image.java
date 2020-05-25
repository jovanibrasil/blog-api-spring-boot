package com.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="images")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long id;
	@Column
	private String name;
	@Column
	private String type;
	@Lob
	@Column(length = 20, nullable = false)
	private byte[] bytes;
	
	@Transient
	private MultipartFile multipartBanner;

	public Image(MultipartFile mtf) {
		this.multipartBanner = mtf;
		this.setContent(mtf);
	}
	
	public void setContent(MultipartFile postBanner) {
		try {
			this.name = postBanner.getOriginalFilename();
			this.type = postBanner.getContentType();
			this.bytes = postBanner.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}

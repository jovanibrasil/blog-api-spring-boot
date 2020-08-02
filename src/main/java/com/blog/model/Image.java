package com.blog.model;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Type(type="org.hibernate.type.BinaryType")
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

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return false;
		
		Image image = (Image) obj;
		
		return Objects.equals(image.getId(), id)
				&& Objects.equals(image.getName(), name)
				&& Objects.equals(image.getType(), type)
				&& Objects.deepEquals(image.getBytes(), bytes);
	}	

}

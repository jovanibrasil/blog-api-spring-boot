ALTER TABLE posts DROP banner;
ALTER TABLE posts 
	ADD COLUMN banner_url varchar(30) 
	default '/images/image-not-found.png';
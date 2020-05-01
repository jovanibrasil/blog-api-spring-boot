CREATE TABLE images (
	image_id bigint not null auto_increment, 
	bytes BLOB,
	name varchar(50) not null, 
	type varchar(11) not null,
	primary key (image_id)
);

ALTER TABLE posts ADD COLUMN image_id bigint not null REFERENCES images(image_id);
ALTER TABLE posts DROP banner_url;






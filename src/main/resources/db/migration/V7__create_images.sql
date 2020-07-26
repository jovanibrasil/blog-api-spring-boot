CREATE TABLE images (
	image_id serial not null, 
	bytes bytea,
	name varchar(50) not null, 
	type varchar(11) not null,
	primary key (image_id)
);

ALTER TABLE posts ADD COLUMN image_id integer REFERENCES images(image_id);
ALTER TABLE posts DROP banner_url;

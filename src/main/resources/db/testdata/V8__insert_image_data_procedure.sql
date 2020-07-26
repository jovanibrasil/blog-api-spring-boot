CREATE PROCEDURE insert_images_blogdb_data() 
AS $$
BEGIN
	IF (EXISTS(SELECT post_id FROM posts)) then
		INSERT INTO images
			(name, type, bytes) VALUES ('image-not-found.png', 'image/png', pg_read_binary_file('/var/lib/image-not-found.png')); 
		UPDATE posts SET image_id = LASTVAL();
	END IF;
END ;
$$
LANGUAGE plpgsql ;
CALL insert_images_blogdb_data();

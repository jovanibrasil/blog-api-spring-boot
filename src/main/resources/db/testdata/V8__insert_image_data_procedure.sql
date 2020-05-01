DELIMITER $$
CREATE PROCEDURE insert_images_blogdb_data() 
BEGIN
	IF (EXISTS(SELECT post_id FROM `posts`)) then
		INSERT INTO `images`
			(`image_id`, `name`, `type`, `bytes`) VALUES (1,'image-not-found.png', 'image/png', LOAD_FILE('/var/lib/mysql-files/image-not-found.png')); 
		UPDATE `posts` SET image_id = 1;
	END IF;
END $$
DELIMITER ;

CALL insert_images_blogdb_data();

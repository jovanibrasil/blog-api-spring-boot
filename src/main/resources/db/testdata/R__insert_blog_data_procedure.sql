DELIMITER $$
CREATE PROCEDURE insert_blogdb_data() 
BEGIN
	IF (NOT EXISTS(SELECT post_id FROM `blogdb`.`posts`)) then
		INSERT INTO `blogdb`.`users`
			(`user_name`, `full_user_name`, `last_update_date`, `profile_type`)
			VALUES ("jovanibrasil", "Jovani Brasil", NOW(), "ROLE_ADMIN");
		INSERT INTO `blogdb`.`users`
			(`user_name`, `full_user_name`, `last_update_date`, `profile_type`)
			VALUES ("fakeuser", "Fake user", NOW(), "ROLE_USER");
		INSERT INTO `blogdb`.`posts`
			(`body`, `creation_date`, `last_update_date`, `summary`, `title`, `user_name`)
			VALUES ("Configurar um blog é uma tarefa que pode ser complexa, dependendo de que tipo de blog você tem e qual é o seu público.", 
			NOW(), NOW(), "Apenda a configurar seu blog", "Configurando meu blog", "fakeuser");
		INSERT INTO `blogdb`.`post_tags` (`post_id`, `tag`) VALUES (LAST_INSERT_ID(), "angular");
		INSERT INTO `blogdb`.`posts`
			(`body`, `creation_date`, `last_update_date`, `summary`, `title`, `user_name`)
			VALUES
			("Existem várias tecnologias disponíveis para desenvolvimento de um blog, cabe a você decidir qual é a melhor para você.", 
			NOW(), NOW(), "Descobrindo como desenvolver um blog", "Desenvolvendo um blog", "jovanibrasil");
		INSERT INTO `blogdb`.`post_tags` (`post_id`, `tag`) VALUES (LAST_INSERT_ID(), "docker");
		INSERT INTO `blogdb`.`posts`
			(`body`, `creation_date`, `last_update_date`, `summary`, `title`, `user_name`)
			VALUES
			("Escrever um post não é fácil como se imagina, depende do seu conhecimento técnico dos recursos disponíveis.", 
			NOW(), NOW(), "Como escrever um bom post", "Escrevendo posts", "fakeuser");
		INSERT INTO `blogdb`.`post_tags` (`post_id`, `tag`) VALUES (LAST_INSERT_ID(), "java");
		INSERT INTO `blogdb`.`posts`
			(`body`, `creation_date`, `last_update_date`, `summary`, `title`, `user_name`)
			VALUES ( "Quando falo em testar um blog estou me referindo ao mais importante: a percepção do usuário.", 
			NOW(), NOW(), "Métodos e técnicas para testar im blog", "Testando um blog", "jovanibrasil");
		INSERT INTO `blogdb`.`post_tags` (`post_id`, `tag`) VALUES (LAST_INSERT_ID(), "others");
		INSERT INTO `blogdb`.`posts`
			(`body`, `creation_date`, `last_update_date`, `summary`, `title`, `user_name`)
			VALUES ("O conhecimento é a única coisa que você leva para a vida toda e pode te trazer sucesso, riqueza e felicidade.", 
			NOW(), NOW(), "Viva o conhecimento", "Vivendo um blog", "fakeuser");
		INSERT INTO `blogdb`.`post_tags` (`post_id`, `tag`) VALUES (LAST_INSERT_ID(), "ai");
	END IF;
END $$
DELIMITER ;

CALL insert_blogdb_data();
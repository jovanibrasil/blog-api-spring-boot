USE `blog_db`;
INSERT INTO `users` (`id`, `user_name`, `full_name`, `password_hash`) VALUES
	(1, 'Jovani', 'Jovani Brasil', 'kjfdlksjflkdjfkjsdjf2313mkdsakdlm'),
    (2, 'Camila', 'Camila Blauth', 'sldasçdkçsalkdçlsakdçlaskdçlnnnnn'),
    (3, 'Rosane', 'Rosane Maria', 'sdmasdsajdlksajldkjaslkdjlsakjdla');

INSERT INTO `posts` (`id`, `author_id`, `title`, `body`, `date`) VALUES
	(0, 2, 'post0', 'teste', '2015-12-17'),
    (1, 1, 'post1', 'teste', '2015-12-17'),
    (2, 3, 'post2', 'teste', '2015-12-17'),
    (3, 1, 'post3', 'teste', '2015-12-17'),
    (4, 1, 'post4', 'teste', '2015-12-17'),
    (5, 1, 'post5', 'teste', '2015-12-17'),
    (6, 2, 'post6', 'teste', '2015-12-17'),
    (7, 3, 'post7', 'teste', '2015-12-17');

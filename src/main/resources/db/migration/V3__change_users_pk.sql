ALTER TABLE posts ADD COLUMN user_name varchar(12);

UPDATE 
	posts AS p INNER JOIN users as u 
	ON p.user_id=u.user_id
SET p.user_name = u.user_name;

ALTER TABLE posts DROP FOREIGN KEY user_id;
ALTER TABLE posts DROP user_id;

ALTER TABLE users DROP user_id;
ALTER TABLE users MODIFY user_name varchar(12);
ALTER TABLE users ADD PRIMARY KEY (user_name);

ALTER table posts ADD FOREIGN KEY posts(user_name) REFERENCES users(user_name)
 
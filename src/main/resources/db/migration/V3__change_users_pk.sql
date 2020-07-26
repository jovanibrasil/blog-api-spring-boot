ALTER TABLE posts ADD COLUMN user_name varchar(12);

UPDATE posts p 
SET user_name = u.user_name
from users u
WHERE p.user_id=u.user_id;

ALTER TABLE posts DROP constraint fk_user;
ALTER TABLE posts DROP user_id;

ALTER TABLE users DROP user_id;
ALTER TABLE users ALTER COLUMN user_name TYPE varchar(12);
ALTER TABLE users ADD PRIMARY KEY (user_name);
 
ALTER table posts 
	ADD CONSTRAINT user_name 
	FOREIGN KEY (user_name)
	REFERENCES users(user_name);

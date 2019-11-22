ALTER TABLE feedbacks DROP name;
ALTER TABLE feedbacks DROP email;
ALTER TABLE feedbacks ADD COLUMN user_name varchar(40);
ALTER TABLE feedbacks ADD COLUMN email varchar(40);
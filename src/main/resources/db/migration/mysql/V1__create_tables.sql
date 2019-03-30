create table users (
	user_name varchar(30) not null, 
	full_user_name varchar(100), 
	last_update_date datetime not null, 
	profile_type varchar(255) not null, 
	primary key (user_name)
) engine=MyISAM;

create table posts (
	post_id bigint not null auto_increment, 
	body longtext not null, 
	creation_date datetime not null, 
	last_update_date datetime not null, 
	summary varchar(1000) not null, 
	title varchar(255) not null, 
	user_name varchar(30), 
	primary key (post_id),
	constraint fk_user foreign key (user_name) references users (user_name)
) engine=MyISAM;

create table post_tags (
	post_id bigint not null, 
	tag varchar(255) not null,
	constraint fk_post foreign key (post_id) references posts (post_id)
) engine=MyISAM;

create table feedbacks (
	id bigint not null auto_increment, 
	content varchar(255) not null, 
	email varchar(255) not null, 
	feedback_date datetime, 
	name varchar(255) not null, 
	primary key (id)
) engine=MyISAM;

create table subscription (
	id bigint not null auto_increment, 
	email varchar(255) not null, 
	subscription_date datetime, 
	primary key (id)
) engine=MyISAM;


drop table attachment;
drop table lecture;
drop table users;
drop table user_roles;


create table lecture (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    name VARCHAR(255) NOT NULL, 
    subject VARCHAR(255) NOT NULL, 
    body VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id) 
);

create table users (
	username varchar(50) NOT NULL, 
	password varchar(50) NOT NULL, 
	PRIMARY KEY(username)
);

create table attachment (
	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
	filename VARCHAR(255) DEFAULT NULL, 
    content_type VARCHAR(255) DEFAULT NULL, 
    content BLOB DEFAULT NULL, 
	lecture_id INTEGER DEFAULT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(lecture_id) REFERENCES lecture(id)
);

create table user_roles (
	user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),, 
	username varchar(50) NOT NULL, 
	role varchar(50) NOT NULL,
	PRIMARY KEY(user_role_id),
	FOREIGN KEY(username) reference users(username)
);


INSERT INTO users values('keith', 'keithpw');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

insert into users values('john', 'johnpw'); 
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

insert into users values('mary', 'marypw');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');

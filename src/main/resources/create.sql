DROP TABLE user_roles;
DROP TABLE attachment;
/*
DROP TABLE poll;
DROP TABLE pollQ;
*/
DROP TABLE users;
DROP TABLE lecture;


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

create table poll (
    subject_id varchar(50) NOT NULL,
    username varchar(50) NOT NULL,  
    choice char(1) NOT NULL,
    PRIMARY KEY(username, subject_id),
    FOREIGN KEY(username) REFERENCES users(username)
);
/*
create table pollQ (
    subject_id varchar(50) NOT NULL,
    username varchar(50) NOT NULL,
    question varchar(100) NOT NULL,
    choice char(1) NOT NULL,
    PRIMARY KEY(username, subject_id),
    FOREIGN KEY(username) REFERENCES users(username)
);
*/
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
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
    username varchar(50) NOT NULL, 
    role varchar(50) NOT NULL,
    PRIMARY KEY(user_role_id),
    FOREIGN KEY(username) REFERENCES users(username)
);


INSERT INTO users VALUES ('keith', '{noop}keithpw');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

insert into users VALUES ('john', '{noop}johnpw'); 
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

insert into users VALUES ('mary', '{noop}marypw');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');
CREATE USER inside WITH SUPERUSER PASSWORD 'Euxc1Qz44tl85bxt';
CREATE DATABASE insidedb;
GRANT ALL PRIVILEGES ON DATABASE insidedb TO inside; 
\c insidedb
CREATE TABLE IF NOT EXISTS user_table (
     user_id BIGSERIAL PRIMARY KEY,
     name CHARACTER VARYING(30),
     password CHARACTER VARYING(30),
     token TEXT
); 
CREATE TABLE IF NOT EXISTS message_table (
     message_id BIGSERIAL PRIMARY KEY,
     message TEXT,
     user_id BIGSERIAL
);

insert into user_table (name,password,token) values('insidetest','lookup','null');

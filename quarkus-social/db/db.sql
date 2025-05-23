CREATE TABLE quarkus-social;

CREATE TABLE users (
	id bigserial not null primary key,
	name varchar(100) not null,
	age integer not null
);

CREATE TABLE posts (
    id bigserial not null primary key,
    post_text varchar(150) not null,
    date_time timestamp not null,
    user_id bigint not null references user(id)
)

CREATE TABLE followers (
	id bigserial not null primary key,
	user_id bigint not null references users(id),
	follower_id bigint not null references users(id)
);
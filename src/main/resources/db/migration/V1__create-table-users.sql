create table users (
 id UUID NOT NULL,
 email varchar(20) NOT NULL,
 password varchar(10) NOT NULL,
 created_date timestamp not null default now(),
 update_date timestamp,
 primary key(id),
 unique(email)
);
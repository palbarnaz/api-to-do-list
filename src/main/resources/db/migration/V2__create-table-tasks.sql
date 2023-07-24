create table tasks (
 id UUID NOT NULL,
 description text NOT NULL,
 detail text NOT NULL,
 created_date timestamp not null default now(),
 update_date timestamp,
 archived boolean not null default false,
 user_id uuid NOT null references users(id),
 primary key(id)
);
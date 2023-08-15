use wanted;

create table account (
    id char(26) not null primary key,
    email varchar(256) not null,
    account_pw_id char(26) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table account_pw (
    id char(26) not null primary key,
    password char(60) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table post (
    id bigint not null primary key auto_increment,
    title varchar(128) not null,
    content varchar(1500) not null,
    account_id char(26) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

alter table account
add constraint account_fk_account_pw_id
foreign key (account_pw_id) references account_pw (id);

alter table post
add constraint post_fk_account_id
foreign key (account_id) references account (id);
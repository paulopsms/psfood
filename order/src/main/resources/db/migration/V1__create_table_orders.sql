CREATE TABLE orders (
    id bigint (20) not null auto_increment,
    date_time datetime not null,
    status varchar(255) not null,
    primary key (id)
)
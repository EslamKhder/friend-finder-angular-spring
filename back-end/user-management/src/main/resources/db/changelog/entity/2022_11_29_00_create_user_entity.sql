CREATE TABLE user_system (
    id bigint IDENTITY not null,
    active BIT,
    created_time datetime2(7) null,
    modified_time datetime2(7) null,
    name varchar(255),
    login_name varchar(255),
    email varchar(255),
    password varchar(255),
    mobile_phone varchar(255),
    admin bit not null DEFAULT 0,
    language VARCHAR(10) NOT NULL CHECK (language IN('ARABIC','ENGLISH')),
    scope VARCHAR(10) NOT NULL CHECK (scope IN('USER','ORGANIZATION')),
    primary key (id)
)
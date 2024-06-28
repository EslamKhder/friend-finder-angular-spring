CREATE TABLE User_System (
    id bigint IDENTITY NOT null,
    active BIT,
    created_time datetime2(7) null,
    modified_time datetime2(7) null,
    scope VARCHAR(20) NOT NULL CHECK (scope IN('USER','ORGANIZATION')),
    user_id bigint not null UNIQUE,
    primary key (id)
)
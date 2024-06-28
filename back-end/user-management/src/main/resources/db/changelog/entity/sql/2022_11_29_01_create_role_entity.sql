CREATE TABLE role (
    id bigint IDENTITY not null,
    active BIT,
    created_time datetime2(7) null,
    modified_time datetime2(7) null,
    code varchar(255) UNIQUE,
    displayName varchar(255),
    primary key (id)
)
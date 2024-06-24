CREATE TABLE organization (
    id bigint IDENTITY not null,
    active BIT,
    created_time datetime2(7) null,
    modified_time datetime2(7) null,
    reference_id varchar(255),
    organization_name varchar(255),
    password varchar(255),
    primary key (id),
    scope VARCHAR(20) NOT NULL CHECK (scope IN('USER','ORGANIZATION'))
)
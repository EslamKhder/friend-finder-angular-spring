CREATE TABLE Post (
    id bigint IDENTITY not null,
    active BIT,
    created_time datetime2(7) null,
    modified_time datetime2(7) null,
    image varchar(255) null,
    text varchar(255) null,
    likes bigint null,
    user_id bigint not null,
    primary key (id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES User_System(id) ON DELETE CASCADE
)
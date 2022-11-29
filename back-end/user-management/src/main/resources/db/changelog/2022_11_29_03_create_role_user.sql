CREATE TABLE role_user (
    user_id bigint,
    role_id bigint,
    CONSTRAINT user_role PRIMARY KEY (user_id,role_id),
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES user_system(id) ON DELETE CASCADE,
    CONSTRAINT FK_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
)
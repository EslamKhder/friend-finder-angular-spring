CREATE TABLE user_role (
    user_id bigint,
    role_id bigint,
    CONSTRAINT role_user PRIMARY KEY (user_id,role_id),
    CONSTRAINT FK_user_role_id FOREIGN KEY (user_id) REFERENCES user_system(id) ON DELETE CASCADE,
    CONSTRAINT FK_role_user_id FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
)
CREATE TABLE organization_role (
    organization_id bigint,
    role_id bigint,
    CONSTRAINT role_organization PRIMARY KEY (organization_id,role_id),
    CONSTRAINT FK_organization_role_id FOREIGN KEY (organization_id) REFERENCES organization(id) ON DELETE CASCADE,
    CONSTRAINT FK_role_organization_id FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
)
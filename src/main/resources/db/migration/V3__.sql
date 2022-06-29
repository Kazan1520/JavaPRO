CREATE TABLE user_roles
(
    login_id BIGINT NOT NULL,
    role_id  BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (login_id, role_id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (login_id) REFERENCES "user" (id);
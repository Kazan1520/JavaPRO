ALTER TABLE project_topic_taken_projects
DROP
CONSTRAINT fk_protoptakpro_on_project_topic;

ALTER TABLE project_topic_taken_projects
DROP
CONSTRAINT fk_protoptakpro_on_taken_project;

DROP TABLE project_topic_taken_projects CASCADE;
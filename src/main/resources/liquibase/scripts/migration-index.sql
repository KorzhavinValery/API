-- liquibase formatted sql
-- changeset vkor:1
CREATE INDEX IF NOT EXISTS index_name_student ON student(name);
-- changeset vkor:2
CREATE INDEX IF NOT EXISTS index_name_color_faculty ON faculty(name, color);
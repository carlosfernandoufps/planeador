-- data.sql

--  Users

INSERT INTO users (institutional_email, password)
VALUES ('director@mail.com', '$2b$12$oa/AWJIz2tth05.M0wswAOZ9POZgGwe.kMMFIYAuB/zM7/WV7phRK');

INSERT INTO users (institutional_email, password)
VALUES ('teacher1@mail.com', '$2b$12$gaNAeUT/yn/IBG1XDAWCsuLyXmbz9BLpTdyCbFYur43tSU7HqxH96');

INSERT INTO users (institutional_email, password)
VALUES ('teacher2@mail.com', '$2b$12$gaNAeUT/yn/IBG1XDAWCsuLyXmbz9BLpTdyCbFYur43tSU7HqxH96');

INSERT INTO users (institutional_email, password)
VALUES ('teacher3@mail.com', '$2b$12$gaNAeUT/yn/IBG1XDAWCsuLyXmbz9BLpTdyCbFYur43tSU7HqxH96');

--  Profiles

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (1, 'Pilar Rodriguez', 'pr@mail.com', '444-555-6666', '11500', 'DIRECTOR');

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (2, 'Jairo Fuentes', 'ct@mail.com', '111-222-3333', '11511', 'TEACHER');

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (3, 'Carlos René', 'mv@mail.com', '111-222-4444', '11522', 'TEACHER');

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (4, 'Pilar Rojas', 'pr@mail.com', '111-222-5555', '11533', 'TEACHER');

--  Semester

INSERT INTO semester (name, start_date, end_date)
VALUES ('2025-I', '2025-01-15', '2025-07-01');

INSERT INTO semester (name, start_date, end_date)
VALUES ('2024-II', '2024-07-01', '2024-12-28');

INSERT INTO semester (name, start_date, end_date)
VALUES ('2024-I', '2024-01-15', '2024-06-28');

--  Course

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('Fundamentos de Programación', 'Lo básico de programación', '1150000', 'WORD', '');

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('POO', 'Programación orientada a Objetos', '1151111', 'PDF', '');

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('API', 'Análisis de proyectos informáticos', '1152222', 'PDF', '');

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('Programación Web', 'Aplicaciones Web', '1153333', 'PDF', '');

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('Bases de datos', 'Bases de datos relacionales', '1154444', 'PDF', '');

--  Assignments

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 2, 1, 'A');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 2, 2, 'A');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 4, 2, 'B');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 4, 3, 'A');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 3, 4, 'A');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 3, 4, 'B');

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name)
VALUES (1, 3, 5, 'A');

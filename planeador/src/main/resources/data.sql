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


-- Insertar versiones de planner
INSERT INTO planner_version (name, is_default_version) VALUES
('Versión 2024', false),
('Versión 2025', true);


INSERT INTO column_description_map (planner_version_id, column_order, column_name, column_description) VALUES
(1, 0, 'Objetivos', 'Objetivos de la actividad planeada'),
(1, 1, 'Temas', 'Temas a presentar'),
(1, 2, 'Actividades', 'Las actividades realizadas en el tiempo de aula'),
(1, 3, 'Instrumentos de evaluación', 'Cómo se evalúan los temas presentados'),
(1, 4, 'Tipo de evidencia de aprendizaje', 'Cómo se evidencia lo que el estudiante aprendió'),
(1, 5, 'Corte', 'El periodo o corte en que se hace la actividad');

INSERT INTO column_description_map (planner_version_id, column_order, column_name, column_description) VALUES
(2, 0, 'RA  Curso', 'Lo que se espera que el estudiante aprenda'),
(2, 1, 'Criterio desempeño', '(evidencia , la razon del aprendizaje , que debe demostrar el estudiante)'),
(2, 2, 'Temas', ''),
(2, 3, 'Actividades pedagógicas', 'Actividad que el profesor va a desarrollar'),
(2, 4, 'Instrumento de evaluación', 'Estrategia de evaluacion (como lo evalua el profesor)'),
(2, 5, 'Tipo de Evidencia de Aprendizaje', 'Entregas por parte de los estudiantes'),
(2, 6, 'Peso de la estrategia de evaluacion', 'Valor que le asigna a la tarea o actividad'),
(2, 7, 'Periodo de evlauacion', 'Primer Previo -  Segundo Previo - Tercer Previo - Examen final');

-- Insertar planners
INSERT INTO planner (planner_version_id) VALUES
(1),(1),(1),
(2),(2),(2),(2),(1),(2),(2);

INSERT INTO planner_row (planner_id) VALUES
(1),(1),(2),(4),(4);

-- Celdas para las filas del planner básico
INSERT INTO planner_row_cell (planner_row_id, cell_value, cell_order) VALUES
(1, 'Crear una página web estática ', 0),
(1, 'HTML, CSS ', 1),
(1, 'Demostración de creación de una página web en clase', 2),
(1, 'Talleres', 3),
(1, 'Lorem Ipsum', 4),
(1, 'Lorem Ipsum', 5);

INSERT INTO planner_row_cell (planner_row_id, cell_value, cell_order) VALUES
(2, 'Crear una página web dinámica ', 0),
(2, 'JavaScript ', 1),
(2, 'Neque porro', 2),
(2, 'Neque porro', 3),
(2, 'Neque porro', 4),
(2, 'Neque porro', 5);

INSERT INTO planner_row_cell (planner_row_id, cell_value, cell_order) VALUES
(3, 'quisquam est qui', 0),
(3, 'quisquam est qui', 1),
(3, 'quisquam est qui', 2),
(3, 'quisquam est qui', 3),
(3, 'quisquam est qui', 4),
(3, 'quisquam est qui', 5);

-- Celdas para las filas del planner avanzado
INSERT INTO planner_row_cell (planner_row_id, cell_value, cell_order) VALUES
(4, 'dolorem ipsum', 0),
(4, 'dolorem ipsum', 1),
(4, 'dolorem ipsum', 2),
(4, 'dolorem ipsum', 3),
(4, 'dolorem ipsum', 4),
(4, 'dolorem ipsum', 5),
(4, 'dolorem ipsum', 6),
(4, 'dolorem ipsum', 7);

INSERT INTO planner_row_cell (planner_row_id, cell_value, cell_order) VALUES
(5, 'quia dolor sit amet', 0),
(5, 'quia dolor sit amet', 1),
(5, 'quia dolor sit amet', 2),
(5, 'quia dolor sit amet', 3),
(5, 'quia dolor sit amet', 4),
(5, 'quia dolor sit amet', 5),
(5, 'quia dolor sit amet', 6),
(5, 'quia dolor sit amet', 7);

--  Assignments

-- 2024

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (2, 2, 4, 'A', 1);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (2, 2, 4, 'B', 2);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (2, 2, 5, 'A', 3);

-- Nuevos

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 2, 1, 'A', 4);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 2, 2, 'A', 5);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 4, 2, 'B', 6);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 4, 3, 'A', 7);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 3, 4, 'A', 8);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 3, 4, 'B', 9);

INSERT INTO assignment (semester_id, teacher_id, course_id, group_name, planner_id)
VALUES (1, 3, 5, 'A', 10);
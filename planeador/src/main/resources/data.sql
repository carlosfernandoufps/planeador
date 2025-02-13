-- data.sql

-- Primero, insertamos un par de usuarios en la tabla user
INSERT INTO users (institutional_email, password)
VALUES ('teacher@mail.com', '$2b$12$gaNAeUT/yn/IBG1XDAWCsuLyXmbz9BLpTdyCbFYur43tSU7HqxH96');

INSERT INTO users (institutional_email, password)
VALUES ('director@mail.com', '$2b$12$oa/AWJIz2tth05.M0wswAOZ9POZgGwe.kMMFIYAuB/zM7/WV7phRK');


-- Luego, insertamos perfiles en la tabla profile
INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (1, 'Carlos Teacher', 'ct@mail.com', '111-222-3333', 'TCH123', 'TEACHER');

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (2, 'Juana Director', 'jd@mail.com', '444-555-6666', 'DIR456', 'DIRECTOR');
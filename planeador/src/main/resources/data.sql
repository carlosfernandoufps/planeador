-- data.sql

--  Users

INSERT INTO users (institutional_email, password)
VALUES ('teacher@mail.com', '$2b$12$gaNAeUT/yn/IBG1XDAWCsuLyXmbz9BLpTdyCbFYur43tSU7HqxH96');

INSERT INTO users (institutional_email, password)
VALUES ('director@mail.com', '$2b$12$oa/AWJIz2tth05.M0wswAOZ9POZgGwe.kMMFIYAuB/zM7/WV7phRK');

--  Profiles

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (1, 'Carlos Teacher', 'ct@mail.com', '111-222-3333', 'TCH123', 'TEACHER');

INSERT INTO profile (id_user, name, personal_email, phone, code, profile_type)
VALUES (2, 'Juana Director', 'jd@mail.com', '444-555-6666', 'DIR456', 'DIRECTOR');

--  Semester

INSERT INTO semester (name, start_date, end_date)
VALUES ('2025-I', '2025-01-15', '2025-07-01');

INSERT INTO semester (name, start_date, end_date)
VALUES ('2024-II', '2024-07-01', '2024-12-28');

INSERT INTO semester (name, start_date, end_date)
VALUES ('2024-I', '2024-01-15', '2024-06-28');

--  Course

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('holamundo', 'Lo básico de programación', '1150000', 'WORD', 'UEsDBBQAAAAIAMaFGVdQr3xKCAAAAAgAAAANAAAAaG9sYW11bmRvLmRvY3htkMFqwzAQRO/5iieXQKBrDBW5tJQSSg8JvfTgWIm1qCxZ3l1HJP++N8vAzHszD2t3WjuiHtZZbkQbAjRgVFprlTGyqjchxF4UylzPcJ28ZMJop+oQovdAXVtE9iWRbTKTLDab91eecZs4Q+5W2X4RQfkKdoCUZ+KgKD1rC5H7GfwPI2Ue5/hz2H7Mk7sE5H5w5A1E5qB9QIo9Yj4QZvOcT5t5r6Qv0x3l7Q/2FJ/7u9QSwECFAAUAAAACADGhRlXUK98SggAAAAIAAAADQAAAAAAAAAAAAAAIAAAAGhvbGFtdW5kby5kb2N4UEsFBgAAAAABAAEAPgAAAPsBAAAAAA==');

INSERT INTO course (name, description, code, doc_type, microcurriculum)
VALUES ('holamundo', 'Programación orientada a Objetos', '1159999', 'PDF', 'JVBERi0xLjMKJcfsj6IKNSAwIG9iago8PC9MZW5ndGggNiAwIFIvRmlsdGVyIC9GbGF0ZURlY29kZT4+CnN0cmVhbQp4nCtUMNAzVDAAQityCxRAQq6hfXxOUWZeXmJJanFyXk5iUapLUn5OZpGCCVDPBCqWkZqXr1CoYGRoYqJgopCcr2AAVDRUMDZQMDYzVDAxNlEwNDFUMAWqMQUqMgQpM4GJmSqYQlVCFQEAnG0QigplbmRzdHJlYW0KZW5kb2JqCjYgMCBvYmoKMTEKZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9QYWdlL01lZGlhQm94IFswIDAgMzAwIDE0NF0KL1Jlc291cmNlczw8L1Byb2NTZXQgWy9QREYgL1RleHQgL0ltYWdlQiAvSW1hZ2VDIC9JbWFnZUldCj4+Ci9Db250ZW50cyA1IDAgUgo+PgplbmRvYmoKMyAwIG9iago8PC9QYWdlcyA0IDAgUgovVHlwZS9DYXRhbG9nCj4+CmVuZG9iagp0ZXN0ZGF0YQolJUVPRgo=');


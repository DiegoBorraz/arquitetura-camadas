CREATE SEQUENCE professional_id_seq;
CREATE TABLE IF NOT EXISTS public.professional(
    id BIGINT PRIMARY KEY DEFAULT nextval('professional_id_seq'),
    name VARCHAR(100) NOT NULL,
    role ENUM VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_date DATETIME  NOT NULL
);

CREATE SEQUENCE contact_id_seq;
CREATE TABLE IF NOT EXISTS public.contact (
    id BIGINT PRIMARY KEY DEFAULT nextval('contact_id_seq'),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    professional_id BIGINT NOT NULL,
    created_date DATETIME NOT NULL,
    FOREIGN KEY (professional_id) REFERENCES professional(id)
);

CREATE TABLE IF NOT EXISTS dog
(
  id         UUID         PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  birth_date DATE,
  height     INT          NOT NULL,
  weight     INT          NOT NULL
);

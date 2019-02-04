CREATE TABLE IF NOT EXISTS dog
(
  id         VARCHAR(300) PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  birth_date DATE,
  height     INT          NOT NULL,
  weight     INT          NOT NULL
);
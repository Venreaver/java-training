CREATE TABLE IF NOT EXISTS dog
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    birth_date DATE,
    height     INT          NOT NULL,
    weight     INT          NOT NULL
);

INSERT INTO dog (id, name, birth_date, height, weight)
VALUES ('aabbccdd-eeff-0011-2233-445566778895', 'First', '2016-1-10', 30, 6);
INSERT INTO dog (id, name, birth_date, height, weight)
VALUES ('aabbccdd-eeff-0011-2233-445566778896', 'Second', '2015-2-22', 50, 12);
INSERT INTO dog (id, name, birth_date, height, weight)
VALUES ('aabbccdd-eeff-0011-2233-445566778897', 'Third', '2017-3-15', 65, 20);
INSERT INTO dog (id, name, birth_date, height, weight)
VALUES ('aabbccdd-eeff-0011-2233-445566778898', 'Fourth', '2018-4-6', 45, 10);
INSERT INTO dog (id, name, birth_date, height, weight)
VALUES ('aabbccdd-eeff-0011-2233-445566778899', 'Fifth', '2014-5-17', 64, 17);
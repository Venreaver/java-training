CREATE TABLE human
(
    id     INT PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    dog_id UUID,
    FOREIGN KEY (dog_id) REFERENCES dog (id)
);

INSERT INTO human (id, name, dog_id)
VALUES (1, 'FirstOwner', 'aabbccdd-eeff-0011-2233-445566778895');
INSERT INTO human (id, name, dog_id)
VALUES (2, 'SecondOwner', 'aabbccdd-eeff-0011-2233-445566778895');
INSERT INTO human (id, name, dog_id)
VALUES (3, 'ThirdOwner', 'aabbccdd-eeff-0011-2233-445566778895');
INSERT INTO human (id, name, dog_id)
VALUES (4, 'FourthOwner', 'aabbccdd-eeff-0011-2233-445566778898');
INSERT INTO human (id, name, dog_id)
VALUES (5, 'FifthOwner', 'aabbccdd-eeff-0011-2233-445566778899');
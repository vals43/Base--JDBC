CREATE TYPE position_enum AS ENUM('GK','DEF','MIDF','STR');
CREATE TYPE continent_enum AS ENUM('[AFRICA','EUROPA','ASIA','AMERICA');
CREATE TABLE Team (
    id int PRIMARY KEY,
    name VARCHAR NOT NULL ,
    continent continent_enum
);
CREATE TABLE Player (
    id int PRIMARY KEY ,
    name VARCHAR NOT NULL ,
    age int NOT NULL ,
    position position_enum NOT NULL,
    id_team INT,
    CONSTRAINT fk_player_team FOREIGN KEY (id_team) REFERENCES Team(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);


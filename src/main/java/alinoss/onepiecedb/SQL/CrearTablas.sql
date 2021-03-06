CREATE TABLE BARCO (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
    CODIGO CHAR(2),
    NOMBRE VARCHAR(20) NOT NULL,
    CONSTRAINT ID_PROVINCIA_PK PRIMARY KEY (ID)
);

CREATE TABLE PIRATA (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY, -- Id autonumérico
    NOMBRE VARCHAR(20) NOT NULL,
    BANDA VARCHAR(30),
    ROL VARCHAR(30),
    BARCO INTEGER,
    EDAD INT,
    RECOMPENSA BIGINT,
    FRUTA BOOLEAN,
    NOMBRE_FRUTA VARCHAR(30),
    FOTO VARCHAR(60),
    CONSTRAINT ID_PIRATA_PK PRIMARY KEY (ID),
    CONSTRAINT BARC_PIRATA_FK FOREIGN KEY (BARCO) REFERENCES BARCO (ID)
);
CREATE TABLE SHIPS (
  id bigint NOT NULL,
  name nvarchar(255) NOT NULL
);

ALTER TABLE SHIPS
    ADD CONSTRAINT ships_pkey PRIMARY KEY (id);

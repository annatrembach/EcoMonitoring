CREATE TABLE IF NOT EXISTS "Objects"(
	Id SERIAL PRIMARY KEY,
	Name text NOT NULL,
	Location text NOT NULL
);

CREATE TABLE IF NOT EXISTS "Substances"(
	Id SERIAL PRIMARY KEY,
	Name text NOT NULL,
	Type int NOT NULL
);

CREATE TABLE IF NOT EXISTS "SubstanceHistory"(
	Id SERIAL PRIMARY KEY,
	Year date NOT NULL,
	Value double precision NOT NULL,
	ObjectId int NOT NULL,
	SubstanceId int NOT NULL
);

ALTER TABLE "SubstanceHistory"
ADD CONSTRAINT fk_objectId
FOREIGN KEY (ObjectId) REFERENCES "Objects" (Id);

ALTER TABLE "SubstanceHistory"
ADD CONSTRAINT fk_substanceId
FOREIGN KEY (SubstanceId) REFERENCES "Substances" (Id);

--DROP TABLE IF EXISTS "Objects";
--DROP TABLE IF EXISTS "Substances";
--DROP TABLE IF EXISTS "SubstanceHistory";
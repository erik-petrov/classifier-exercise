CREATE TABLE IF NOT EXISTS document(
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS classification(
    id SERIAL PRIMARY KEY,
    label varchar(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS score(
    id SERIAL PRIMARY KEY,
    document_id INTEGER NOT NULL REFERENCES document(id),
    classification_id INTEGER NOT NULL REFERENCES classification(id),
    score DOUBLE PRECISION NOT NULL
);
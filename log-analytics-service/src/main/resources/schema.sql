CREATE TABLE IF NOT EXISTS logs (
    id SERIAL PRIMARY KEY,
    source TEXT NOT NULL,
    level TEXT NOT NULL,
    message TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

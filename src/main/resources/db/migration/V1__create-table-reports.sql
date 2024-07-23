CREATE TYPE report_status AS ENUM ('OPEN', 'WORKING', 'SOLVED');

CREATE TABLE reports (
    id TEXT PRIMARY KEY NOT NULL,
    title VARCHAR(35) NOT NULL,
    author_name VARCHAR(35) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status report_status NOT NULL DEFAULT 'OPEN'
);

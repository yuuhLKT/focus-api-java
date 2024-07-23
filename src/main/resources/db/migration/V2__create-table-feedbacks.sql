CREATE TABLE feedbacks (
    id TEXT PRIMARY KEY NOT NULL,
    title VARCHAR(35) NOT NULL,
    author_name VARCHAR(35) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(8) NOT NULL DEFAULT 'FEEDBACK'
);

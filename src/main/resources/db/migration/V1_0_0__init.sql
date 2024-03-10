CREATE TABLE IF NOT EXISTS article
(
    article_id uuid PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    title  VARCHAR(100) NOT NULL,
    author VARCHAR NOT NULL,
    content TEXT NOT NULL,
    publishing_date TIMESTAMP NOT NULL
)
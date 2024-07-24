CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    title VARCHAR(35) NOT NULL,
    author_name VARCHAR(35) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    post_id TEXT NOT NULL,
    post_type VARCHAR(10) NOT NULL,
    CONSTRAINT post_type_check CHECK (post_type IN ('report', 'feedback'))
);

CREATE OR REPLACE FUNCTION enforce_comment_post_fk()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.post_type = 'report' THEN
        IF NOT EXISTS (SELECT 1 FROM reports WHERE id = NEW.post_id) THEN
            RAISE EXCEPTION 'Invalid post_id for reports';
END IF;
    ELSIF NEW.post_type = 'feedback' THEN
        IF NOT EXISTS (SELECT 1 FROM feedbacks WHERE id = NEW.post_id) THEN
            RAISE EXCEPTION 'Invalid post_id for feedbacks';
END IF;
ELSE
        RAISE EXCEPTION 'Invalid post_type';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER enforce_comment_post_fk_trigger
    BEFORE INSERT OR UPDATE ON comments
    FOR EACH ROW EXECUTE FUNCTION enforce_comment_post_fk();
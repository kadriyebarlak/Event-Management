CREATE TABLE IF NOT EXISTS performer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    role VARCHAR(255),
    biography TEXT
);

CREATE TABLE IF NOT EXISTS event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_type VARCHAR(50),
    name VARCHAR(255),
    summary TEXT,
    description TEXT,
    start_date_time DATETIME,
    end_date_time DATETIME,
    created_by_user INT,
    created_at DATETIME,
    updated_by_user INT,
    updated_at DATETIME,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS event_performer (
    event_id BIGINT NOT NULL,
    performer_id BIGINT NOT NULL,
    PRIMARY KEY (event_id, performer_id),
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (performer_id) REFERENCES performer(id)
);

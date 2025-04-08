-- 创建TaskList表（带外键约束）
DROP TABLE IF EXISTS `TaskList`;
CREATE TABLE TaskList (
    filename VARCHAR(255) NOT NULL,
    task_type VARCHAR(50),
    creator VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50),
    completed_at DATETIME,
    FOREIGN KEY (filename) REFERENCES VideoState(filename)
)DEFAULT CHARSET=utf8;
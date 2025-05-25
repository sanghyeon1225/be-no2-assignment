use kakaotech;

/* 필수 과제 테이블 구성 */
CREATE TABLE schedule (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          post VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          create_time DATETIME NOT NULL,
                          update_time DATETIME NOT NULL
)

/* 도전 과제 테이블 구성 */
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      create_time DATETIME NOT NULL,
                      update_time DATETIME NOT NULL
)

CREATE TABLE schedule2 (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           password VARCHAR(255) NOT NULL,
                           post VARCHAR(255) NOT NULL,
                           user_id BIGINT NOT NULL,
                           create_time DATETIME NOT NULL,
                           update_time DATETIME NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES user (id)
)

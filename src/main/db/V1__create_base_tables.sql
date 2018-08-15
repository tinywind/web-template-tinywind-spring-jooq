CREATE TABLE file_entity (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,

  size BIGINT NOT NULL COMMENT '파일 사이즈',
  original_name VARCHAR(256) NOT NULL COMMENT '파일 원본 이름',
  path VARCHAR(1024) NOT NULL COMMENT '파일 저장 경로',

  created_at TIMESTAMP NOT NULL DEFAULT now() COMMENT '레코드 생성 시각'
) COMMENT '파일' ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE user_entity (
  id VARCHAR(16) PRIMARY KEY COMMENT '휴대폰번호 (-) 제외',

  grade ENUM('NORMAL', 'MANAGER') NOT NULL DEFAULT 'NORMAL' COMMENT '일반사용자/관리자',
  password VARCHAR(128) NOT NULL COMMENT '로그인 패스워드',
  name VARCHAR(32) NOT NULL COMMENT '이름',
  department VARCHAR(32) NOT NULL COMMENT '부서명',

  created_at TIMESTAMP NOT NULL DEFAULT now() COMMENT '레코드 생성 시각'
) COMMENT '사용자' ENGINE = InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


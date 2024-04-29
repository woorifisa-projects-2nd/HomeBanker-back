INSERT INTO USERS (name, birth, phone, address, login_id, login_pw, role, identification_num, join_date) VALUES
    ('조유정', '1996-12-22', '01063822741', '서울', 'id', '$2a$10$lBV.lzUehu2BqtPNzccAdetTX.gFSGwwMstNNZ/qt4lyk68cwVmhC', 'ROLE_CUSTOMER',
     '961222-2222222', '2024-04-16');

INSERT INTO USERS (name, birth, phone, address, login_id, login_pw, role, identification_num, join_date) VALUES
    ('은행원', '1998-04-20', '01086249375', '서울', 'admin', '$2a$10$lBV.lzUehu2BqtPNzccAdetTX.gFSGwwMstNNZ/qt4lyk68cwVmhC', 'ROLE_ADMIN',
     '980420-2222222', '2024-04-16');

INSERT INTO board (content, reply_YN, created_at, customer_id) VALUES ('안녕', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id) VALUES ('빠밤', 'N', '2023-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id) VALUES ('음식', 'N', '2024-11-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);
INSERT INTO board (content, reply_YN, created_at, customer_id ) VALUES ('내용', 'N', '2024-12-12 09:09:10', 1);

INSERT INTO product_code (product_code, type_name) VALUES ('Y001', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y002', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y003', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y004', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y005', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y006', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y007', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y008', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y009', '예금');
INSERT INTO product_code (product_code, type_name) VALUES ('Y010', '예금');

INSERT INTO product_code (product_code, type_name) VALUES ('C001', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C002', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C003', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C004', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C005', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C006', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C007', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C008', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C009', '카드');
INSERT INTO product_code (product_code, type_name) VALUES ('C010', '카드');

INSERT INTO product_code (product_code, type_name) VALUES ('D001', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D002', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D003', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D004', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D005', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D006', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D007', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D008', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D009', '대출');
INSERT INTO product_code (product_code, type_name) VALUES ('D010', '대출');

-- 예적금 상품
INSERT INTO product (product_name, product_description, is_shown, product_code, product_interest, max_month) VALUES
('청년희망적금', '19세에서 34세의 청년 중 총급여 3600만 원 이하의 청년이 가입할 수 있다.', true, 'Y001', 2.5, 24),
('스마트저축예금', '높은 이자율을 제공하는 저축예금 상품', true, 'Y002', 1.8, 12),
('우대이자적금', '특정 조건 충족 시 추가 이자 제공', true, 'Y003', 2.2, 36),
('행복플러스저축예금', '장기 저축을 위한 상품', true, 'Y004', 2.0, 60),
('신규창업자금적금', '신규 창업자를 위한 특별적금 상품', true, 'Y005', 2.3, 12),
('미래설계저축예금', '미래를 위한 저축 계획 상품', true, 'Y006', 1.9, 36),
('골드라인적금', '고액 저축자를 위한 고이자 적금 상품', true, 'Y007', 2.5, 24),
('주니어스마트저축예금', '어린이와 청소년을 위한 저축예금 상품', true, 'Y008', 2.0, 12),
('행복나눔적금', '사회공헌 활동 연계 적금 상품', true, 'Y009', 2.1, 24),
('여행목돈마련적금', '여행자금 마련을 위한 적금 상품', true, 'Y010', 2.2, 18);

-- 대출 상품
INSERT INTO product (product_name, product_description, is_shown, product_code, product_interest, max_month) VALUES
('신용대출', '신용등급에 따른 맞춤형 대출 상품', true, 'D001', 3.5, 60),
('주택담보대출', '주택을 담보로 제공하여 받을 수 있는 대출', true, 'D002', 2.8, 240),
('자동차대출', '자동차 구매를 위한 대출 상품', true, 'D003', 4.0, 60),
('학자금대출', '학비 지원을 위한 대출 상품', true, 'D004', 2.2, 84),
('사업자금대출', '사업 운영 자금을 지원하는 대출 상품', true, 'D005', 3.8, 120),
('햇살론', '저신용자나 저소득층을 위한 정부지원 대출 상품', true, 'D006', 2.0, 36),
('전세자금대출', '전세 보증금을 마련하기 위한 대출 상품', true, 'D007', 2.5, 120),
('신혼부부전용대출', '신혼부부를 위한 맞춤형 대출 상품', true, 'D008', 2.3, 240),
('장기주택담보대출', '장기간 주택담보 대출 상품', true, 'D009', 2.9, 360),
('개인사업자대출', '개인 사업자를 위한 운영 자금 대출 상품', true, 'D010', 4.2, 60);

-- 카드 상품
INSERT INTO product (product_name, product_description, is_shown, product_code, product_interest, max_month) VALUES
('프리미엄 포인트 카드', '높은 포인트 적립률을 제공하는 프리미엄 카드', true, 'C001', NULL, NULL),
('여행 마일리지 카드', '항공 마일리지 적립에 최적화된 카드', true, 'C002', NULL, NULL),
('영화 할인 카드', '영화관 이용 시 할인 혜택을 제공하는 카드', true, 'C003', NULL, NULL),
('쇼핑 리워드 카드', '다양한 쇼핑몰에서의 할인 혜택을 제공하는 카드', true, 'C004', NULL, NULL),
('주유 할인 카드', '주유 시 할인 혜택을 제공하는 카드', true, 'C005', NULL, NULL),
('다이닝 리워드 카드', '음식점에서의 할인 및 리워드 제공 카드', true, 'C006', NULL, NULL),
('교통 리워드 카드', '대중교통 이용 시 혜택을 제공하는 카드', true, 'C007', NULL, NULL),
('건강 관리 카드', '건강 관련 지출에 대한 리워드를 제공하는 카드', true, 'C008', NULL, NULL),
('교육 리워드 카드', '교육 관련 지출 시 혜택을 제공하는 카드', true, 'C009', NULL, NULL),
('환경 친화 카드', '환경 보호 활동에 기여하는 소비에 대해 리워드를 제공하는 카드', true, 'C010', NULL, NULL);
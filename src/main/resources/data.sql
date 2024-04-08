INSERT INTO `customer` (`customer_name`, `customer_birth`, `customer_phone`,
                        `customer_address`, `customer_login_id`, `customer_login_pw`,
                        `customer_role`,
                        `customer_identification_num`, `customer_recent_login`, `join_date`)
VALUES ('홍길동', '1990-01-01', '01012345678',
        '서울시 강남구 역삼동', 'gildong', 'securePassword', 'customer',
        '1234567890123456', '2024-04-01 12:30:00', '2024-01-01'),

       ('김철수', '1985-05-05', '01098765432',
        '부산시 해운대구 우동', 'chulsu', 'anotherSecure', 'customer',
        '6543210987654321', '2024-04-01 13:00:00', '2024-02-15'),

       ('이영희', '1992-08-25', '01055556666',
        '대구시 수성구 범어동', 'younghee', 'password', 'customer',
        '9876543210987654', '2024-04-01 14:15:00', '2024-03-20');
# Gifticon Trader

## 기술 스택 요약

- Language: Java 17
- Framework: Spring Boot 3, Spring Security, JPA, Thymeleaf
- Build Tool: Gradle
- Test: JUnit5, Mockito
- Database: PostgreSQL, H2 (test)
- Email: JavaMailSender
- Redis Session & Caching
- TODOs
    - RabbitMQ 기반 메시지 큐잉

## 구현기능
- 인증
  - [x] 회원가입
  - [x] 로그인
  - [x] 로그아웃
- 기프티콘
  - [ ] 기프티콘 관리
  - [ ] 기프티콘 거래
- 정산
  - [ ] 판매자 정산

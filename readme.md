# Gifticon Trader

## 프로젝트 소개

기프티콘 거래 플랫폼입니다. 기프티콘을 사고 팔 수 있는 기능을 제공합니다.

## 프로젝트 목적

기프티콘 거래 플랫폼을 구현하여 Spring Boot와 JPA 활용한 웹 애플리케이션 개발 경험을 쌓고,

DDD(도메인 주도 설계)와 TDD를 적용하여, 유지보수성과 확장성을 고려한 아키텍처를 설계합니다.

## 기술 스택 요약

- Language: Java 17
- Framework: Spring Boot 3, Spring Security, JPA, Thymeleaf
- Build Tool: Gradle
- Test: JUnit5, Mockito
- Database: PostgreSQL, H2 (test)
- Email: JavaMailSender
- Redis Session & Caching
- RabbitMQ 기반 메시지 큐잉
- gitflow CI (self-hosted)
  - [Docker Hub](https://hub.docker.com/r/hmk6264/gifticon-trader)

## Aggregate Root

- User
- Gifticon
- Notification
- Transaction 
- Settlement

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

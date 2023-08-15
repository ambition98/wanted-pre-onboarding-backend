# wanted-pre-onboarding-backend
### 이름: 이용재
### ambition65@naver.com
---
## 실행방법
```
$ wget https://github.com/ambition98/wanted-pre-onboarding-backend/releases/download/wanted/YJ_Lee-wanted.tar.gz
$ tar xvfz YJ_Lee-wanted.tar.gz
$ cd springboot-wanted
$ docker-compose up --build -d
```
## 구성환경
![화면 캡처 2023-08-16 013546](https://github.com/ambition98/wanted-pre-onboarding-backend/assets/37949600/50d8103c-fbbd-41bd-b9c7-7ea3fc6905af)
## ERD
![화면 캡처 2023-08-16 013410](https://github.com/ambition98/wanted-pre-onboarding-backend/assets/37949600/3b04dbe3-37a2-46de-9352-df697dff9e10)
## 시연영상
https://www.youtube.com/watch?v=XHYsATPGbOs
## 구현 방법 및 설명
* Springboot 에서 제공하는 MVC 패턴을 이용하여 Rest API 구현
* 로그인 세션 유지는 요구에 따라 JWT를 이용
* 로그인에 성공하면 HttpOnly, Secure 쿠키를 담아서 응답 (Secure 옵션은 배포단계에서 제외)
* JWT에는 사용자의 PK가 담아져 있음, PK를 유추할 수 없도록 ULID값 사용
* 사용자는 매 요청시마다 쿠키를 전송하며 SpringSecurity의 Filter는 쿠키를 확인하여 올바른 JWT이면 WantedAuthentication 발급
  유효하지 않은 JWT 이면 AnonymousAuthentication 을 발급하며, Role 이 필요한 URL에는 접근 불가

## API 명세서

# LogSend
<p>카프카를 이용하여 로그 메시지를 전송하는 애플리케이션</p>
<br/>

### ⚙️ 기술 스택
> - Java 17
> - JDK 17
> - Spring Boot 3.4.0

<br/>


## 1. 개발 과정
- 개발 과정은 티스토리 블로그에 정리하였으니 참고 바랍니다.
- [카프카를 이용한 로그 전송 애플리케이션 구현: Spring Boot, Kafka](https://ynslee627.tistory.com/313)

<br/>


## 2. 아키텍처
![image](https://github.com/user-attachments/assets/14c19883-1e8d-4031-854f-cd97089f8e35)


## 3. 실행 방법
1. AWS EC2 인스턴스에 카프카 설치 및 실행 후 `application.yml`에서 <public-ip> 설정하기
2. LogSend 애플리케이션 실행 (포트 8080)
3. API 요청:
   - 로그 메시지 전송
   - URL: `localhost:8080/`
   - Method: GET
   - Response: `My Web`
  

# LogtoS3
<p>카프카로부터 받은 로그 메시지를 파일로 만들어 AWS S3 버킷에 업로드 하는 애플리케이션</p>
<br/>

### ⚙️ 기술 스택
> - Java 17
> - JDK 17
> - Spring Boot 3.4.0
> - AWS S3

<br/>


## 1. 개발 과정
- 개발 과정은 티스토리 블로그에 정리하였으니 참고 바랍니다.
- [카프카로부터 메시지를 받아서 S3에 업로드하는 애플리케이션 구현: Spring Boot, Kafka, AWS S3](https://ynslee627.tistory.com/314)

<br/>


## 2. 아키텍처
![image](https://github.com/user-attachments/assets/7cd7b4b5-8f2f-448d-bfcc-41ac2e47ae72)


## 3. 실행 방법
1. AWS EC2 인스턴스에 카프카 설치 및 실행 후 `application.yml`에서 <public-ip> 설정하기
2. LogSend 애플리케이션 실행 (포트 8080)
3. LogtoS3 애플리케이션 실행 (포트 8081)
4. API 요청:
   - 로그 메시지 전송
   - URL: `localhost:8080/`
   - Method: GET
   - Response: `My Web`
     - LogtoS3 애플리케이션에서 해당 로그 메시지를 수신하여 파일로 출력
     - 로그 파일을 S3 버킷에 업로드
    ![image](https://github.com/user-attachments/assets/9abd65e0-dc29-407d-87da-869bcc7c3dbc)


### 환경변수 설정
- `.env` 파일을 만들어 AWS access key 및 secret key를 설정해주어야 합니다.
- `.env` 파일은 `.gitignore` 파일에 추가하여 깃허브에 업로드되지 않도록 해야 합니다. (외부 노출 절대 금지!)


# 검색 API


블로그 검색 API 를 이용하여 블로그 글을 검색하는 API 서버입니다.

Daum 블로그 글을 먼저 검색하고 오류 발생시 Naver 블로그를 검색합니다.

인기 검색어 상위 10개를 빈도수와 함께 조회할 수 있습니다.

---

- Java version: 17
- spring boot version: 3.0.4

--- 

프로젝트 루트에 있는 jar 파일을 이용해 서버를 실행시킬 수 있습니다.

- java -jar search.blog-0.0.1-SNAPSHOT.jar


swagger: http://localhost:8080/swagger-ui/index.html
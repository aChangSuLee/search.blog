# 검색 API


블로그 검색 API 를 이용하여 블로그 글을 검색하는 API 서버입니다.

Daum 블로그 글을 먼저 검색하고 오류 발생시 Naver 블로그를 검색합니다.
(source 파라미터로 검색 위치를 변경할 수 있습니다.)

인기 검색어 상위 10개를 빈도수와 함께 조회할 수 있습니다.


## 블로그 검색
```
GET /search HTTP/1.1
```

### Request
#### Parameter

| Name    | Type    | Description                                                | Required |
|---------|---------|------------------------------------------------------------|----------|
| keyword | String  | 검색을 원하는 질의어                                                | O        |
| size    | Integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본값 10                        | X        |
| page    | Integer | 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                              | X        |
| sort    | String  | 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy | X        |
| source  | String  | Daum or Naver 블로그 선택값, daum(다음) 또는 naver(네이버), 기본값 daum    | X        |

- Response 는 소스별로 상이하나, 페이지 기반으로 블로그 검색 결과를 응답.

## 인기 키워드 조회
```
GET /popular HTTP/1.1
```

- 검색된 키워드를 빈도수로 정렬하여 상위 10개의 키워드를 응답
- 빈도수가 같은 경우 키워드로 정렬


---

- Java version: 17
- spring boot version: 3.0.4

--- 

프로젝트 루트에 있는 jar 파일을 이용해 서버를 실행시킬 수 있습니다.

- java -jar search.blog-0.0.1-SNAPSHOT.jar


swagger: http://localhost:8080/swagger-ui/index.html
# A test application for store articles
[![Build app](https://github.com/zheleznikov/article-service/actions/workflows/build-app.yml/badge.svg)](https://github.com/zheleznikov/article-service/actions/workflows/build-app.yml)
[![Test Coverage](https://api.codeclimate.com/v1/badges/ca2a9249bbf69d4893a4/test_coverage)](https://codeclimate.com/github/zheleznikov/article-service/test_coverage)
[![Maintainability](https://api.codeclimate.com/v1/badges/ca2a9249bbf69d4893a4/maintainability)](https://codeclimate.com/github/zheleznikov/article-service/maintainability)

An application:
- can receive an article and then save it into DB. Available for user and admin.
- can give a list of articles using pagination. Available for user and admin.
- can give a count of published articles on daily bases for the 7 days. Available ONLY for admin.


It has 3 API:
### 1. Receive an article
POST
 
URL - `/api/v1/create-article`

Request JSON-example:

    
        {
            "title": "Article title",
            "author": "Article author",
            "content": "Article content...",
            "publishingDate": "2024-03-10"
        } 


Success response JSON-example:


        {
          "articleId": "48012656-7028-4304-b17a-125ed9de64e3",
          "message": "Article successfully created",
          "createdAt": "2024-03-11T11:27:33.7801474"
        } 


Example error response: 
    
    {
      "message": "JSON parse error..."
    }

#### This API is available for user and admin with Basic Auth:

    Username: user or admin
    Password: pwd

### 2. Get list of article use pagination
GET

URL - `/api/v1/article-list`

You could add query params for pagination: size and number.

E.g.:  `/api/v1/article-list?number=2&size=6`.

Then you'll get a list of articles as Page.class.
<details>
<summary>Success response JSON-example</summary>

        {
        "content": [
            {
                "articleId": "4ed82b8d-de05-4487-a404-b8a817c6bd8b",
                "createdAt": "2024-03-11T00:26:01.998139",
                "title": "my titile",
                "content": "hey thats my article-with-date",
                "publishingDate": "2024-03-10",
                "author": "serg-zhele-2"
            },
            {
                "articleId": "f029d39b-4730-47f7-b983-e8b405e32e6e",
                "createdAt": "2024-03-10T14:35:16.128381",
                "title": "my titile",
                "content": "hey thats my article-with-date",
                "publishingDate": "2024-03-10",
                "author": "serg-zhele-2"
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 2,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalElements": 4,
        "totalPages": 2,
        "first": true,
        "numberOfElements": 2,
        "size": 2,
        "number": 0,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "empty": false
    }
</details>

#### This API is available for user and admin with Basic Auth:

    Username: user or admin
    Password: pwd



#### 3. Get a count of published articles on daily bases for the 7 days

GET

URL - `/inner-api/number-of-articles-last-7-days`


Success response JSON-example:


        {
          "totalCount": 200
        } 




#### This API is available for admin ONLY with Basic Auth:

    Username: admin
    Password: pwd



## how to run
1. Clone an app

`git clone https://github.com/zheleznikov/article-service.git`
2. Run Docker

`docker run --rm --name pg-docker -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=usr -e POSTGRES_DB=localDB -p 5430:5432 postgres:13`

3. Start an app

`./gradlew bootRun`



Also you could build an app and then run a jar

3.1. Build an APP

`./gradlew build`


3.2. Run jar

`java -jar ./build/libs/article-service-0.0.1-SNAPSHOT.jar`

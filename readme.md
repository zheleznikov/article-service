# A test application for store articles
[![Build app](https://github.com/zheleznikov/article-service/actions/workflows/build-app.yml/badge.svg)](https://github.com/zheleznikovarticle-service/actions/workflows/build-app.yml)

[![Test Coverage](https://api.codeclimate.com/v1/badges/ca2a9249bbf69d4893a4/test_coverage)](https://codeclimate.com/github/zheleznikov/article-service/test_coverage)

It has 3 API:
1. For adding an article. POST-request
URL - /api/v1/create-article


    {
        "title": "Great title",
        "author": "Serg Zhele",
        "content": "This article is about ...",
        "publishingDate": "2024-03-10"
    } 

2. For getting list of articles
URL - /api/v1/article-list?number=0&size=2
You can get a list of articles as Page

3. For getting a count of published articles on daily bases for the 7 days
URL - /inner-api/number-of-articles-last-7-days


## how to try
1. Clone an app
2. Run Docker use `docker run --rm --name pg-docker -e POSTGRES_PASSWORD=pwd -e POSTGRES_USER=usr -e POSTGRES_DB=localDB -p 5430:5432 postgres:13`
3. Start an app
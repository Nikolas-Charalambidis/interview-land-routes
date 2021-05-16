![example workflow](https://github.com/nikolas-charalambidis/interview-land-routes/actions/workflows/build.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Nikolas-Charalambidis_interview-land-routes&metric=alert_status)](https://sonarcloud.io/dashboard?id=Nikolas-Charalambidis_interview-land-routes)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Nikolas-Charalambidis_interview-land-routes&metric=coverage)](https://sonarcloud.io/dashboard?id=Nikolas-Charalambidis_interview-land-routes)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/eb860f37eb614244af8612c5d590ba16)](https://www.codacy.com/gh/Nikolas-Charalambidis/interview-land-routes/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nikolas-Charalambidis/interview-land-routes&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/96851e8a-c54b-4da2-af22-c86814a44ded)](https://codebeat.co/projects/github-com-nikolas-charalambidis-interview-land-routes-master)
[![GitHub](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/Nikolas-Charalambidis/react-hooks/blob/master/LICENSE)

# Interview: Land routes

- **⚠ No longer improved and left as delivered** (except typos or merging/splitting repos and relevant changes).
- **ℹ I am not about to disclose the name of the interviewing company. I am not a creator of the task.**

## Table of contents
- [Task](#task)
- [Solution](#solution)
  * [Libraries and implementation](#libraries-and-implementation)
  * [How to run](#how-to-run)
- [Things to improve](#things-to-improve)
- [Personal rating](#personal-rating)
- [Interviewer rating](#interviewer-rating)

## Task
Your task is to create a simple Spring Boot service, that is able to calculate any possible land
route from one country to another. The objective is to take a list of country data in JSON format
and calculate the route by utilizing individual countries border information.

**Specifications**
- Spring Boot, Maven
- Data link: https://raw.githubusercontent.com/mledoze/countries/master/countries.json
- The application exposes REST endpoint /routing/{origin}/{destination} that
returns a list of border crossings to get from origin to destination
- Single route is returned if the journey is possible
- Algorithm needs to be efficient
- If there is no land crossing, the endpoint returns HTTP 400
- Countries are identified by `cca3` field in country data
- HTTP request sample (land route from Czech Republic to Italy):
  - `GET /routing/CZE/ITA HTTP/1.0` :
    ```
    {
    "route": ["CZE", "AUT", "ITA"]
    }
    ```

**Expected deliveries**
1. Source code
2. Instructions on how to build and run the application

_________

## Solution

I have created a simple Spring Boot application exposing a REST endpoint with a logic of routing between countries. 
A list of countries with borders is available at another endpoint. So the application serves both as a REST server and REST client.

### Libraries and implementation
- **REST client** using **Spring Cloud OpenFeign** for a REST client to the single [endpoint](https://raw.githubusercontent.com/mledoze/countries/master/countries.json). 
DTO object is mapped manually. Since the media type is `text/plain` from the Github raw-content, 
there is required an additional `MappingJackson2HttpMessageConverter` configured in the global `FeignConfiguration`.
- **REST server** is generated from the OpenApi specification using **OpenAPI generator Maven plugin**. 
The controller layer with the models is available on the classpath once generated. 
A simple GET endpoint is the only choice for this use-case.
- **DTO mapping** and object boilerplate generation is done using a strong combo **MapStruct** and **Lombok**. 
It means the annotation processors must be configured in `maven-compiler-plugin`. 
- **Package structure** is simply [layered](https://phauer.com/2020/package-by-feature/#package-by-layer). 
I use standard Spring beans implementing an interface and autowiring by constructor (except the test classes).
- **Automation** is based on GitHub Actions and SonarCloud integration for code coverage.
- **Search algorithm** is a breadth-first search in an unweighted graph remembering visited notes to avoid cycling.
I initially thought of finding an implementation of the Dijsktra's algorithm, 
but it would be overkill since the length of edges are in this case equal to 1 and each other.
Although, in the reality, the solution might not offer the shortest path but rather a path with the least borders crossing 
(let's say we do an application for some drug-traffickers to help them to minimize risks).
I started up with this [answer](https://stackoverflow.com/a/1579508/3764965) on StackOverflow 
and modified it to match my data structure and 
optimized it a bit to cut off unnecessary searches as long the algorithm is based on the breath-first search.
I suck in these things, so I rather searched for something that works, and I can modify it for my needs.
- **Optimization** and **validations** are also done. It makes no sense to search for a land route between the USA and Japan. 
In `Region` enum class, I implemented a simple check used later whether it makes sense to search for the shortest path between the countries.
Also, if the country doesn't exist in the data set returned from the [endpoint](https://raw.githubusercontent.com/mledoze/countries/master/countries.json) 
or there is surely no way to get from an origin to the destination by feet. 

What surprised me was that Singapore is understood as an isolated island (borderless) in the Asia region, 
although Malaysia [**HAS**](https://en.wikipedia.org/wiki/Malaysia) borders with Singapore and there is [**Johor Causeway bridge**](https://en.wikipedia.org/wiki/Johor%E2%80%93Singapore_Causeway) between these countries.

### How to run

The only prerequisite is Java 11.

 - Build the application:
   - Windows: `mvnw.cmd clean install`
   - Unix: `mvnw clean install`
   
 - Run the application:
   - Windows: `mvnw.cmd spring-boot:run`
   - Unix: `mvnw spring-boot:run`

 - Try it out in Postman / Insomnia / Command line:
    ```
   curl --request GET \
    --url http://localhost:8080/routing/CZE/ITA
   ```

Let's try some interesting combinations:
 - A route between Czechia and Italy: `/routing/CZE/ITA` results in `"CZE","AUT","ITA"`
 - A quite long path between Lesotho and Malaysia: `/routing/LSO/MYS` results in `"LSO","ZAF","BWA","ZMB","COD","CAF","SDN","EGY","ISR","JOR","IRQ","IRN","AFG","CHN","MMR","THA","MYS"`
 - One island, two countries (The UK and Ireland): `/routing/GBR/IRL` results in `"GBR","IRL"`
 - Let's not leave Czechia: `/routing/CZE/CZE` results in `"CZE"`
 - Let's not leave an island: `/routing/GRL/GRL` results in `"GRL"`

_____________

## Things to improve 

 - **Algorithm**: I feel the algorithm would be faster if the search happens from both `origin` **and** `destination` continuously, 
 i.e. the number of visited countries during the search might be reduced. I may be wrong at this one, though.
 - **Integration test**: Yeah, I omitted them.
 - **Swagger UI**: I had a trouble configuring Swagger UI. Maybe next time.
 
____________

## Personal rating

At the first glance, it looked like a simple REST client and server application based on Spring Boot where 
I had a chance to show off the various ways of integration, DTO mapping, error handling, code generating, 
testing etc.

However, after studying the [endpoint](https://raw.githubusercontent.com/mledoze/countries/master/countries.json) data structure, and seeing 
the task is all about finding the shortest route which eventually led to one of the graph search algorithms. 
Sadly, the task specification explicitly states *"Algorithm needs to be efficient"* but nothing saying the code should be readable, maintainable, automated, and tested. This rather brings me back to the algorithm books over discovering new and modern ways to get things done. I have to admit I highly appreciate masking yet another graph algorithm into a real use-case using an external API (or a JSON at least), though. The task was fun!

**Summary**:

- Implementation requirements: 3/5
- Task complexity: 3/5
- Time consuming: 3/5 (between 4h to 8h depending on the seniority)
- Recommended tech-stack: 4/5
- Freedom of tech-stack choice: 5/5
- Task creativity and idea: 4/5
- Real use-case: 4/5

____________

## Interviewer rating

TODO

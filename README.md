# Credit Card Application

## Important Assumptions for Application

- An user can have multiple credit cards
- No credit card is shared between user
- Once registered the only property user allowed to update is password in User entity
- If an user is deleted the credit card associated with user is also deleted

## Technical Decision & Explanation

### Using JWT to secure Api

JWT token is user to secure api

#### PROS
- Allow multiple login
- Stateless , so no session management
- Since stateless , scalability is good 
 
#### CONS

-  Even is user is logged out ,token is still valid. 

### Exposing H2DB without Authorization

This done for demo purpose only

### Different Deployments for UI & Service

Thought to user different application for frontend and backend. This helps to scale components independently 
(Both vertically and horizontally across layers)


## Sample

````bash

curl -X GET \
  http://localhost:8060/users/2/credit-card \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTYyNTY5NDg0LCJleHAiOjE1NjI2NTU4ODR9.Kdnrden-5qPirh-dSJWm4JVVZPSgH6GeDj6cp6qyXPfNWkbWhGFTWkaNjXn3r5EWnsjYZwe1MQ3F7wOsTElIMA' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Host: localhost:8060' \
  -H 'Postman-Token: 19987ae5-3955-494e-87e7-66c8f74dfa12,ac1affcf-2640-42a6-ad5f-558fa168eb3f' \
  -H 'User-Agent: PostmanRuntime/7.15.2' \
  -H 'cache-control: no-cache'
  
  curl -X PUT \
    http://localhost:8060/auth/login \
    -H 'Accept: */*' \
    -H 'Accept-Encoding: gzip, deflate' \
    -H 'Cache-Control: no-cache' \
    -H 'Connection: keep-alive' \
    -H 'Content-Length: 45' \
    -H 'Content-Type: application/json' \
    -H 'Host: localhost:8060' \
    -H 'Postman-Token: d1bd1b8f-fd70-4dcf-8bed-31e8e8700bae,e7136197-ab17-46c5-bce5-d721cee283b8' \
    -H 'User-Agent: PostmanRuntime/7.15.2' \
    -H 'cache-control: no-cache' \
    -d '{
  	"username":"user",
  	"password":"user123"
  }'
  
  curl -X POST \
    http://localhost:8060/auth/signUp \
    -H 'Accept: */*' \
    -H 'Accept-Encoding: gzip, deflate' \
    -H 'Cache-Control: no-cache' \
    -H 'Connection: keep-alive' \
    -H 'Content-Length: 109' \
    -H 'Content-Type: application/json' \
    -H 'Host: localhost:8060' \
    -H 'Postman-Token: e50b5f38-cbf5-449e-b78b-4a89850a8b9f,1047545f-9f54-4c03-aae9-f368c78487e3' \
    -H 'User-Agent: PostmanRuntime/7.15.2' \
    -H 'cache-control: no-cache' \
    -d '{
  	"username":"user",
  	"password":"user123",
  	"name":"user name",
  	"email":"user@org.com",
  	"role":["USER"]
  }'
  
  curl -X GET \
    http://localhost:8060/users/1/credit-card \
    -H 'Accept: */*' \
    -H 'Accept-Encoding: gzip, deflate' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU2MjU2OTYxMywiZXhwIjoxNTYyNjU2MDEzfQ.7GjQ7aIPhvE9tGhxGrlSvnqkaRMB6A7N54bgp0J9WdOQS-Hk48RKJ2Qs4W_Xo-3ssz7NxLy2oWPQInGMWq3GUQ' \
    -H 'Cache-Control: no-cache' \
    -H 'Connection: keep-alive' \
    -H 'Host: localhost:8060' \
    -H 'Postman-Token: cfd80c46-dcc6-48c5-9cb5-6ba9127cdc74,fc58dd72-6194-4716-a7f5-f5cbf328e5f2' \
    -H 'User-Agent: PostmanRuntime/7.15.2' \
    -H 'cache-control: no-cache'
    
    curl -X GET \
      http://localhost:8060/admin/users \
      -H 'Accept: */*' \
      -H 'Accept-Encoding: gzip, deflate' \
      -H 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU2MjU2OTAyOCwiZXhwIjoxNTYyNjU1NDI4fQ.hgk4kxAy-9wJGWHJLZBzh2xpuCrOPQH9rdblw2YT9S5ryLDpEUOETcOh6o7-VWVKyCuM7tFPg7ddMaVpgTwtlA' \
      -H 'Cache-Control: no-cache' \
      -H 'Connection: keep-alive' \
      -H 'Host: localhost:8060' \
      -H 'Postman-Token: f717018f-6fa8-4cd2-9b13-6abf0abf3a67,d97698d4-727d-4ff2-88f2-7201b2c1db26' \
      -H 'User-Agent: PostmanRuntime/7.15.2' \
      -H 'cache-control: no-cache'
````
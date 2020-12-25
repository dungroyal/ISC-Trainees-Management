# ISC-Trainees-Management
# API - ISC - Management
#Fix and Update Spring Security API - ISC - Management
#
-url Register: http://localhost:8080/api/auth/register
-Request Body Register:

{
  "username": "shinAdmin",
  "email": "shinAdmin@gmail.com",
  "password": "11111111",
  "role": ["admin"]
}
ex Role: "role": ["user", "admin"], "role": ["mod"].....
====//=====
-url Login: http://localhost:8080/api/auth/login
-Request Body Login:

{
  "username": "shinAdmin",
  "password": "11111111"
}

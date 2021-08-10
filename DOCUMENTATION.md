# Simple Documentation

- CRUD of Users, Records and Services from API

## Model
- Service.java
- Record.java
- User.java

## V1
----

### Authentication

- Request Body consumes `application/json`

|Header Name    | Value                             |
|---            |---                                |
|X-Auth-Token   | `accessToken` from `/v1/login/`   |

#### Authentication

##### POST /v1/users/signUp
Request:
```json
{
    "username": "user",
    "password": "password"
}
```

Response:
```json
{
    "id": 1,
    "uuid": "2ddfc8cc-5e60-4f87-88d0-ba190a5014ca",
    "username": "user",
    "role": "ROLE_USER",
    "status": "ACTIVE",
    "userBalance": 100.0,
    "deletedAt": null
}
```

##### POST /v1/users/login
Request:
```json
{
    "username": "user",
    "password": "password"
}
```

Response:
```json
{
    "roles": [
        "ROLE_ADMIN"
    ],
    "accessToken": "ey40eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sImV4cCI6MTYyODUyNjc3NCwidXNlcm5hbWUiOiJqb3NlIn0.Gp_SxP2a-j-axBky2E4amMZ8rUgslB_xcr1ij2RfpKUR645_Wb2Of2-b74Hh7dd_GDK6wn7udZBLGDmBGdD2jg",
    "username": "user"
}
```

#### Users
Response `UserResponse.java`
```json
{
  id: user.id,
  username: user.username,
  uuid: user.uuid,
  role: user.role,
  status: user.status,
  userBalance: user.userBalance,
}
```

##### POST /v1/users/create
Request:
```json
{
    "username": "example",
    "password": "apassword",
    "status": "ACTIVE",
    "role": "ROLE_ADMIN",
    "userBalance": 1000.0
}
```

Response:
```json
{
    "id": 1,
    "uuid": "2ddfc8cc-5e60-4f87-88d0-ba190a5014ca",
    "username": "example",
    "status": "ACTIVE",
    "role": "ROLE_ADMIN",
    "userBalance": 1000.0
}
```

##### GET /v1/users
Sample Response:
```json
{
    "totalPages": 0,
    "currentPage": 0,
    "pageSize": 1,
    "result": [
        {
            "id": 1,
            "username": "admin",
            "status": "ACTIVE",
            "role": "ROLE_ADMIN",
            "userBalance": 1000.0
        },
        {
            "id": 5,
            "username": "anuser",
            "status": "ACTIVE",
            "role": "ROLE_USER",
            "userBalance": 100.0
        },
        {
            "id": 3,
            "username": "another_user",
            "status": "ACTIVE",
            "role": "ROLE_USER",
            "userBalance": 20.0
        }
    ]
}
```

##### PUT /v1/users/update
Request:
```json
{
    "username": "example",
    "status": "ACTIVE",
    "role": "ROLE_ADMIN",
    "userBalance": 1000.0
}
```

Response:
```json
{
    "id": 1,
    "username": "example",
    "status": "ACTIVE",
    "role": "ROLE_ADMIN",
    "userBalance": 1000.0
}
```

##### DELETE /v1/users/delete/:userId

Response:
`userId`

#### Services
Response `ServiceEntityResponse.java`
```json
{
  id: service.id,
  uuid: service.uuid,
  status: service.status,
  type: service.type,
  cost: service.cost,
}
```

##### GET /v1/services
Sample Response:
```json
{
  "totalPages": 1,
  "currentPage": 0,
  "pageSize": 7,
  "result": [
    {
      "id": 17,
      "uuid": "d8b22e69-332d-4429-adb4-051ee1061401",
      "type": "DIVISION",
      "cost": 10.0,
      "status": "TRIAL"
    },
    {
      "id": 19,
      "uuid": "0c541e8a-4c5c-4f8e-a333-3c6ea20240ee",
      "type": "SQUARE_ROOT",
      "cost": 1.0,
      "status": "TRIAL"
    },
    {
      "id": 21,
      "uuid": "1171807d-aa12-4897-8228-6830d586fba2",
      "type": "FREE_FORM",
      "cost": 3.0,
      "status": "TRIAL"
    },
    {
      "id": 22,
      "uuid": "bd7a7709-75e2-4f5b-81b6-0479467f6cb1",
      "type": "RANDOM_STRING",
      "cost": 5.0,
      "status": "TRIAL"
    },
    {
      "id": 23,
      "uuid": "da5b7813-10f2-409a-b91e-50c78323079b",
      "type": "MULTIPLICATION",
      "cost": 2.0,
      "status": "TRIAL"
    },
    {
      "id": 18,
      "uuid": "d8b22e69-332d-4429-adb4-051ee1061400",
      "type": "ADDITION",
      "cost": 2.0,
      "status": "TRIAL"
    },
    {
      "id": 20,
      "uuid": "1b3ad9d4-624d-46a9-b034-acb1344096c7",
      "type": "SUBTRACTION",
      "cost": 2.0,
      "status": "TRIAL"
    }
  ]
}
```

##### PUT /v1/services/create
Request:
```json
{
  "type": "DIVISION",
  "cost": 10.0,
  "status": "TRIAL"
}
```

Response:
```json
{
  "id": 17,
  "uuid": "d8b22e69-332d-4429-adb4-051ee1061401",
  "type": "DIVISION",
  "cost": 10.0,
  "status": "TRIAL"
}
```

##### PUT /v1/services/update
Request:
```json
{
  "uuid": "d8b22e69-332d-4429-adb4-051ee1061401",
  "type": "DIVISION",
  "cost": 10.0,
  "status": "TRIAL"
}
```

Response:
```json
{
  "id": 17,
  "uuid": "d8b22e69-332d-4429-adb4-051ee1061401",
  "type": "DIVISION",
  "cost": 10.0,
  "status": "TRIAL"
}
```

##### DELETE /v1/services/delete/:serviceId

Response:
`serviceId`

#### Records
Response `RecordResponse.java`
```json
{
  id: record.id,
  uuid: record.uuid,
  service: serviceResponse,
  user: userResponse,
  cost: record.cost,
  userBalance: record.userBalance,
  serviceResponse: record.serviceResponse,
  date: record.date,
}
```

##### GET /v1/records & GET /v1/records/user 
Sample Response:
```json
{
  "totalPages": 1,
  "currentPage": 0,
  "pageSize": 2,
  "result": [
    {
      "id": 11,
      "uuid": "9564399f-de40-4358-8697-5806dab8480f",
      "service": {
        "id": 19,
        "uuid": "0c541e8a-4c5c-4f8e-a333-3c6ea20240ee",
        "type": "SQUARE_ROOT",
        "cost": 1.0,
        "status": "TRIAL"
      },
      "user": {
        "id": 3,
        "username": "jose2",
        "uuid": "2cdb4b8b-aaaa-4987-86e3-e94df5d98939",
        "status": "ACTIVE",
        "role": "ROLE_USER",
        "userBalance": 77.0,
        "deletedAt": null
      },
      "cost": 1.0,
      "userBalance": 96.0,
      "serviceResponse": "2.8284271247461903",
      "date": "2021-08-10T10:35:19.235629"
    },
    {
      "id": 12,
      "uuid": "cce2557a-e99e-4224-87ec-b904a02b6495",
      "service": {
        "id": 19,
        "uuid": "0c541e8a-4c5c-4f8e-a333-3c6ea20240ee",
        "type": "SQUARE_ROOT",
        "cost": 1.0,
        "status": "TRIAL"
      },
      "user": {
        "id": 3,
        "username": "jose2",
        "uuid": "2cdb4b8b-aaaa-4987-86e3-e94df5d98939",
        "status": "ACTIVE",
        "role": "ROLE_USER",
        "userBalance": 77.0,
        "deletedAt": null
      },
      "cost": 1.0,
      "userBalance": 95.0,
      "serviceResponse": "2.8284271247461903",
      "date": "2021-08-10T10:36:29.061098"
    }
  ]
}
```

##### PUT /v1/records/create
Request:
```json
{
  "serviceId": 19,
  "userId": 3,
  "cost": 1.0,
  "userBalance": 95.0,
  "serviceResponse": "2.8284271247461903",
  "date": "2021-08-10T10:36:29.061098"
}
```

Response:
```json
{
  "id": 12,
  "uuid": "cce2557a-e99e-4224-87ec-b904a02b6495",
  "service": {
    "id": 19,
    "uuid": "0c541e8a-4c5c-4f8e-a333-3c6ea20240ee",
    "type": "SQUARE_ROOT",
    "cost": 1.0,
    "status": "TRIAL"
  },
  "user": {
    "id": 3,
    "username": "jose2",
    "uuid": "2cdb4b8b-aaaa-4987-86e3-e94df5d98939",
    "status": "ACTIVE",
    "role": "ROLE_USER",
    "userBalance": 77.0,
    "deletedAt": null
  },
  "cost": 1.0,
  "userBalance": 95.0,
  "serviceResponse": "2.8284271247461903",
  "date": "2021-08-10T10:36:29.061098"
}
```

##### PUT /v1/records/update
Request:
```json
{
  "id": 12,
  "serviceId": 19,
  "userId": 3,
  "cost": 1.0,
  "userBalance": 95.0,
  "serviceResponse": "2.8284271247461903",
  "date": "2021-08-10T10:36:29.061098"
}
```

Response:
```json
{
  "id": 12,
  "uuid": "cce2557a-e99e-4224-87ec-b904a02b6495",
  "service": {
    "id": 19,
    "uuid": "0c541e8a-4c5c-4f8e-a333-3c6ea20240ee",
    "type": "SQUARE_ROOT",
    "cost": 1.0,
    "status": "TRIAL"
  },
  "user": {
    "id": 3,
    "username": "jose2",
    "uuid": "2cdb4b8b-aaaa-4987-86e3-e94df5d98939",
    "status": "ACTIVE",
    "role": "ROLE_USER",
    "userBalance": 77.0,
    "deletedAt": null
  },
  "cost": 1.0,
  "userBalance": 95.0,
  "serviceResponse": "2.8284271247461903",
  "date": "2021-08-10T10:36:29.061098"
}
```

##### DELETE /v1/records/delete/:recordId

Response:
`recordId`

#### Operations (Services)
##### POST /v1/services/
This endpoint will look for the Service with that type and obtain the info from the request.

###### Example ADDITION:

Request:
```json
{
    "serviceType": "ADDITION",
    "numbers": [-1.0, 1, 2]
}
```

Response:
```json
{
    "serviceType": "ADDITION",
    "resultString": "2",
    "result": 2
}
```

###### Example SQUARE_ROOT:

Request:
```json
{
    "serviceType": "SQUARE_ROOT",
    "numbers": [-1.0, 1, 2], // will use -1.0 only
    "number": -1.0 // will use this first then go for the first number of numbers.
}
```

Response:
```json
{
    "serviceType": "SQUARE_ROOT",
    "resultString": "NaN",
    "result": "NaN"
}
```

###### Example FREE_FORM:

Request:
```json
{
    "serviceType": "FREE_FORM",
    "freeForm": "1+2+3"
}
```

Response:
```json
{
    "serviceType": "FREE_FORM",
    "resultString": "6",
    "result": "6"
}
```

###### Example RANDOM_STRING:

Request:
```json
{
    "serviceType": "RANDOM_STRING"
}
```

Response:
```json
{
    "serviceType": "RANDOM_STRING",
    "resultString": "randOm2hehe"
}
```
# Parking Spot Control API

API de CRUD desenvolvida utilizando Spring no curso da Michelli Brito.

### POST /parking-spot

Request body:

```json
{
    "parkingSpotNumber": "string",
    "licensePlateCar": "string",
    "brandCar": "string",
    "modelCar": "string",
    "colorCar": "string",
    "responsibleName": "string",
    "apartment": "string",
    "block": "string"
}
```

Success response status ```201 Created```:

```json
{
    "id": "921545b6-3201-4b1d-9f32-326773391fc9",
    "parkingSpotNumber": "string",
    "licensePlateCar": "string",
    "brandCar": "string",
    "modelCar": "string",
    "colorCar": "string",
    "responsibleName": "string",
    "apartment": "string",
    "block": "string",
    "createdAt": "2023-03-12T16:47:06Z",
    "updatedAt": null
}
```

Fail response status ```409 Conflict```:

```
Conflict: Licence plate car is already in use.
```

```
Conflict: Parking spot number is already in use.
```

```
Conflict: Parking spot is already registered for this apartment.
```

### GET /parking-spot

Request body: ```none```

Response status ```200 OK```:

```json
[
  {
    "id": "921545b6-3201-4b1d-9f32-326773391fc9",
    "parkingSpotNumber": "string",
    "licensePlateCar": "string",
    "brandCar": "string",
    "modelCar": "string",
    "colorCar": "string",
    "responsibleName": "string",
    "apartment": "string",
    "block": "string",
    "createdAt": "2023-03-12T16:47:06Z",
    "updatedAt": null
  }
]
```

Response headers:

```
pageNumber: int
pageSize: int
totalPages: int
totalElements: int
```

### GET /parking-spot/{id}

Request body: none

Success response status ```200 OK```:

```json
{
    "id": "921545b6-3201-4b1d-9f32-326773391fc9",
    "parkingSpotNumber": "string",
    "licensePlateCar": "string",
    "brandCar": "string",
    "modelCar": "string",
    "colorCar": "string",
    "responsibleName": "string",
    "apartment": "string",
    "block": "string",
    "createdAt": "2023-03-12T16:47:06Z",
    "updatedAt": null
}
```

Fail response status ```404 Not Found```:

```
Parking spot not found.
```

### PUT /parking-spot/{id}

Request body:

```json
{
    "parkingSpotNumber": "string",
    "licensePlateCar": "string",
    "brandCar": "string",
    "modelCar": "string",
    "colorCar": "string",
    "responsibleName": "string",
    "apartment": "string",
    "block": "string"
}
```

Success response status ```204 No Content```

Fail response status ```404 Not Found```:

```
Parking spot not found.
```

### DELETE /parking-spot/{id}

Request body: ```none```

Success response status ```204 No Content```

Fail response status ```404 Not Found```:

```
Parking spot not found.
```

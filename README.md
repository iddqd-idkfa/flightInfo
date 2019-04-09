# Flight Info 

## Project

This project displays available flight info by aggregating it from two sources:

- `https://obscure-caverns-79008.herokuapp.com/business`
- `https://obscure-caverns-79008.herokuapp.com/cheap`

### Request

Requests are only accepted in `GET` type, and request accepts two optional parameters: `sort` and `page`.
-  `page` argument.
    -  when it's **not supplied**, server generates a new response and returns it, while caching the response.
    -  when it's **supplied**, server tries to return a previously generated response and does not fetch new results from the two abovementioned sources.
- `sort` argument. available sort values: `+id`,`-id`,`+from`,`-from`,`+to`,`-to`,`+arrival`,`-arrival`,`+departure`,`-departure`, where + stands for ascending ordering and - stands for descending ordering
    - when it's **not supplied**, server returns the previously sorted array, or does not apply any sorting to the new flight info that is fetched.
    - when it's **supplied**, server checks if the previous sorting method was the same. if not, it re-sorts the flight info according to the new sorting method.

Request examples:

`http://localhost:8000/flights?sort=+from` requests a new set of flight info to be fetched, with *ascending source city(from)* ordering

`http://localhost:8000/flights?sort=+from&page=1` requests 2nd page of the previously cached flight info

`http://localhost:8000/flights?sort=-arrival&page=3` requests 4th page of the previously cached flight info, but wants *descending arrival time* ordering instead.
  

### Response 
Response type: `application/json`

Response size: Each response contains at most 10 flight info, separated by pagination

Response example: 
```
{
  "res": [
    {
      "uuid": "4129062200552738816",
      "from": "Tizi",
      "to": "Formosa",
      "departure": "2019-04-09T02:44:42.466Z",
      "arrival": "2019-04-09T03:33:38.616Z"
    },
    {
      "uuid": "8619336192028798976",
      "from": "Vicuna Mackenna",
      "to": "Cruz del Eje",
      "departure": "2019-04-09T03:27:26.862Z",
      "arrival": "2019-04-09T05:43:22.681Z"
    },
    {
      "uuid": "8468314847704314880",
      "from": "Vicuna Mackenna",
      "to": "Granadero Baigorria",
      "departure": "2019-04-09T02:12:57.725Z",
      "arrival": "2019-04-09T03:43:03.265Z"
    }
  ],
  "numpages": 4
}
```
Explanation: `res` field contains the flight info, each flight consisting of `uuid`, `from`, `to`, `departure` and `arrival` fields for unique id, source city, desination city, departure time (in GMT+0) and arrival time (in GMT+0) respectively.

## Running the project

By default, project attaches to localhost and to port 8000. Settings can be changed from `application.properties` file

### curl teting commands
### get All Users
```
curl -s http://localhost:8080/topjava/rest/admin/users
```
### get User 100001(Admin)
```
curl -s http://localhost:8080/topjava/rest/admin/users/100001
```
### get All Meals
```
curl -s http://localhost:8080/topjava/rest/meals
```
### get Meal 100005
```
curl -s http://localhost:8080/topjava/rest/meals/100005
```
### getBetween Meals
```
curl -s "http://localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&startTime=07:00:00&endDate=2020-01-31&endTime=11:00:00"
```
### get Meal not found
```
curl -s -v http://localhost:8080/topjava/rest/meals/100100
```
### delete Meal
```
curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100003
```
### create Meal
```
curl -s -X POST -d '{"dateTime":"2020-06-01T11:00","description":"Created breakfast","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals
```
### update Meal
```
curl -s -X PUT -d '{"dateTime":"2020-01-31T20:00", "description":"Updated dinner", "calories":520}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100009
```
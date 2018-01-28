# Server SIT

### Génération d'un token
Pour générer un token il suffit de passer une requete en POST comme suit : 
```shell
curl -X POST \
-H "X-Requested-With: XMLHttpRequest" 
-H "Content-Type:application/json"
-H "Cache-Control: no-cache"
-d '{"username": "admin","password": "admin"}'
"http://localhost:8080/api/authenticate"
```

```shell
curl -X POST -H "X-Requested-With: XMLHttpRequest" -H "Content-Type:application/json" -H "Cache-Control: no-cache" -d '{"username": "admin","password": "admin"}' "http://localhost:8080/api/authenticate"
```

Pour accéder à l'API protégée par un authentification JWT passé le token dans le header :

```shell
curl -H "Authorization: Bearer $id_token" "http://localhost:8080/api/users/"
```

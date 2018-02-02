# Server SIT

### Base de données
Avant de lancer le projet une base de données MongoDB doit être lancée 

Par défaut le projet est configuré pour que la base de données soit sur le port _27017_ du localhost

jHispter propose un _DockeFile_ pour la base de données que l'on peut lancer via :
```shell
docker-compose -f ./src/main/docker/mongodb.yml up
```
ou via un simple docker run : 
```shell
docker run --name mongodb -p 27017:27017 mongo
```

ou via l'installation de mongodb en local.

### Pour builder le projet
Compiler le coté client en Angular 5
```shell
npm install
```
Lancer le serveur avec Maven
```shell
mvn
```

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

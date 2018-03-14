Compte rendu SIT - Antoine Mullier
=====================
Groupe B
-------------------

Pendant SIT, nous avons pu travaillé en équipe sur les approches 
qui nous permettraient de mettre en place une solution pour le projet final.

Pendant cette première phase, l'objectif principal était de mettre en place
une architecture pouvant assurer le transfert du message de la tablette androïd
vers le drone. Nous nous sommes dirigé vers une architecture divisée en 3 : 
- La tablette android, qui propose une application qui propose des actions utilisateurs
simples à comprendre et à utiliser.
- Le serveur qui va servir de relai pour entre la tablette et le drone. Il va également permettre
de persister les données.
- Le drone qui va récupérer les informations du serveur pour intéragir avec le drone en tant que tel.

Je me suis orienté sur la partie serveur pour laquelle nous avons choisi les technos suivantes :
- Pour le serveur WEB : une application spring avec Java (avec jhipster)
- Pour la base de données : un essai avec MongoDB pour voir les avantages de la technologie.
- Un serveur RabbitMQ pour assurer le transfert des données dans le sens Drone>Tablette

Finalement nous avons pu mettre en place les notions de missions, d'ordre (dans une mission) mais
également le déploiement avec Jenkins et Docker (facilité avec jHipster).

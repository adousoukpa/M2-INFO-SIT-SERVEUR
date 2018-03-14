# Compte rendu SIT - SADOK Romain
## Groupe B
#### Sous groupe Android
Ma contribution au projet de SIT fut sur différentes facettes, à commencer par le design de l’architecture général du projet, et des différentes maquettes sur android.

On a penser à la communication drone-android via un serveur RabbitMQ. J’ai mis en place un service android qui tourne en arrière plan, et qui écoute si des nouvelles données ( que ça soit les coordonnées GPS du drone, ou son état ..ect ) sont arrivées dans les queues où on s’est préalablement abonné, une fois qu’on a consommé la donnée, on la remonte vers la map (Fragement android) qui est supposé afficher la position du drone "en temps réel".

J'ai mis en place aussi l'activity missionManager qui Permet de naviguer vers les principales fonctionnalités de l’application:

* Création d'une nouvel mission.
* Visualisation des missions.
* lancement d'une mission ..ect

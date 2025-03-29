# 8INF257 - Informatique mobile - Hiver 2025

## Équipe 11 - Arthur CHEVREUX (CHEA17120400), Aloïs FLEURY (FLEA05010500), Ewen HAMONO (HAME02020400)

### Application mobile de gestion de routines

### Premier démarrage du projet

Pour sélectionner le lieu de la routine sur une carte, la librairie de Google Maps a été utilisée.
Elle nécessite l'ajout d'une clé d'API.

Pour ajouter la clé au projet, il faut créer un fichier `secrets.properties` à la racine du projet.
Ce fichier doit contenir le texte suivant : `MAPS_API_KEY=` (il faut ajouter la clé d'API après le
signe '=', on ne peut pas l'exposer sur GitHub).
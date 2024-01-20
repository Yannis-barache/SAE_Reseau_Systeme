# Tuit'o :incoming_envelope:

![GitHub last commit](https://img.shields.io/github/last-commit/Yannis-barache/SAE_Reseau_Systeme?style=for-the-badge)

![GitHub repo size](https://img.shields.io/github/repo-size/Yannis-barache/SAE_Reseau_Systeme?style=for-the-badge)

![GitHub top language](https://img.shields.io/github/languages/top/Yannis-barache/SAE_Reseau_Systeme?style=for-the-badge)

![GitHub language count](https://img.shields.io/github/languages/count/Yannis-barache/SAE_Reseau_Systeme?style=for-the-badge)


![GitHub contributors](https://img.shields.io/github/contributors/Yannis-barache/SAE_Reseau_Systeme?style=for-the-badge)



![Logo de l'université et logo de l'IUT'O informatique](./img/Logo.png)

## 📝 Table des matières



<nav>
    <ul>
        <li><a href="#description">Description du projet</a></li>
        <li><a href="#membres">Membres du groupe</a></li>
        <li><a href="#fonctionnalites">Les fonctionnalités</a></li>
        <li><a href="#technologies">Les technologies utilisées</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#utilisation">Utilisation</a></li>
        <li><a href="#documentation">Manuel Utilisateur</a></li>
        <li><a href="#justification">Justification des choix techniques</a></li>
        <li><a href="#diagramme">Diagramme de classe</a></li>
    </ul>
</nav>

## :page_facing_up: Description du projet <a name = "description"></a>

Ce projet est un projet de groupe réalisé dans le cadre de notre deuxième année de BUT Informatique. Il a pour but de nous faire découvrir le monde de l'entreprise et de nous faire travailler en groupe sur un projet concret. Ce projet porte plus précisément sur la création d'une application client-serveur recréant un réseau social qui s'appelera `Tuit'o`.

## :busts_in_silhouette: Membres du groupe <a name = "membres"></a>

[![Yannis Barache](https://img.shields.io/badge/Yannis%20Barache-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Yannis-barache)



[![Ibrahima Barry](https://img.shields.io/badge/Ibrahima%20Barry-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ibarry25)

## :computer: Les technologies utilisées <a name = "technologies"></a>
- `Java` (pour le serveur et le client)
- `JavaFX` (pour l'interface graphique)
- `Git` (pour la gestion du projet)

## :inbox_tray: Installation <a name = "installation"></a>

Pour installer le projet, il vous suffit de cloner le projet sur votre machine.

```bash
git clone https://github.com/Yannis-barache/SAE_Reseau_Systeme.git
```

##  Utilisation <a name = "utilisation"></a>

Pour utiliser l'application, il vous suffit d'executer le fichier `lancement.sh` qui se trouve à la racine du projet avec la commande suivante.

```bash
./compil.sh
```

ensuite il vous suffit d'exécuter le fichier Serveur.java puis le fichier Client.java.


## Manuel utilisateur <a name = "documentation"></a>


### Pour poster un message

`<message>` : Permet de poster le message que l'utilisateur souhaite ce message sera mis dans le json 
et pourra être liké ou supprimé par la suite.

### Pour interagir avec l'application 

L'utilisateur peut utiliser le terminal pour interagir avec l'application. Il peut utiliser 
les commandes suivantes précédés par un `/` :


#### Pour supprimer un message

`delete <id_message>` : Permet de supprimer un message en indiquant le message 
que l'utilisateur souhaite supprimer.

#### Pour liker un message
`like <id_message>` : Permet de liker un tuit en indiquant l'id du message que l'utilisateur souhaite liker.

#### Pour suivre ou ne plus suivre un utilisateur
`follow <nom_utilisateur>` : Permet de suivre l'utilisateur passée en paramètre. <br>
`unfollow <nom_utilisateur>` : Permet de ne plus suivre l'utilisateur passée en paramètre.

#### Pour quitter l'application
`exit` : Permet de quitter l'application.


## Justification des choix techniques <a name = "justification"></a>

### Pourquoi Java ?

Nous avons choisi Java car c'est un langage que nous connaissons bien et qui est très utilisé dans le monde de 
l'entreprise. De plus, il est très utilisé pour la création d'application client-serveur.
Dans l'optique de réaliser une interface graphique, nous aurions aussi pu utiliser JavaFX.

### Pourquoi utiliser un json ?
Toutes les données de l'application sont stockées dans un fichier json.
Le json est un format de fichier très utilisé donc très documenté. 
Il est aussi très facile à manipuler en Java avec l'utilisation de 
`org.json` qui est une librairie très connue et très utilisée.

Une autre solution aurait été d'utiliser une base de données mais nous avons préféré utiliser un fichier json car 
nous n'avions pas besoin de faire des requêtes complexes et nous n'avions pas besoin de faire des jointures.


## Diagramme de classe  <a name = "diagramme"></a>
<div style="text-align:center">
<img src="Diagramme.png" />
</div>












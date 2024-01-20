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
        <li><a href="#documentation">Documentation</a></li>
    </ul>
</nav>

## :page_facing_up: Description du projet <a name = "description"></a>

Ce projet est un projet de groupe réalisé dans le cadre de notre deuxième année de BUT Informatique. Il a pour but de nous faire découvrir le monde de l'entreprise et de nous faire travailler en groupe sur un projet concret. Ce projet porte plus précisément sur la création d'une application client-serveur recréant un réseau social qui s'appelera `Tuit'o`.

## :busts_in_silhouette: Membres du groupe <a name = "membres"></a>

[![Yannis Barache](https://img.shields.io/badge/Yannis%20Barache-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Yannis-barache)



[![Ibrahima Barry](https://img.shields.io/badge/Ibrahima%20Barry-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/ibarry25)


## :gear: Les fonctionnalités <a name = "fonctionnalites"></a>

### :computer: Partie Client

- [ ] Création d'un tuit
- [ ] Suppression d'un tuit
- [ ] Like d'un tuit
- [ ] Follow d'un utilisateur
- [ ] Unfollow d'un utilisateur

### :floppy_disk: Partie Serveur

- [ ] Authentification
- [ ] Création d'un compte
- [ ] Suppresion d'un tuit
- [ ] Suppression d'un utilisateur

## :computer: Les technologies utilisées <a name = "technologies"></a>
- `Java`
- `JavaFX`
- `MySQL`
- `Git`

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


## :page_with_curl: Documentation <a name = "documentation"></a>

L'utilisateur peut utiliser le terminal pour interagir avec l'application. Il peut utiliser les commandes suivantes précédés par un `/` :


- `exit` : Permet de quitter l'application
- `delete <id_message>` : Permet de supprimer un tuit
- `like <id_message>` : Permet de liker un tuit
- `follow <nom utilisateur>` : Permet de suivre un utilisateur
- `unfollow <nom utilisateur>` : Permet de ne plus suivre un utilisateur

L'utilisateur peut également envoyer un message à tout le monde en tapant simplement le message qu'il souhaite envoyer directement dans le terminal.




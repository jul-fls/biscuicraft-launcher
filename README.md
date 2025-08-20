# biscuicraft-launcher

---

# 🚀 Biscuicraft Launcher

Ce projet est le **launcher officiel du serveur Minecraft Biscuicraft**, que nous avons cofondé entre amis.
Il vise à simplifier l’accès au serveur, automatiser la gestion des mods et limiter les risques de triche.

---

## 🗂️ Organisation du projet

Le projet complet est découpé en **3 repositories distincts** :

1. **Launcher principal** → ce repo (*biscuicraft-launcher*)
2. **Launcher du launcher (Bootstrap)** → petit exécutable qui s’occupe de mettre à jour automatiquement le launcher principal
3. **Scripts PHP (site web)** → utilisés pour collecter des statistiques (téléchargements et lancements du jeu)

---

## 🎯 Objectifs

* ✅ **Faciliter l’accès au serveur**
  Pas besoin de télécharger/configurer les mods manuellement : le launcher synchronise automatiquement le jeu local avec le serveur distant.

* ✅ **Limiter la triche et les hacks**
  À chaque lancement, les fichiers du jeu sont scannés (**hash MD5**).
  Tout fichier absent ou modifié est supprimé puis re-téléchargé depuis le serveur de mise à jour.

* ✅ **Indépendance du launcher officiel Minecraft**
  Les fichiers sont stockés dans un dossier dédié `.Biscuicraft`, indépendant du `.minecraft` officiel.
  Ainsi, un joueur peut utiliser le launcher Biscuicraft sans avoir installé Minecraft au préalable (seul **Java 8 64 bits minimum** est requis).

---

## 🧑‍💻 Compétences acquises

Ce projet m’a permis de :

* Apprendre les bases du **langage Java** (syntaxe, classes, méthodes, organisation du code)
* Mettre en pratique mes connaissances en **HTML** (intégrées dans certaines parties du code)
* Réutiliser mes acquis en **PHP** pour la collecte de statistiques côté serveur
* Mélanger **nouvelles compétences (Java)** et **connaissances antérieures (HTML & PHP)** dans un projet complet

---

## 🖼️ Illustrations

Quelques captures d’écran du projet :

* **Interface du launcher principal**
  ![Interface du launcher](https://github.com/user-attachments/assets/22560fe8-f918-4ab7-be5c-f6154dc01951)


* **Écran de chargement du bootstrap (launcher du launcher)**
  ![Bootstrap loading](https://github.com/user-attachments/assets/08cac6aa-4db3-4392-a6bc-1ee85f6e86b3)



* **Exécution du bootstrap**
  ![Bootstrap run](https://github.com/user-attachments/assets/7aed1f64-5df6-42d0-859e-668a954c3b72)

*(Les images sont cliquables dans la version web du projet.)*

---

## ⚙️ Prérequis

* **Java 8** ou supérieur (**64 bits obligatoire**)
* Connexion Internet pour télécharger/mettre à jour les fichiers du jeu

👉 Veux-tu que je t’écrive aussi une **version courte du README (10 lignes max)** comme pour le projet précédent, pour garder un repo GitHub propre et pointer vers ton site/portfolio pour les détails ?

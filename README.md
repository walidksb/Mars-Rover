# 🚀 Mars Rover
## 👥 Membres du projet
- NAHILI Abdelhakim
- KESBI Walid
## 📘 Description du projet
- Ce projet consiste à simuler les déplacements de plusieurs rovers sur une grille représentant la surface de Mars.
- Chaque rover reçoit une configuration initiale (position, orientation) ainsi qu'une liste de commandes (`LEFT`, `RIGHT`, `MOVE`) à exécuter.
- Le programme lit un fichier **YAML** en entrée (`config.yml`), exécute les commandes pour chaque rover, puis écrit les positions finales dans un fichier **YAML** de sortie (`output.yml`).
## 📚 Description des emprunts

Dans le cadre de ce projet, plusieurs ressources ont été consultées afin de comprendre, concevoir et corriger certaines parties du code.  
Les emprunts suivants ont été réalisés dans un objectif pédagogique et de compréhension du langage Java, des formats YAML et de la structure d’un projet Gradle.

---

### 🔹 GitHub
Des exemples de projets similaires ont été consultés sur **GitHub** pour comprendre :
- la **structure des packages** (`input`, `output`, `simulator`) ;
- la **gestion des dépendances Gradle** (`build.gradle.kts`) ;
- et la **lecture/écriture de fichiers YAML** avec **Jackson**.

> 📘 Exemple :
> - Un projet open-source utilisant `ObjectMapper` et `YAMLFactory` pour lire un fichier de configuration.
> - Un autre dépôt présentant une architecture similaire basée sur des records Java et des enums pour modéliser des entités simples.

Les exemples ont servi de référence pour la mise en place du **format de données**, sans copier directement de code.

---

### 🔹 Stack Overflow
Le site **Stack Overflow** a été utilisé pour résoudre des problèmes techniques précis, notamment :
- la configuration de **Gradle** avec le plugin `application` ;
- la **gestion du `PATH` Java** sur Windows (erreur « java n’est pas reconnu ») ;
- l’utilisation de **Jackson** pour sérialiser et désérialiser des `record` Java.

> 💡 Exemple de question consultée :
> - « How to read YAML file in Java using Jackson? »
> - « Java not recognized as an internal or external command (Windows) »
> - « Jackson support for Java 24 & 25 record classes »

Ces réponses ont permis de corriger les erreurs liées à la **lecture YAML**, et d’assurer la **compatibilité du projet avec Java 17**.

---

### 🔹 Livre : *Introduction à l’informatique* — École Polytechnique
📖 **Auteur : François Morain**

Ce livre a servi de **base théorique** pour :
- la compréhension des **concepts fondamentaux de la programmation** (fonctions, structures de données.)
- la **méthodologie de développement logiciel** (abstraction, modularité, tests)
- la réflexion sur la **structure logique du simulateur** (séparation entrée / traitement / sortie).

> 📘 Exemple :
> L’approche consistant à séparer la logique de simulation dans un package dédié (`simulator`) s’inspire des principes de modularité présentés dans ce manuel.

---

### 🔹 ChatGPT
L’outil **ChatGPT** a été utilisé dans un cadre **d’assistance à la rédaction et à la correction**.  
Il a permis :
- d’obtenir des **explications pédagogiques** sur le fonctionnement de Gradle et Jackson ;
- d’aider à **corriger des erreurs de compilation** ou d’imports ;
- et de **formater le code** ou le rendre plus lisible.

> 💡 Exemple :
> - Correction d’une erreur `refusing to merge unrelated histories` lors du push Git.
> - Amélioration du code de lecture/écriture YAML avec `ObjectMapper` et `SequenceWriter`.
> - Génération automatique du `README.md` au format Markdown.

Toutes les suggestions issues de ChatGPT ont été **relues, comprises et adaptées** avant intégration, conformément aux bonnes pratiques universitaires.

---

### 🧩 Synthèse
| Source | Rôle principal | Exemple concret |
|---------|----------------|----------------|
| GitHub | Structure du projet & lecture YAML | Organisation des packages |
| Stack Overflow | Résolution d’erreurs techniques | Problèmes Gradle et Java PATH |
| Livre de François Morain | Base théorique | Modélisation et modularité |
| ChatGPT | Assistance à la correction et à la documentation | Aide à la rédaction du README et du code |

---

Toutes ces ressources ont été utilisées dans une **démarche d’apprentissage**, et non pour copier des solutions toutes faites.  
Elles ont contribué à améliorer la **qualité**, la **structure** et la **compréhension globale** du projet.

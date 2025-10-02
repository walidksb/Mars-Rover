[![CI Pipeline](https://etulab.univ-amu.fr/n24024683/mars-rover-nahili-abdelhakim-kesbi-walid/badges/master/pipeline.svg)](https://etulab.univ-amu.fr/n24024683/mars-rover-nahili-abdelhakim-kesbi-walid/-/pipelines)

# 🚀 Mars Rover 

## 👥 Membres du projet
- **NAHILI Abdelhakim**
- **KESBI Walid**

---

## 📘 Description du projet

Ce projet simule les déplacements de un ou plusieurs **rovers** sur une grille représentant la surface de Mars.  
Chaque rover dispose :
- d’une **position initiale** (`x`, `y`, orientation),
- et d’une **liste de commandes** (`LEFT`, `RIGHT`, `MOVE`).

Le programme :
1. Lit une configuration d’entrée dans un fichier **YAML** (`config.yml`)
2. Exécute les déplacements des rovers
3. Produit un fichier **YAML de sortie** (`output.yml`) indiquant :
    - les positions finales,
    - les orientations,
    - les rovers détruits,
    - et le **pourcentage de la surface explorée**.

---
## 📖 Fonctionnalités

- Supporte **plusieurs rovers** en parallèle.  
- Deux types de grilles :
  - **Rectangulaire** : grille finie avec des bords.  
  - **Toroïdale** : grille qui se referme sur elle-même (comme un donut).  
- **Obstacles** : un rover est détruit s’il tombe sur un obstacle.  
- **Rayon d’exploration** :
  - `0` : par défaut, le rover explore uniquement les cases visitées.  
  - `1` : explore toutes les cases voisines autour de sa trajectoire.  
  - `2+` : explore toutes les cases dans une distance de Manhattan donnée.  
- **Observateur de simulation** : permet de suivre les positions des rovers et les cases explorées à chaque étape pour la creation de l'interface graphique.  
- Génère un résultat final contenant :
  - Pourcentage de la grille explorée.  
  - État final de chaque rover (détruit ou survivant, position finale).  
  - L’ensemble des coordonnées explorées. 

---

## 🛠️ Structure du projet
fr.univ_amu.m1info.mars_rover
├── input
│ ├── MarsRoverInput.java
│ ├── RoverConfiguration.java
│ ├── GridConfiguration.java
│ ├── Command.java
│ ├── Coordinates.java
│ ├── Direction.java
│ ├── GridKind.java
│ └── Position.java
├── output
│ ├── MarsRoverOutput.java
│ ├── MarsRoverState.java
│ ├── Coordinates.java
│ ├── Position.java
│ └── RoverGUI.java
└── simulator
  ├── App.java
  └── MarsRoverSimulator.java

---
### Prérequis
- **Java 17** ou supérieur
- **Gradle** (pour construire et exécuter le projet)
- Fichier de configuration YAML `config.yml` dans le répertoire racine

### Commandes Gradle
1. **Compiler le projet** :
```bash
gradle build

gradle run
```
Le programme lit automatiquement config.yml et produit output.yml.

## 📚 Description des emprunts

Au cours du développement de ce projet, plusieurs ressources externes ont été **consultées et adaptées** afin de mieux comprendre certains concepts liés à la programmation Java, à la gestion des fichiers YAML, à la conception modulaire et aux tests unitaires.  
Chaque ressource a été utilisée dans un **but d’apprentissage**, et toutes les idées empruntées ont été **analysées, modifiées et intégrées** pour s’adapter à la structure et aux besoins spécifiques du projet.

Aucune portion de code n’a été copiée telle quelle — chaque contribution a servi à **améliorer notre compréhension** et à renforcer la qualité de notre propre implémentation.

---

### 🔹 GitHub — Projets open source similaires

**Objectif :**  
Explorer des projets similaires (notamment le *Mars Rover Kata*) afin de comprendre :
- comment **structurer un projet modulaire** avec plusieurs packages (`input`, `output`, `simulator`) ;
- comment **lire et écrire des fichiers YAML** à l’aide de `ObjectMapper` et `YAMLFactory` ;
- et comment **organiser les tests unitaires** avec JUnit 5.

> 📘 Exemple emprunté :  
> Un projet présentait une manière simple de lire une configuration YAML avec Jackson :
> ```java
> ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
> MarsRoverInput input = objectMapper.readValue(inputStream, MarsRoverInput.class);
> ```

🧠 **Adaptation réalisée :**  
Nous avons repris cette idée, mais en l’adaptant à notre architecture :
- utilisation de **records Java** pour modéliser des entités immuables ;
- création d’une classe `GridConfiguration` permettant de gérer des grilles de taille variable ;
- ajout de contrôles d’erreurs (`Objects.requireNonNull`) pour garantir la validité des données.

---

### 🔹 Stack Overflow — Résolution de problèmes techniques

**Objectif :**  
Utiliser des discussions techniques pour résoudre des erreurs rencontrées lors du développement, notamment :
- la configuration du **plugin Gradle application** et du `mainClass` ;
- la **désérialisation des records Java** avec Jackson ;
- les erreurs liées à `NullPointerException` lors de la lecture YAML ;
- et des problèmes de **configuration du PATH** Java sous Windows.

> 💡 Exemple 1 :  
> [How to read YAML file in Java using Jackson?](https://stackoverflow.com/questions/46525970/how-to-read-yaml-file-in-java-using-jackson)  
> ➜ Nous a permis de comprendre comment utiliser `YAMLFactory` correctement.

> 💡 Exemple 2 :  
> [Jackson support for Java records](https://stackoverflow.com/questions/65188767/jackson-deserialize-record)  
> ➜ Nous a expliqué comment rendre nos `record` compatibles avec Jackson.

> 💡 Exemple 3 :  
> [Gradle Application Plugin mainClass](https://stackoverflow.com/questions/51228231/gradle-application-plugin-mainclass)  
> ➜ Nous a aidés à configurer correctement la classe principale du projet pour l’exécution avec Gradle.

🧠 **Adaptation réalisée :**  
Nous avons ajouté des contrôles de nullité et corrigé la compatibilité YAML :
```java
public record RoverConfiguration(Position position, List<Command> commands) {
    public RoverConfiguration {
        Objects.requireNonNull(position);
        Objects.requireNonNull(commands);
    }
}


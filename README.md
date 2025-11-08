[![CI Pipeline](https://etulab.univ-amu.fr/n24024683/mars-rover-nahili-abdelhakim-kesbi-walid/badges/master/pipeline.svg)](https://etulab.univ-amu.fr/n24024683/mars-rover-nahili-abdelhakim-kesbi-walid/-/pipelines)

# 🚀 Mars Rover Simulator  
---

## 📘 Project Overview  

This project simulates the movement of one or more **rovers** exploring the surface of Mars.  
Each rover is defined by:  
- an **initial position** (`x`, `y`, and orientation),  
- and a **list of commands** (`LEFT`, `RIGHT`, `MOVE`).  

The simulator:  
1. Reads a **YAML configuration file** (`config.yml`)  
2. Executes the rover commands on the grid  
3. Produces an **output YAML file** (`output.yml`) containing:  
   - the final positions and orientations,  
   - destroyed rovers,  
   - and the **percentage of explored surface**.  

---

## 🌍 Main Features  

- Supports **multiple rovers** operating simultaneously.  
- Two grid types:  
  - **Rectangular**: finite grid with boundaries.  
  - **Toroidal**: wraps around like a donut.  
- **Obstacles**: rovers are destroyed upon collision.  
- **Exploration radius**:
  - `0`: default — only visited cells.  
  - `1`: includes adjacent cells.  
  - `2+`: explores all cells within a Manhattan distance.  
- **Simulation observer** for real-time tracking (used for GUI creation).  
- **Final output** includes:  
  - percentage of explored surface,  
  - final state of each rover,  
  - full list of explored coordinates.  

---

## ⚙️ How to Run  

### ✅ Requirements  
- **Java 17** or later  
- **Gradle** installed  
- A valid `config.yml` file placed in the project root  

### ▶️ Run the Simulation  

**1. Build the project:**  
```bash
gradle build
```

**2. Run the simulator:**  
```bash
gradle run
```
The program automatically reads config.yml and generates output.yml in the same directory.

#💡 Summary
This project demonstrates:
* modular Java architecture,
* clean input/output data management using YAML,
* and extensible design suitable for visualization or GUI simulation.
| 🪐 A complete Mars exploration simulator built with clarity, modularity, and robustness in mind.
----

## 👥 Project Members  
- **Abdelhakim NAHILI**  
- **Walid KESBI**  


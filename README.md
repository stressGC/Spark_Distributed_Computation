# FINAL PROJECT - BDDR @ UQAC

This project has been made for the final part of the BDDR course @ UQAC. A demo is accessible [there](https://github.com/stressGC/Spark_Distributed_Computation/blob/master/demo.mp4?raw=true).

## I] Requirements

- Java
- Scala
- Spark

## II] Installation
```
git clone git@github.com:stressGC/Spark_Distributed_Computation.git localName
cd localName
```
## III] Exercice 1

All the files related to the crawler first exercice are in the /crawler folder. The entry point of the crawler is the file Main.java.

The second part of the first exercice is in the inversedIndex folder. Its entry point is the file App.scala.

We built a graphical interface on top of this exercice, it is a ReactJS built component. It is accessible [here](https://github.com/LudoCruchot/BDDRfrontOffice).

## IV] Exercice 2

All the files related to the second exercice are in the /fights folder. Its entry point is the file App.scala.

We only have implemented the first fight since we were lacking some time (we are a team of 2 !). We have used your advises and GraphX functions to do our implementation.

Here are the things we did implement:
- 2D map
- multiple opponents (no limit)
- movements
- attacks based on spells and minimum range
- random computation of the damages dealt
- death check
- end of fight check
- logs in console so we can follow the fight

Here are the things that could be improved:
- optimisation on joins and aggregations
- 3D map
- 1d20 mecanic
- multiple spell per entity
- multiple damage lines per spell
- multiples actions per iteration
- more understandable logs (UI ?)

Since we encountered difficulties, we chose to focus on the Spark side of the problem, and less on the POO part of the problem.

## Authors
[Georges Cosson](https://github.com/stressGC) & [Antoine Demon](https://github.com/LudoCruchot)

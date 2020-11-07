_View the file in markdown_

## Assignment 1
- Name - Aravind Metku
- Id - 2019HT12094


## Chandy Misra Hanss (OR) Algorithm Implementation

### Requirements
#### Java 
- Java version > 8

#### How to run
- Copy the absolute path to cwh config input file (Find the samples in /main/resources with diagrams for each config in sample folder)
- Run the provided jar
    - example: java -jar CMHOR-1.0-SNAPSHOT.jar "<absolute path to the file>"
    
#### Project Structure
- App.java is the main
- com/bits/cmh/DeadlockDetector.java is the starting point for the CMH algorithm

#### Sample Input files
1. Sample 1.png - graph_simple.cmh
1. Sample 2.png - graph_ll_with_dl.cmh
1. Sample 3.png - graph_ll_without_dl.cmh
1. Sample 4.png - graph_ml_with_dl.cmh

#### Constructing New Config Input file
- Process configuration input file (<file_name>.cwh)
    - File format
        - 1: 2,3<br/>2: 3,4
    ##### This means 
    - P1 dependents are P2, P3
    - P2 dependents are P3, P4

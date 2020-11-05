## Assignment 1
- Name - Aravind Metku
- Id - 2019HT12094

## Chandy Misra Hanss (OR) Algorithm Implementation

### Requirements
#### Java 
- Java version 14

#### How to run
- Copy the absolute path to cwh config input file (samples provided)
- Run the provided jar
    - example: java -jar "<absolute path to the file>"
    
#### Project Structure
- App.java is the main
- com/bits/cmh/DeadlockDetector.java is the starting point for the CMH algorithm

#### Config Input file
- Process configuration input file (<file_name>.cwh)
    - File format
        - 1: 2,3<br/>2: 3,4
    ##### This means 
    - P1 dependents are P2, P3
    - P2 dependents are P3, P4

# Company Question

## Requirements
JDK11
Ensure JDK11 bin is in path

## Execution 
mvn install

java -jar company-question-0.0.1-SNAPSHOT.jar {file-name}

### Sample Input File
```input
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300
306,Hello,John,37000,305
307,Hello,Sam,20000,306
```

### Sample Output
```output
---------------All Salary Details in format id,salary,ideal_salary,difference------------------------
307,20000,20000,0
306,37000,30000,-7000
305,34000,36000,2000
300,50000,50000,0
124,45000,60000,15000
125,47000,47000,0
123,60000,64200,4200

---------------Managers Earning Less in format id,salary,ideal_salary,difference-------------------------
305,34000,36000,2000
124,45000,60000,15000
123,60000,64200,4200
---------------Managers Earning Less in format id,salary,ideal_salary,difference-------------------------

---------------Managers Earning High in format id,salary,ideal_salary,difference-------------------------
306,37000,30000,-7000
---------------Managers Earning High in format id,salary,ideal_salary,difference-------------------------

--------Employess having more than 4 Managers between then and CEO in the form id,level,difference
307,6,1

--------Employess having more than 4 Managers between then and CEO in the form id,level,difference
```

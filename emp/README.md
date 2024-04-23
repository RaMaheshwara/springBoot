# springBootProject

**#versions:**
org.springframework.boot' version '3.2.5'
io.spring.dependency-management' version '1.1.4
java:17

**dependencies used:**
org.springframework.boot:spring-boot-starter-data-jpa //for jpa 
org.springframework.boot:spring-boot-starter-web
org.springframework.boot:spring-boot-devtools  //for live runtime changes
com.mysql:mysql-connector-j                    //for connecting to mysql
org.springframework.boot:spring-boot-starter-test
org.json', name: 'json', version: '20140107    //to use json

**project type**
gradle

**structure:**
Created two table employee and department to store specific field;
Employee table stores: employee id(primary key), employee name, amount, joining date, exit date, departmentId
Department table store: department id(primary key), department name
one employee repository to extends feature of jpa repository
one department repository to extends feature of jpa repository;
one controller class where request will land(containing end points)
one service class where business logic is present
one main class which will run the project
configuration of jpa and mysql in application.properties file

**test cases peroformed**

FOR POST API

Case 1: send the given payload in request to save it 
Resp:{"message":"data saved successfully","status":200}
Case 2: if some data is missing
Resp:{"message":"data not found","status":400}

For GET API

Case 1: send the date in request header
Response:{
    "data": [
        {
            "currency": "USD",
            "employees": [
                {
                    "amount": 2500,
                    "empName": "sam"
                },
                {
                    "amount": 700,
                    "empName": "susan"
                }
            ]
        },
        {
            "currency": "INR",
            "employees": [
                {
                    "amount": 3000,
                    "empName": "pratap m"
                },
                {
                    "amount": 5000,
                    "empName": "raj singh"
                }
          ]
        }
    ],
    "errorMessage": ""
}
Case 2: if date is missing
Response:{"message":"No date found for performing operation"}
Case 3: if no data found on the date
Response: {"errorMessage":"No matching record found"}

**My learnings**
what if user send same payload multiple times to encounter that there is no unique key in the payload thats'why I didn't take it into consideration but my suggestion is there must be some unique parameter to prevent that for this I have used empName but it also can get repeated so I commented that code but it is working fine
I have save data in different table to mention the structure of the dB Employee table has its column while department has his own but department table is linked with employee table for various reason for that I have used concept of primary and foreign key.
I have used dev tools also which somehow eases the development process
Used logger also to log the error and responses;
Result of get api is right but not sorted because the dS I used to iterate on result is HashMap(Hash Map is an unordered data structure) 
there is no need to enter same department time multiple times whenever we encounter them for that we have checked whether the department id is present previously so we worked accordingly.

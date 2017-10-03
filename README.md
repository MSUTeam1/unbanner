# unbanner

## Collaboration Team

* Lawrence Brogan (zeonin)
* Nick Barnes (nbarnes7)
* Casey Blanton (cblano7)
* Mahesh (mkbhattarai)
* Harley Dutton (toasterbrain)
* Chris Brodski (brodski)

## How to run

Install Gradle

Install MongoDB

$ mongod

$ ./gradlew bootRun 

open in browser:
http://localhost:8080/

to SEE LIST OF STUDENTS:
http://localhost:8080/students
there is currently no way to get back to the landing page

to CREATE A NEW STUDENT click on " create new student"
htttp://localhost:8080/students/new
then fill in the 900#, first name, and last name fields
click the "create" button. you will be sent back to the list of all students:
http://localhost:8080/students

to VIEW AN INDIVIDUAL STUDENT:
click the "view" button on that on that student's row
you will be taken to a page where you can update or delete the student:
http://localhost:8080/student/<gibberish>

to UPDATE A STUDENT'S INFORMATION
while viewing an individual student...
enter data into the 900#, first name, and last name fields
press the "update" button. you will be sent back to the list of all students:
http://localhost:8080/students

to DELETE A STUDENT'S INFORMATION
while viewing an individual student...
click the delete button. you will be sent back to the student list:
http://localhost:8080/students

to VIEW THE LIST OF COURSES
there is currently no button so you have to type:
http://localhost:8080/courses

to VIEW AND THEN DELETE OR EDIT A COURSE
click on "view" next to that course
you will be taken to:
http://localhost:8080/courses/<gibberish>
There is presently no way back to the course list without deleting or editing
click "delete" to do that
fill in the fields and click "update" to do that
if you delete or update you will be taken back to:
http://localhost:8080/courses/

to CREATE A NEW COURSE:
while on the course list page...
click the "create new course button"
fill in the fields
click "create". you will be sent back to... nevermind this whole thing is broken




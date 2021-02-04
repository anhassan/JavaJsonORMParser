# Introduction & Design
This application takes in a String of JSON Data and will render an output with the following format:
* First row of the output would be the header, it would match the keys found in the JSON
* Also a new column would be added at the end called ***"ageGroup"***
* If a data row does not have the corresponding value for the column, output would be left blank

The data rendered from  the JSON input would have the following features:
* Data would be only be included where ***optIn*** = true
* First character of ***firstName*** and ***lastName*** would capitalized
* The output ***phone number*** would have the following format : *(XXX) XXX-XXXX*

* The output ***"postalCode"*** would be in the format of A#A #A#
* For the ***"birthday"*** column, output only yyyy-MM-dd
* For the ***"ageGroup"*** column, the age would be calculated based on the current date and "birthday" value.

* If there is no birthday - output would be *"Unknown"*
* If Age > 60 - output would be *"Over60"*
* If Age < 20 - output would be *"Under20"*
* Otherwise - output would be  *"20to60"*

# Implementation
-   A *Gson* parser is used to parse the Json string using an object mapper approach by creating a ***POJO***(Plain Old Java Object). 
-   The *toString()* method of the *POJO* is overloaded to create records in accordance to the desired format.
-   ***Regular Expressions*** are used in conjunction with string manipulations to get attributes like postal code, phone number in the required format.
-   In order to increase the efficiency and readability of the code proper ***modularization*** is used along with utility/helper functions.
-   For *optimizing time complexity*, ***Java 8 stream functions*** like ***filter()*** and ***map()*** are used for filtration of records in primary Json file.

# Fess Administration System

The Fees Administration System is a robust Java application designed to streamline fee management processes within educational institutions. Developed using Java Swing for a responsive user interface and leveraging a relational database for data management, the system enhances operational efficiency and data accuracy.

## Key Functionalities
1.	**Login.java**: The entry point for the application, this module facilitates user authentication. Users must log in with valid credentials to access the system's features, ensuring data security and integrity.
2. **Signup_Page.java** : This component allows new users to register by providing necessary details. It securely stores user information in the database, enabling subsequent logins.
3. **home.java** : After successful login, users are directed to the home interface, which provides easy navigation to various functionalities of the system, including adding fees, viewing records, and generating reports.
4. **AddFees.java** : This module enables administrators to enter new fee records into the database. Users can input essential details such as receipt numbers, student information, payment modes, and amounts, ensuring that all financial transactions are recorded accurately.
5. **UpdateFeesDetails.java** : This functionality allows users to modify existing fee records. Administrators can search for specific records and make necessary adjustments to ensure that all information remains current and accurate.
6. **ViewAllRecords.java** : This module provides a comprehensive view of all fee transactions. Users can easily browse through the complete list of records, which includes detailed information on each transaction, facilitating effective record-keeping and audits.
7. **SearchRecord.java** : This feature allows users to search for specific fee records based on various criteria, such as student names or receipt numbers. It significantly improves the efficiency of data retrieval, saving time and effort.
8. **PrintReciept.java** : Users can generate and print receipts for completed fee transactions. Receipts include all relevant details, such as payment mode, student information, and amounts, enhancing transparency and record-keeping.
9. **GenerateReport.java** : This module enables users to generate financial reports based on selected courses and date ranges. It aggregates total fee collections and presents the data in a structured format. The system converts numerical amounts into words for clarity in financial documentation.
10. **EditCourse.java** : This component allows administrators to manage course details within the application. Users can add, edit, or delete courses as necessary, ensuring that the system remains up-to-date with the latest offerings.
11. **DBConnection.java** : This utility class manages the database connection, ensuring secure and reliable interactions with the fees database. It handles all queries related to fee records, ensuring data consistency and integrity throughout the application.


## Problem Statement

Managing student fees in educational institutions is often cumbersome and prone to errors due to manual methods like paper records and spreadsheets. These traditional approaches lead to inefficiencies, data inaccuracies, lack of transparency, difficulty in generating reports, and ineffective communication. 

A comprehensive Fees Administration System can automate fee collection, record-keeping, reporting, and communication, addressing these challenges. By using technology, it ensures accuracy, efficiency, and transparency, improving administrative operations and user satisfaction while maintaining reliable financial records.

## Output

Login Page:

<p align="center">
<img width="600" height="350" src="Images/1.png ">
</p>
                            
SignUp Page:

<p align="center">
<img width="600" height="350" src="Images/2.png ">
</p>
                                                               
Home Page:

<p align="center">
<img width="600" height="350" src="Images/3.png ">
</p>
                              
Add Fees Module:   
                             
<p align="center">
<img width="600" height="350" src="Images/4.png ">
</p>

Update Fees Module:      
                                   
<p align="center">
<img width="600" height="350" src="Images/5.png ">
</p>

Edit Course Details:

<p align="center">
<img width="600" height="350" src="Images/6.png ">
</p>
                                          
Search Record:

<p align="center">
<img width="600" height="350" src="Images/7.jpg ">
</p>

Generate Report:

<p align="center">
<img width="600" height="350" src="Images/7.jpg ">
</p>

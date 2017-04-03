Hello.
1. at first to create user with role admin via WEB UI you should edit SecurityConfiguretion class as described in comments in it.
2. after creation of admin user you'll be able to create all other users so it would be better to change SecurityConfiguration back again.
3. all type of users can see only particular pages and hence do only particular things.
4. please fill users and deliveryAddresses and Companies etc to see full functional of a project.
5. plese create DataBase as described in file logistic.sql (it's dump from MySQL Workbench)
6. please fill list of equipment from excel file (one item in separate cell)
7. for user type "warehouse" to close order he/she should upload file with equipment identifiers (for example serial number) with neccessary quantity (as set in order). Backend sould check if these identifiers are now in store.
8. to create project i used Intellij/gradle/ web project 

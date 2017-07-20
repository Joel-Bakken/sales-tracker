# _Sales Tracker Management Software_

#### _Brief Description: Program executes various tasks relating to tracking product inventory and customers, July 20, 2017_

#### By _**Mara Timberlake & Joel Bakken**_

## Description
_This program, used to execute various business-related tasks, includes:_
* _Lists out all of the business's customers, selecting a customer, and see a list of all products that belong to that customer._
* _Adds new customers, and new products to a specific customer._
* _Updates customer's and product's details._
* _Delete a customer, and delete their products._
* _Delete a product._

## Specifications
* _Program displays homepage details of the business_
* _User can view all listed customers and products, and add or delete a member from either category._
* _Displays the updated information upon submission._
* _Example:_

|Behavior|Input|Output|
|---|---|---|
|Add New Product|Input: Milk - $3|Output: Milk - $3|

## What's included
Within the repository you'll find the following directories and files:
```
sales-tracker/
├── src/
│    └── main/
│    │    └── java/
|    │    │     └── App.java
|    │    │     └── Customer.java
|    │    │     └── DB.java
|    │    │     └── Product.java
|    │    │     └── VelocityTemplateEngine.java
|    |    └── resources/
|    |          └──templates/
|    |               └──customer-form.vtl
|    |               └──customer-product-form.vtl
|    |               └──customer-product-success.vtl
|    |               └──customer-success.vtl
|    |               └──customer.vtl
|    |               └──customers.vtl
|    |               └──delete-success.vtl
|    |               └──index.vtl
|    |               └──layout.vtl
|    |               └──product-form.vtl
|    |               └──product.vtl
|    |               └──products.vtl
|    |               └──customer.vtl
|    └── test/
│         └── java/
|               └── CustomerTest.java
|               └── DatabaseRule.java
|               └── ProductTest.java
├── .gitignore
├── build.gradle
└── README.md
```

## Setup/Installation Requirements

* _Install gradle on your device_
* _LOCAL: Go to Terminal_
* _Clone this repository:_
```
$ cd ~/Desktop
$ git clone https://github.com/Littlejoel29/sales-tracker.git
$ cd sales-tracker
```
* _Run Postgres with terminal command:_
```
$ postgres
```
* _Open a new tab in terminal by pressing [command ⌘] + [T]_
* _In the new tab, create 'sales_tracker' database:_
```
$ psql
$ CREATE DATABASE sales_tracker;
$ \c sales_tracker;
$ CREATE TABLE customers (id SERIAL PRIMARY KEY, name varchar);
$ CREATE TABLE products (id SERIAL PRIMARY KEY, name varchar, cost int, customerId int);
$ CREATE DATABASE sales_tracker_test WITH TEMPLATE sales_tracker;

```
* _Return to original tab where repository was cloned and run gradle:_
```
$gradle run
```
* _Open browser window:_
```
localhost:4567
```

## Known Bugs

_No known bugs at this time_

## Support and contact details

_For questions or feedback, please notify Mara Timberlake at maratimberlake@msn.com, or Joel Bakken at jkbetc@gmail.com_

## Technologies Used

_Languages used include Java. IDE used - Atom_

### License

*This software runs under the MIT license*

Copyright (c) 2017 **_Mara Timberlake & Joel Bakken_**

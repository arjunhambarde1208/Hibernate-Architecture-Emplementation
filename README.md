	Introduction
This Proof of Concept (POC) aims to demonstrate Hibernate's architecture and its key components in a Java-based application. Hibernate is a powerful Object-Relational Mapping (ORM) framework that simplifies database interactions by eliminating the need for manual SQL queries and JDBC operations. Through this POC, we will showcase how Hibernate manages database transactions efficiently using ORM principles.
1. Hibernate
Hibernate is an ORM (Object-Relational Mapping) framework for Java that simplifies database interactions by mapping Java objects to database tables. It abstracts the complexities of JDBC, allowing developers to perform database operations without writing extensive SQL queries.

2. Hibernate Architecture
The Hibernate architecture consists of several components:
a. Configuration
•	It loads the configuration settings from hibernate.cfg.xml or hibernate.properties file.
•	Contains database connection details and Hibernate properties.
b. SessionFactory
•	It is a heavyweight object created once per application and provides Session objects.
•	It is built using the configuration file.
c. Session
•	A lightweight object created for every transaction.
•	It is used to interact with the database, fetch data, and perform CRUD operations.
d. Transaction
•	Manages database transactions (commit, rollback).
•	Hibernate uses JDBC transactions or JTA for transaction management.
e. Query (HQL/SQL)
•	Hibernate Query Language (HQL) is used to fetch and manipulate data in an object-oriented manner.
•	Native SQL queries can also be executed.
f. Criteria API
•	Provides an alternative to HQL for programmatic query construction.

g. Hibernate Annotations & Entity Manager
•	Hibernate supports JPA (Java Persistence API) annotations for defining entity mappings.

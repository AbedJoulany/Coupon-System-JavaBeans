# Coupon-System-JavaBeans
Building the core of the System  OOP, Java Beans, DAO, JDBC, Threads

# Description
At this stage, the database for storing and retrieving information about customers, companies and coupons will be defined. An isolation layer called DAO (Data Access Objects) will be built over the database, which will allow easy work from Java to the database.
In addition, basic infrastructure services will be established, such as a database of links to the database (ConnectionPool) and a daily job that maintains the system and cleans it of expired coupons.
Three Entry Points will be defined for the system for each of the system's client types - administrator, company and client, which will be connected by logging in

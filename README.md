mongodb-jndi-datasource
=======================
**A JNDI pooled datasource for MongoDB**

Tested on Tomcat 6 but should work fine on other Java servers with some configuration changes (JDNI datasource declaration).

**Main changes**
- v5.0 now the resource is defined using the standard format of the MongoDB connection URI used to connect to a MongoDB database server. 
- v4.0 now is possible to choose the Authentication Mechanism (SCRAM-SHA-1 or MONGODB-CR). Default as SCRAM-SHA-1
- v3.0 now returns `com.mongodb.client.MongoDatabase` instead of `com.mongodb.DB` (deprecated)
- v2.0 uses MongoDB java client v3.0.0
- v1.0 uses MongoDB java client v2.12.2

##Deploy instructions for Tomcat 6:
These instructions explain how to bind the datasource (DS) to the Bonita web application.

1. Obtain the Mongo DB client JAR file from here (make sure the version match with the project, see above): http://docs.mongodb.org/ecosystem/drivers/java/ 
2. Obtain this project's JAR file or build it using Maven
3. Place the 2 aforementioned JAR files in this directory: `TOMCAT_HOME/lib/`
4. Edit this file (for binding the DS to Bonita): `TOMCAT_HOME/conf/Catalina/localhost/bonita.xml`
5. Add this block of XML somewhere in the `Context` tag

``` XML
<Resource name="testMongodbDS"
	auth="Container"
	type="com.mongodb.client.MongoDatabase"
	factory="org.mongodb.datasource.MongoDatasourceFactory"	
	connectionString="mongodb://host:27017,host2:27017/?replicaSet=rs0"	
	databaseName="my-database"	 
/>
```

**Note:** Remember to update these settings to match your MongoDB settings. The 'databaseName' is the database component to indicate which database to work with by default. 


##Usage instructions:
Once you have deployed the datasource on your server, you may call it with the following Java code:

``` Java
// Retrieve connection from datasource
Context initCtx = new InitialContext();
MongoDatabase db = (MongoDatabase) initCtx.lookup("java:/comp/env/testMongodbDS");
// Perform a query
db.getCollectionNames();
```

Note that the object returned from the datasource is a [com.mongodb.client.MongoDatabase](http://api.mongodb.org/java/3.0/com/mongodb/client/MongoDatabase.html)

##Troubleshooting datasource deployment:
Datasource deployment can be troubleshooted by setting this logging level in your server:
```
org.mongodb.datasource.level = FINE
```

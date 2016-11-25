package org.mongodb.datasource;

import javax.naming.RefAddr;
import javax.naming.Reference;

import com.mongodb.MongoException;

public class MongoDatasourceConfiguration {
	
	// Connection URL
	private static final String PROP_CONNECTION_STRING = "connectionString";
	
	// Retrives a connection from this datasource against this database
	private static final String PROP_DATABASE_NAME = "databaseName";
	
	/** 
	 * <p>The standard format of the MongoDB connection URI used to connect to a MongoDB 
	 * database server. The format is the same for all official MongoDB drivers.</p>
	 * 
	 * <pre>mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]</pre>
	 * 
	 */
	private String connectionString = null;
	
	private String databaseName = null;
	 
	
	public static MongoDatasourceConfiguration loadFromJNDIReference(final Reference reference) throws Exception {
		
		final MongoDatasourceConfiguration config = new MongoDatasourceConfiguration();

		// Server settings		
		String connString = getReferenceValue(reference, PROP_CONNECTION_STRING);
		if (!isEmptyValue(connString)) {
			config.connectionString = connString;
		} else {
			 throw new MongoException("Could not locate '" + PROP_CONNECTION_STRING + "' in either environment or obj");		      
		}

		// Database settings
		String databaseName = getReferenceValue(reference, PROP_DATABASE_NAME);
		if (!isEmptyValue(databaseName)) {
			config.databaseName = databaseName;
		} else {
			 throw new MongoException("Could not locate '" + PROP_DATABASE_NAME + "' in either environment or obj");		      
		}
		
		return config;
	}
  
	private static String getReferenceValue(final Reference reference, final String key) {
		RefAddr property = reference.get(key);
		return property == null ? null : (String) property.getContent();
	}

	private static boolean isEmptyValue(final String value) {
		return (value == null) || (value.trim().isEmpty());
	}

	public String getConnectionString() {
		return connectionString;
	}
	
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}

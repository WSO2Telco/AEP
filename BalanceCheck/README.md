## 1.0 Configuration Setup

Folder path:	/deploy/config.yml
 

### 1.0.1 Redis Configuration 

 - port 		: redis port
 - host 		: redis host
 - password 	: redis password
 - timeout 		: redis timeout
 - poolsize 	: redis connection pool size

### 1.0.2 Server Configuration


- applicationConnectors: 
A set of connectors which will handle application requests. 

- adminConnectors: 
A set of connectors which will handle admin requests. 

- port: 
TCP/IP port on which to listen for incoming connections.
Built-in default is an HTTP connector listening on port 8181, override if needed

- bindHost: 
The hostname to bind to.
 

### 1.0.3 Log configuration  
  

- Level:

Logback logging level. ‘INFO’ Designates informational messages that highlight the progress of the application at coarse-grained level. 

- Loggers:

io.dropwizard for INFO

com.wso2telco for DEBUG

- Appenders:

Locations to where the logging messages should be displayed or written

-- type:
Console / File

-- threshold: 
The lowest level of events to print to the console/ write to the file.

-- timeZone: 
The time zone to which event timestamps will be converted.
 
-- target: 
The name of the standard stream to which events will be written.
Can be stdout or stderr. 

-- currentLogFilename: 
The filename where current events are logged.

-- archive: 
Whether or not to archive old events in separate files.
  
-- archivedLogFilenamePattern: 
Required if archive is true.The filename pattern for archived files.%d is replaced with the date in yyyy-MM-dd format, and the fact that it ends with.gz indicates the file will be gzipped as it’s archived.Likewise, filename patterns which end in .zip will be filled as they are archived.

-- archivedFileCount: 
The number of archived files to keep.
Must be between 1 and 50. 

-- logFormat: 
Logback pattern with which events will be formatted.  


## 4. Build the Service

Run the following Maven command. This will create the fat jar BalanceCheck-1.0.jar in the target directory.

```
mvn clean install
```

This fat jar is a jar file that contains token pool microservice as well as all its dependencies.

## 5. Run the Service

In order to get the service up and running, execute the following command.

```
java -jar target/BalanceCheck-1.0.jar server deploy/config.yml
```

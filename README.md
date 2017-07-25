# EnterpriseServiceBus
Service Bus which enables multiple services to interact with each other. Implemented using java sockets, Java Native Interface (JNI) and jython library. Service Bus can interact with java, C++ and python. 


## High-Level Architecture
![architecture](https://github.com/heshanera/EnterpriseServiceBus/blob/master/imgs/ServiceBus.png)


## How to Run

***ServiceBus uses the port '5001'. Please free the port before starting the application.

### Go to the 'Demo' directory
        > cd Demo
### Starting the serice bus:
        > java -jar ServiceBus.jar
### Starting the client/s:
        > java -jar Client.jar

## Adding a Service
	1. Adding the configuration parameter of the service in 'EsbConfiguration.xml' 

	### Java
		2. Add the service to '/ServiceBus/src/services/javaServices'
		3. Add the service info to '/ServiceBus/src/services/JavaServiceDirectory.java'
		4. Compile
	
	### Python
		2. Add the service to '/ServiceBus/src/services/PythonServices'
		3. Add the service info to '/ServiceBus/src/services/PythonServiceDirectory.java'
		4. Compile

	### C++
		2. Add the service to '/ServiceBus/src/services/CppServices'
		3. Add the service info to '/ServiceBus/src/services/CppServices/CppServiceDirectory.cpp'
		4. Compile

### Commands
	man: return the list of commands
	man <serviceName>: return the service info
	list: return the list of services
	exit: disconnect from the Service Bus

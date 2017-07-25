# EnterpriseServiceBus
Service Bus which enables multiple services to interact with each other. Implemented using java sockets, Java Native Interface (JNI) and jython library. Service Bus can interact with java, C++ and python. 


## High-Level Architecture
![architecture](https://github.com/heshanera/EnterpriseServiceBus/blob/master/imgs/ServiceBus.png)


&nbsp;&nbsp;
## How to Run

##### Go to the 'Demo' directory
        > cd Demo
##### Starting the serice bus:
        > java -jar ServiceBus.jar
##### Starting the client/s:
        > java -jar Client.jar


&nbsp;&nbsp;
## Adding a Service

- Adding the configuration parameters of the service to 'EsbConfiguration.xml'

- #### Java
	- Add the service to '/ServiceBus/src/services/javaServices'
	- Add the service info to '/ServiceBus/src/services/JavaServiceDirectory.java'
	- Compile

- #### Python
	- Add the service to '/ServiceBus/src/services/PythonServices'
	- Add the service info to '/ServiceBus/src/services/PythonServiceDirectory.java'
	- Compile

- #### C++
	- Add the service to '/ServiceBus/src/services/CppServices'
	- Add the service info to '/ServiceBus/src/services/CppServices/CppServiceDirectory.cpp'
	- Compile

&nbsp;&nbsp;
## Commands

| Command       | Description   |
| ------------- |:-------------:|
| > man | return the list of commands |
| > man <serviceName\> | return the service info |
| > list | return the list of services  |
| > exit | disconnect from the Service Bus |
| > <serviceName\> <param1\> ... | calling a service |
	

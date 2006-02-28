@ECHO OFF
set LIBPATH=bin;lib\axis-schema.jar;lib\axis.jar;lib\commons-codec-1.3.jar;lib\commons-discovery-0.2.jar;lib\commons-logging-1.0.4.jar;lib\jaxrpc.jar;lib\jcommon-1.0.0.jar;lib\jfreechart-1.0.0.jar;lib\log4j-1.2.8.jar;lib\saaj.jar;lib\wsdl4j-1.5.1.jar;lib\xercesImpl.jar
set JAVA_HOME=c:\jdk1.5.0_05\
%JAVA_HOME%\bin\java -cp %LIBPATH% org.apache.axis.wsdl.client.AdminClient src\interfaz_remota\deployAdmin.wsdd
%JAVA_HOME%\bin\java -cp %LIBPATH% org.apache.axis.wsdl.client.AdminClient src\interfaz_remota\deployMsg.wsdd
%JAVA_HOME%\bin\java -cp %LIBPATH% org.apache.axis.wsdl.client.AdminClient src\interfaz_remota\deployStoxtreme.wsdd
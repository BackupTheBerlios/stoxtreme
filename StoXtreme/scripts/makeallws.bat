@ECHO OFF
set LIBPATH=bin;lib\axis-schema.jar;lib\axis.jar;lib\commons-codec-1.3.jar;lib\commons-discovery-0.2.jar;lib\commons-logging-1.0.4.jar;lib\jaxrpc.jar;lib\jcommon-1.0.0.jar;lib\jfreechart-1.0.0.jar;lib\log4j-1.2.8.jar;lib\saaj.jar;lib\wsdl4j-1.5.1.jar;lib\xercesImpl.jar
set JAVA_HOME=c:\jdk1.5.0_05\
rem GENERA LOS WSDL
set JAVA=%JAVA_HOME%\bin\java -cp %LIBPATH% 

%JAVA% org.apache.axis.wsdl.Java2WSDL -o admin.wsdl -l"http://localhost:8080/axis/services/StoXtremeAdmin" stoxtreme.interfaz_remota.Administrador
%JAVA% org.apache.axis.wsdl.Java2WSDL -o mensajes.wsdl -l"http://localhost:8080/axis/services/StoXtremeMsg" stoxtreme.interfaz_remota.StoxtremeMensajes
%JAVA% org.apache.axis.wsdl.Java2WSDL -o stoxtreme.wsdl -l"http://localhost:8080/axis/services/StoXtreme" stoxtreme.interfaz_remota.Stoxtreme

%JAVA% org.apache.axis.wsdl.WSDL2Java -v -o src -d Application -s -S true admin.wsdl
RENAME src\stoxtreme\interfaz_remota\deploy.wsdd deployAdmin.wsdd
RENAME src\stoxtreme\interfaz_remota\undeploy.wsdd undeployAdmin.wsdd
%JAVA% org.apache.axis.wsdl.WSDL2Java -v -o src -d Application -s -S true mensajes.wsdl
RENAME src\stoxtreme\interfaz_remota\deploy.wsdd deployMsg.wsdd
RENAME src\stoxtreme\interfaz_remota\undeploy.wsdd undeployMsg.wsdd
%JAVA% org.apache.axis.wsdl.WSDL2Java -v -o src -d Application -s -S true stoxtreme.wsdl
RENAME src\stoxtreme\interfaz_remota\deploy.wsdd deployStoxtreme.wsdd
RENAME src\stoxtreme\interfaz_remota\undeploy.wsdd undeployStoxtreme.wsdd
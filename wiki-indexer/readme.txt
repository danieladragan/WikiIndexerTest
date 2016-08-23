/*
 * $Author: aancuta, vsima
 * $Revision: 1.0 $
 * $Date: 2016/08/19 $
 *
 * ====================================================================
 *
 * Copyright 
 *
 * This program is Copyright Endava 2016. All rights reserved
*/


1. Table of Contents
==================================================================================

1. Table of Contents
2. Download
3. Requirements
4. Introduction
5. RUN IN AN IDE
6. Problems 



2.Download
==================================================================================

See the Wiki project page to download this release.

	https://github.com/wk-adriana/wiki-indexer


3.Requirements
==================================================================================

IntelliJ IDEA 2016.2 - https://www.jetbrains.com/idea/download/#section=windows
Apache Maven 3.3.9 - https://maven.apache.org/download.cgi
Maven  Environment Setup  -http://www.tutorialspoint.com/maven/maven_environment_setup.htm
MySQL 5.7 - https://dev.mysql.com/downloads/mysql/
Tomcat 8.5.4 - http://tomcat.apache.org/download-80.cgi 
Nodejs - https://nodejs.org/en/ 

4.Introduction
==================================================================================

Wiki aplication is developed by using the Spring Framework and also the Spring Boot
 
Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run". 
We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. 
Most Spring Boot applications need very little Spring configuration.

The main role of the application is to find the most ten frequent word from an article,
this words must be shown in a bar-chart,this can be done by receiving a title from the user or by uploading a file with multiple title .
The result of the ten most frequent word will be the aggregation of the all the article that have title in that file .


5.RUN IN AN IDE
==================================================================================

If you want to run the examples in an IDE, such as Intelij, you should
download the contents https://github.com/wk-adriana/wiki-indexer unzip and import the project 
after you should wait for all the dependendency to be loaded .
Press Run (On top right) - WikiIndexerAplication
The file that will be uploaded will be put in the resources folder, also the file must be a .txt file 

6.Problems 
==================================================================================

If you encounter any problems don't hesitate to ask us at :
Adriana.Ancuta@endava.com
Viorel.Sima@endava.com

1.	No UI, cautarea unui singur titlu si afisarea rezultatelor la consola Adriana Ancuta
2.	Salvarea rezultatelor in baza de date si caching de acolo Sima Viorel
3.	Cautarea a mai multe titluri, folosind fisier de pe local (single thread) Adriana Ancuta
4.	3 + multiple threads Adriana Ancuta
5.	Integrare UI, cu afisarea rezultatelor in mod text si fisier de pe local in cazul modului batch Adriana Ancuta
6.	5 + componenta upload fisier Sima Viorel
7.	Full UI, cu afisare bar chart Adriana Ancuta
8.	Executor pattern folosit pentru search bazat pe fisier de intrare (handling al threadurilor mai frumos) Adriana Ancuta

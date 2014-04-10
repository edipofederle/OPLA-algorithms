## Run Before mvn install


### Install dependency on local maven repo
	donwload jar from: https://dl.dropboxusercontent.com/s/rw3sgyd4rgc4kgw/opla-tool-0.0.1.jar?dl=1&token_hash=AAG_0vw6NdXwOC8DOGthZVXfNyka_g9oTdZv-vJq_AV3mA

	mvn install:install-file -Dfile=opla-tool-0.0.1.jar -DgroupId=ufpr.br -DartifactId=opla-tool -Dversion=0.1 -Dpackaging=jar

Your pom.xml already include bellow lines.

	<dependency>
		<groupId>ufpr.br</groupId>
		<artifactId>opla-tool</artifactId>
		<version>0.1</version>
	</dependency>

	rm arch-0.0.1-SNAPSHOT.jar (optinal)

	### Mvn Install

	mvn install


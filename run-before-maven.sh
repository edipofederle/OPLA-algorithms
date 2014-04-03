curl -O https://dl.dropboxusercontent.com/s/nl74m3xfumua16a/arch-0.0.1-SNAPSHOT.jar?dl=1&token_hash=AAG7LQjRp046uL37Yb6h_s0NU-chr6nXmpQqv1BKkblx4Q

mvn install:install-file -Dfile=arch-0.0.1-SNAPSHOT.jar -DgroupId=ufpr.br -DartifactId=opla-tool -Dversion=0.1 -Dpackaging=jar


echo ""
echo "ADD to pom.xml"
echo ""
 
cat  << EOF
<dependency>
<groupId>ufpr.br</groupId>
<artifactId>opla-tool</artifactId>
<version>0.1</version>
</dependency>
EOF

rm arch-0.0.1-SNAPSHOT.jar
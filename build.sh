#!/bin/bash

# Build and containerize this Spring Boot web application with Maven & Docker.
# Chris Joakim, 2020/01/07

source app-env.sh 

arg_count=$#

display_help() {
    echo "script options:"
    echo "  ./build-mvn.sh           (default: does mvn compile, package, and docker build)"
    echo "  ./build-mvn.sh compile"
    echo "  ./build-mvn.sh package"
    echo "  ./build-mvn.sh mvn_tree"
    echo "  ./build-mvn.sh mvn_classpath"
}

mvn_clean_compile() {
    echo "mvn clean compile..."
    mvn clean compile
}

mvn_package_app() {
    rm $COMPILED_JARFILE
    create_build_resources
    echo "mvn clean compile package..."
    mvn clean compile package -P sql
}

create_build_resources() {
    echo 'create_build_resources'
    date -u > src/main/resources/build_date.txt
    whoami  > src/main/resources/build_user.txt
    cat src/main/resources/build_date.txt
    cat src/main/resources/build_user.txt
}

if [ $arg_count -gt 0 ]
then
    if [ $1 == "help" ] 
    then
        display_help
    fi

    if [ $1 == "compile" ] 
    then
        mvn_clean_compile
    fi

    if [ $1 == "package" ]
    then
        mvn_package_app  
    fi

    if [ $1 == "mvn_tree" ]
    then
        echo "mvn dependency:tree"
        mvn dependency:tree
    fi

    if [ $1 == "mvn_classpath" ]
    then
        echo "mvn dependency:build-classpath"
        mvn dependency:build-classpath > mvn-build-classpath.txt
        cat mvn-build-classpath.txt | tr ":" "\n" > mvn-build-classpath-lines.txt
        cat mvn-build-classpath-lines.txt | grep repository | sort 
        rm mvn-build-classpath*
    fi
else
    mvn_package_app

    if [ -f "$COMPILED_JARFILE" ]; then
        echo 'copying compiled jar file to '$CONTAINER_JARFILE
        cp $COMPILED_JARFILE $CONTAINER_JARFILE
        ls -al target/ | grep jar$ 

        echo ''
        echo 'building container: '$DOCKERHUB_CONTAINER_NAME
        docker build -t $DOCKERHUB_CONTAINER_NAME . 

        # echo ''
        # echo 'listing local image: '$DOCKERHUB_CONTAINER_NAME
        # docker images | grep $DOCKERHUB_CONTAINER_NAME
    else
        echo 'container not created due to compilation errors'
    fi
fi

echo 'done'

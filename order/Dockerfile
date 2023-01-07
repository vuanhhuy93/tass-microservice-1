FROM openjdk:11.0.15-jdk
MAINTAINER vuanhhuy93@gmail.com

RUN mkdir /opt/bin
RUN mkdir /opt/bin/config
WORKDIR /opt/bin

ADD build/libs/order-1.0.jar /opt/bin/order-1.0.jar

ADD shell-docker.sh /opt/bin
ADD config/* /opt/bin/config
RUN chmod +x shell-docker.sh

CMD ["./shell-docker.sh"]


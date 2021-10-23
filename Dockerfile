FROM centos:7

LABEL maintainer 'Marcos Estevam <estevamdf@gmail.com>'

ARG JDK_DOWNLOAD=jdk-8u231-linux-x64.rpm
ENV LANG="pt_BR.UTF-8"
ENV JAVA_HOME=/usr/java/jdk-8u231

RUN curl -L https://github.com/hmsjy2017/get-jdk/releases/download/v8u231/${JDK_DOWNLOAD} \
         -o /tmp/${JDK_DOWNLOAD} \
 && rpm -ivh /tmp/${JDK_DOWNLOAD} \
 && rm -rf /tmp/* /*.rpm \
 && ln -fs /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime

COPY target/generator-*jar-with-dependencies.jar /opt/telemetry/generator.jar
WORKDIR /opt/telemetry
CMD ["/usr/bin/java","-jar","telemetry.jar"]

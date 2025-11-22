# 1. 基础镜像 (建议使用精简版 JDK)
FROM openjdk:17-jdk-slim

# 2. 维护者信息
LABEL maintainer="mj"

# 3. 挂载目录 (Tmp 目录，Tomcat 需要)
VOLUME /tmp

# 4. 接收参数 (Jar 包名字)
ARG JAR_FILE=target/*.jar

# 5. 复制 Jar 包
COPY ${JAR_FILE} auth.jar

# 6. 启动命令 (注意：这里不写死 JVM 参数，而是引用环境变量)
# 这样可以在 K8s Yaml 里动态调整内存
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /auth.jar"]
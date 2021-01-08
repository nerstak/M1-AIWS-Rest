FROM jboss/wildfly:21.0.0.Final

ENV DB_HOST wildfly-postgres
ENV DB_PORT 5432
ENV DB_NAME st2eedb
ENV DB_USER adm
ENV DB_PASS adm
ENV DS_NAME Project
ENV JNDI_NAME java:jboss/datasources/Project

USER root

ADD https://jdbc.postgresql.org/download/postgresql-42.2.4.jar /tmp/postgresql-42.2.4.jar

WORKDIR /tmp
COPY input_files/wildfly-command.sh ./
COPY input_files/module-install.cli ./
# search and replace
RUN sed -i -e 's/\r$//' ./wildfly-command.sh
RUN chmod +x ./wildfly-command.sh
RUN ./wildfly-command.sh \
    &&  rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/

# Create Wildfly admin user
RUN $JBOSS_HOME/bin/add-user.sh admin admin --silent

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
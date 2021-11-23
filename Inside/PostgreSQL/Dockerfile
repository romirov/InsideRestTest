# Use the official image as a parent image.
FROM debian:bullseye

RUN apt-get update \
    && apt-get install -y locales postgresql postgresql-client postgresql-contrib vim openssh-server \
    && rm -rf /var/lib/apt/lists/* \
    && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8 

ENV LANG en_US.utf8
#Run the rest of the commands as the `inside` user created by the `postgres-13` package when it was ``apt-get installed``
USER postgres

# Create a PostgreSQL role named ``docker`` with ``docker`` as the password and
# then create a database `docker` owned by the ``docker`` role.
# Note: here we use ``&&\`` to run commands one after the other - the ``\``
#       allows the RUN command to span multiple lines.
COPY init.sql /usr/src/
RUN    /etc/init.d/postgresql start\
    #&& psql --command "CREATE USER inside WITH SUPERUSER PASSWORD 'Euxc1Qz44tl85bxt';" \
    #&& psql --command "CREATE DATABASE insidedb;" \ 
    #&& psql --command "GRANT ALL PRIVILEGES ON DATABASE insidedb TO inside;" \ 
    && psql -f /usr/src/init.sql \
    && /etc/init.d/postgresql stop

# Adjust PostgreSQL configuration so that remote connections to the
# database are possible.
RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/13/main/pg_hba.conf

# And add ``listen_addresses`` to ``/etc/postgresql/main/postgresql.conf``
RUN echo "listen_addresses='*'" >> /etc/postgresql/13/main/postgresql.conf

# Expose the PostgreSQL port
EXPOSE 5432

# Add VOLUMEs to allow backup of config, logs and databases
#VOLUME  ["/etc/postgresql", "/var/log/postgresql", "/var/lib/postgresql"]

# Set the default command to run when starting the container
CMD ["/usr/lib/postgresql/13/bin/postgres", "-D", "/var/lib/postgresql/13/main", "-c", "config_file=/etc/postgresql/13/main/postgresql.conf"]


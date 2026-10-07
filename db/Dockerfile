# Use latest MySQL server
FROM mysql/mysql-server:latest

# Set working directory
WORKDIR /tmp

# Copy SQL files for initial data
COPY world_db/*.sql /tmp/
COPY world_db/*.dump /tmp/
COPY world_db/world.sql /docker-entrypoint-initdb.d/

# Set root password and create root@% for remote access
ENV MYSQL_ROOT_PASSWORD=rootpsw
ENV MYSQL_ROOT_HOST=%

# Add remote access grant script (executed on container startup)
COPY remote-access.sql /docker-entrypoint-initdb.d/
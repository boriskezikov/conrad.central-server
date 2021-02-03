-- schema owner
CREATE USER oscentral WITH password 'oscentral';

CREATE DATABASE "os_central_db" WITH OWNER = postgres ENCODING = 'UTF8';

\connect "os_central_db";

-- schema user
CREATE USER oscentral_user WITH password 'oscentral_user';
CREATE USER oscentral_read WITH password 'oscentral_read';

-- create schema
CREATE SCHEMA oscentral AUTHORIZATION oscentral;

-- add-privileges
GRANT USAGE ON SCHEMA oscentral TO oscentral_user;
GRANT USAGE ON SCHEMA oscentral TO oscentral_read;

ALTER DEFAULT PRIVILEGES FOR USER oscentral IN SCHEMA oscentral GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO oscentral_user;
ALTER DEFAULT PRIVILEGES FOR USER oscentral IN SCHEMA oscentral GRANT USAGE ON SEQUENCES TO oscentral_user;
ALTER DEFAULT PRIVILEGES FOR USER oscentral IN SCHEMA oscentral GRANT EXECUTE ON FUNCTIONS TO oscentral_user;
ALTER DEFAULT PRIVILEGES FOR USER oscentral IN SCHEMA oscentral GRANT SELECT ON TABLES TO oscentral_read;

-- add-extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
ALTER EXTENSION "uuid-ossp" SET SCHEMA oscentral
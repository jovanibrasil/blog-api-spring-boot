SELECT 'CREATE DATABASE blogdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'blogdb')\gexec

SELECT 'CREATE DATABASE usersdb'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usersdb')\gexec

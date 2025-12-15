CREATE DATABASE mini_football_db;

\c mini_football_db;

CREATE USER mini_football_db_manager WITH PASSWORD '123456';

GRANT ALL ON SCHEMA public TO mini_football_db_manager;

GRANT ALL PRIVILEGES ON DATABASE mini_football_db TO mini_football_db_manager;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO mini_football_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO mini_football_db_manager;

ALTER TABLE Player OWNER TO mini_football_db_manager;
ALTER TABLE Team OWNER TO mini_football_db_manager;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO mini_football_db_manager;

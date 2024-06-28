# User Accounts Management System



## PostgreSQL - main database
```
CREATE DATABASE account_management
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Polish_Poland.1250'
    LC_CTYPE = 'Polish_Poland.1250'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE public.account
(
    id_acc SERIAL,
    username character varying(255) NOT NULL,	
    password character varying(255) NOT NULL,
    gender character varying(1) NOT NULL,
    age integer NOT NULL,
    creation_timestamp timestamp with time zone NOT NULL,
    PRIMARY KEY (id_acc)
);

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;
```


### PostgreSQL - test database
```
CREATE DATABASE account_management_test
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Polish_Poland.1250'
    LC_CTYPE = 'Polish_Poland.1250'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE public.account
(
    id_acc SERIAL,
    username character varying(255) NOT NULL,	
    password character varying(255) NOT NULL,
    gender character varying(1) NOT NULL,
    age integer NOT NULL,
    creation_timestamp timestamp with time zone NOT NULL,
    PRIMARY KEY (id_acc)
);

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;
```

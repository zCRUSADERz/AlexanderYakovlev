CREATE DATABASE "ItemsTracker"
  WITH
  ENCODING = 'UTF8';

CREATE TABLE roles (
  role_name character varying PRIMARY KEY
);

CREATE TABLE rules (
  rule_name character varying PRIMARY KEY
);

CREATE TABLE rules_for_role (
  role_name character varying REFERENCES roles,
  rule_name character varying REFERENCES rules,
  PRIMARY KEY (role_name, rule_name)
);

CREATE TABLE users (
  user_id serial PRIMARY KEY,
  login character varying(50) NOT NULL UNIQUE,
  password character varying(50) NOT NULL,
  registration_time timestamp NOT NULL DEFAULT now(),
  role_name character varying REFERENCES roles
);

CREATE TABLE categories (
  category_name character varying(50) PRIMARY KEY
);

CREATE TABLE item_states (
  state_name character varying(50) PRIMARY KEY
);

CREATE TABLE items (
  item_id serial PRIMARY KEY,
  name character varying(50) NOT NULL,
  description text,
  user_id integer REFERENCES users,
  category_name character varying(50) REFERENCES categories,
  state_name character varying(50) REFERENCES  item_states
);

CREATE TABLE comments (
  description text PRIMARY KEY,
  item_id integer REFERENCES items
);

CREATE TABLE attachs (
  file_path character varying PRIMARY KEY,
  item_id integer REFERENCES items
);

INSERT INTO roles VALUES
  ('User'),
  ('Moderator'),
  ('Admin');

INSERT INTO rules VALUES
  ('Create Item'),
  ('Moderate'),
  ('Comment'),
  ('Change item state'),
  ('Read');

INSERT INTO rules_for_role VALUES
  ('User', 'Read'),
  ('User', 'Comment'),
  ('User', 'Create Item'),
  ('Moderator', 'Moderate'),
  ('Moderator', 'Read'),
  ('Admin', 'Create Item'),
  ('Admin', 'Moderate'),
  ('Admin', 'Comment'),
  ('Admin', 'Change item state'),
  ('Admin', 'Read');

INSERT INTO users(login, password, role_name) VALUES
  ('Admin', 'admin', 'Admin'),
  ('Moderator1', 'password', 'Moderator'),
  ('User1', 'userPassword', 'User'),
  ('User2', 'pass', 'User');

INSERT INTO categories VALUES
  ('First category'),
  ('Second category'),
  ('Third category');

INSERT INTO  item_states VALUES
  ('Open'),
  ('Active'),
  ('Resolved'),
  ('Closed');

INSERT INTO items(item_id, name, description, user_id, category_name, state_name) VALUES
  (0, '1Item', 'First item', 2, 'First category', 'Open'),
  (1, '2Item', 'Second item', 2, 'Second category', 'Active'),
  (2, '3Item', 'Third item', 3, 'Third category', 'Resolved');

INSERT INTO comments VALUES
  ('comment', 0),
  ('thanks', 1),
  ('Hello!', 2);

INSERT INTO attachs VALUES
  ('Path', 2),
  ('File path', 1);
DROP TABLE events IF EXISTS;
CREATE TABLE events (
  id INTEGER IDENTITY PRIMARY KEY,
  userIdentity VARCHAR(100),
  pollingName  VARCHAR(100),
  userOption VARCHAR(100),
  eventTime VARCHAR(100)
);
CREATE INDEX userIdentity ON events (userIdentity);

DROP TABLE results IF EXISTS;
CREATE TABLE results (
  id INTEGER IDENTITY PRIMARY KEY,
  userIdentity VARCHAR(100),
  pollingName  VARCHAR(100),
  userOption VARCHAR(100)
);
CREATE INDEX pollingName ON results (pollingName);


DROP TABLE polls IF EXISTS;
CREATE TABLE polls (
  id INTEGER IDENTITY PRIMARY KEY,
  pollingName  VARCHAR(100),
  validoptions VARCHAR(100),
  starttime VARCHAR(100),
  endtime VARCHAR(100)
);
CREATE INDEX pollingName2 ON polls (pollingName);

DROP TABLE users IF EXISTS;
CREATE TABLE users (
  id INTEGER IDENTITY PRIMARY KEY,
  userIdentity VARCHAR(100)
);
CREATE INDEX userIdentity2 ON users (userIdentity);

DROP TABLE errormessages IF EXISTS;
CREATE TABLE errormessages (
  id INTEGER IDENTITY PRIMARY KEY,
  userIdentity VARCHAR(100),
  pollingName  VARCHAR(100),
  userOption VARCHAR(100),
  errorMessage VARCHAR(100)
);
CREATE INDEX errorMessage1 ON errormessages (errorMessage);
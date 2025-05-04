# Filter service

## Intro

This project was made as test task for Askend job.
The project is made by Taavi Kivimaa april & may 2025

## Using

* Create database with psql using command
  ````
  CREATE DATABASE filter;
  CREATE ROLE filteradmin WITH LOGIN PASSWORD 'postgres';
  GRANT ALL ON DATABASE filter TO filteradmin;
  ````
* Run `./gradlew bootRun` in root folder. This will migrate database on the first run.
* Run `cd frontend && npm run dev` to start frontend locally.
* Go to localhost:5173 in browser, and play around with adding filters.
* To have filter modal in non-modal mode, change isModal prop to false in App.tsx
* For running tests make sure Docker is running in background, then run `./gradlew test`

_This is for local development only, don't use this configuration in production_

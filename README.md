# MDD App

MDD App is a social media platform designed for developers.
It is split into two parts: a frontend and a backend.

You will find install and run instructions in:

    /front/README.md

    /back/README.md

## Features

    Subscribe to topics and access article feeds

    Read and comment on articles

    Create new posts

    Update user profile

    Manage subscriptions

## Quick start with compose

To build and run the full stack application, go to the project folder and create .env files for backend. You can rename .env*sample examples

```bash
cd mdd/
mv .env-mdd-sample .env-mdd
mv .env-mysql-sample .env-mysql
```

Then up the stack

```bash
docker compose up -d
```

If you need to give admin access to db or back, change front/back/db port numbers, please see docker-compose.yml configuration, nginx and back/*.env files.

You can access the application on <http://localhost>

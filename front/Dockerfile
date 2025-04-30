### STAGE 1:BUILD ###
FROM node:22.15.0-slim AS builder

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

### STAGE 2:RUN ###
FROM nginx:alpine

RUN mkdir /app

#Copy generated files into nginx
COPY --from=builder /app/dist/front/browser /app

COPY nginx.conf /etc/nginx/nginx.conf

USER nginx

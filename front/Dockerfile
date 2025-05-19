## Stage 1: build de React
FROM node:18-alpine AS builder
WORKDIR /app

# Instala dependencias y arma el build
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

## Stage 2: servidor estático con Nginx
FROM nginx:alpine
# Copiamos sólo la carpeta build a la ubicación de Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Exponemos HTTP
EXPOSE 80

# Arranca Nginx en foreground
CMD ["nginx", "-g", "daemon off;"]

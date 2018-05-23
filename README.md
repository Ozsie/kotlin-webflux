# kotlin-webflux
Kotlin+Sprin Boot 2+WebFlux

```bash
# Build jar
mvn clean install
# Start minikube
minikube start
# Attach minikube docker env
eval $(minikube docker-env)
# Build Docker image
docker build -t webflux .
# Deploy to minikube
kubectl apply -f manifest.yaml
# Print service url
minikube service webflux --url
```

To get all items: `curl $(minikube service webflux --url)/api/items`

## Installing Minikube (on OSX)
Assuming you have Brew and Docker installed

 1. brew cask install virtualbox
 2. brew cask install minikube
 3. ???
 4. Profit
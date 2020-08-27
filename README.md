# notebook-email-service


## Deployment

See [notebook-deployment.git](https://github.com/fbyrne/notebook-deployment) for notes on deployment to a kubernetes cluster.

## Dev lifecycle on minikube

```
$ eval $(minikube docker-env)
$ skaffold build && kubectl -n notebook-dev rollout restart deployment email
```

# Modern microservices-based architecture with Spring Boot and Spring Cloud

A simple microservices-based application that uses the reactive stack of Spring Boot together with the Eureka services provided by Spring Cloud. 

## eureka-service

The Spring Cloud's Eureka service, providing services registrations and discovery features. It is responsible for keeping track of all the services present in the ecosystem and resolve their 
addresses when they are invoked through their service name.

## config-service

The Spring Cloud's Config service, maintaining the configurations of all the microservices present in the ecosystem. In the example, we configured a local git repository present in the file system. However, the `spring.cloud.config.server.git.uri` configuration can be easily changed to point to a remote git repository, for example. 

Remember to run `git init` to initialize the local repository, and to commit the existing files to make them available to the service during the lookup phase.

Each microservice of the ecosystem will retrieve its configuration from the Config service.


## image-service

Service acting as supplier of images.

### Endpoints 

| Verb | Path | Parameters |
| ---- | ---- | ---------- |
| GET  | `/images` | - |
| ...  | ...  | ... |

## gallery-service

Service acting as supplier of galleries of images supplied by the `image-service`.

### Endpoints 

| Verb | Path | Parameters |
| ---- | ---- | ---------- |
| GET  | `/galleries/{id}` | - `id`: the identifier of the gallery  |
| ...  | ...  | ... |

## gateway-service

This service is designed to represent the access point of our microservices ecosystem. Even if with the actual configuration you can access each micro-service directly by poiting to its port, in a production scenario every endpoint should be pass through the gateway. Indeed, the gateway will be the only responsible to check authentication and to route the request to the right micro-service, possibly residing in another machine.

The service exploits the Spring Cloud's `spring-cloud-starter-gateway-server-webflux` dependency and acts as load balancer for the requests configured in its application.yaml, under the properties specified with `cloud.gateway.server.webflux.routes.*`

## Example of usage

Let us image an environmente where we have:

- an instance `eureka-service` running on port 9090;
- an instance `config-service` running on port 8888;
- an instance `user-service` running on port 9101;
- two instances of `image-service` running on ports 9096 and 9097;
- an instance of `gateway-service` running on port 9095 and configuring a mapping like the following one:

```
   gateway:
      server:
        webflux:
          routes:
          ...
            
          - id: image-service
            uri: lb://IMAGE-SERVICE
            predicates:
            - Path=/images/**
```

First of all, we send a request to the `auth-service` to login and obtain the access token that will be used for the next requests. To do so, we try to reach the service endpoint through the `gateway-service`, by performing the request POST `http://localhost:9095/auth/login`. In order to successfully authenticate, the provided credentials must match a valid user in the database used by the `user-service`.

Then we use the obtained token to send a request to the `gateway-service` for an endpoint exposed by the `image-service` (e.g. `<server-address>:9095/images`). The request must include the bearer token in the authorization header. If we have a look to the logs of the instances of the `image-service`, we can see that the requests are distributed among the available instances. 


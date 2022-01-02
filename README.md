# Spring-Microservices-WebFlux-Eureka

A simple microservices-based application that uses the reactive stack of Spring Boot together with the Eureka services provided by Spring Cloud. 

## eureka-service

The Spring's Eureka service, registered as `@EnableEurekaServer`, responsible of keeping track of all the services present in the ecosystem and resolve their 
addresses when they are invoked through their service name.

## image-service

Service acting as supplier of images. The service is registered as `@EnableEurekaClient`.

### Endpoints 

| Verb | Path | Parameters |
| ---- | ---- | ---------- |
| GET  | `/images` | - |
| ...  | ...  | ... |

## gallery-service

Service acting as supplier of galleries of images supplied by the `image-service`. The service is registered as `@EnableEurekaClient`.

### Endpoints 

| Verb | Path | Parameters |
| ---- | ---- | ---------- |
| GET  | `/galleries/{id}` | - `id`: the identifier of the gallery  |
| ...  | ...  | ... |

## gateway-service

A physical gateway, registered as `@EnableEurekaClient`. The service exploits the Spring Cloud's `spring-cloud-starter-gateway` dependency and acts as load balancer for the requests configured in its application.yaml, under the properties specified with `cloud.gateway.routes.*`

## Example of usage

Let us image an environmente where we have:

- an instance `eureka-service` running on port 9090;
- two instances of `image-service` running on ports 9096 and 9097;
- an instance of `gateway-service` running on port 9095 and configuring a mapping like the following one:

```
   gateway:
      routes:
      ...
        
      - id: image-service
        uri: lb://IMAGE-SERVICE
        predicates:
        - Path=/images/**
```

Then if we send a request to the `gateway-service` for an endpoint exposed by the `image-service` (e.g. `<server-address>:9095/images`), and we have a look to the logs of the instances of the `image-service`, we can see that the requests are distributed among the available instances.


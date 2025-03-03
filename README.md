# Run docker container using

```sh
docker pull mongo:latest
```

```sh
docker run -d -p 27017:27017 --name=mongo-local mongo:latest
```

```sh
db.createCollection("users");
```

```sh
db.createCollection("todo-items");
```

# This is how Spring Security with JWT works

1. Initial Login Request Flow
   When a user sends a login request to /user/login, the request first passes through various Spring Security filters.
   The login request contains username and password in the LoginRequest object:

   ```java
   public LoginResponse login(@NotNull LoginRequest loginRequest) {
       Authentication authenticate = authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
       );
       // ... rest of the code
   }
   ```

2. Authentication Process
   The Authentication interface is a core part of Spring Security that represents the current authentication request
   UsernamePasswordAuthenticationToken is a specific implementation of Authentication that holds the credentials
   When authenticationManager.authenticate() is called, it triggers the following chain of events:
    1. The AuthenticationManager (configured in ApplicationSecurityConfig.java) delegates to the
       DaoAuthenticationProvider
    2. The provider uses the UserDetailsService to load the user
   ```java
   @Bean
   public UserDetailsService userDetailsService() {
       return userName -> userRepository.findByUserName(userName)
               .orElseThrow(() -> new UsernameNotFoundException("User not found"));
   }
   ```
    3. The password is verified using the configured BCryptPasswordEncoder

   ```java
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }
   ```

3. JWT Token Generation
   If authentication is successful:
   ```java
    @Override
    public LoginResponse login(@NotNull LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        if (!authenticate.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid user request");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(200);
        loginResponse.setDescription("user authenticated");
        loginResponse.setToken(jwtService.generateToken(loginRequest.getUserName()));
        return loginResponse;
    }
            
   ```
      1. A JWT token is generated using the JwtService
      2. This token will be used for subsequent requests


4. Subsequent Request Authentication
   For all subsequent requests, the JWT token is validated through the JwtAuthFilter

```java
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) {
    final String authHeader = request.getHeader("Authorization");
    // Extract and validate JWT token
    // Set authentication in SecurityContext if valid
}
```

5. Security Configuration
   The security rules are defined in SecurityConfig.java

```java

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/user/create", "/user/getAll", "/user/login", "/health/**")
            .permitAll()
            .anyRequest()
            .authenticated();
    // more config

}
```

This configuration:

1. Disables CSRF for API endpoints
2. Allows public access to specific endpoints (like login)
3. Requires authentication for all other endpoints
   The complete request flow is:
1. Login request arrives at /user/login
2. UserServiceImpl.login() is called
3. AuthenticationManager validates credentials using UserDetailsService and PasswordEncoder
4. If valid, JWT token is generated
5. For subsequent requests:
    1. JwtAuthFilter intercepts the request
    2. Extracts JWT from Authorization header
    3. Validates token using JwtService
    4. If valid, sets authentication in SecurityContext
    5. Request proceeds to the protected resource
       This setup provides a stateless authentication mechanism where:
1. Initial authentication is done via username/password
2. Subsequent requests use JWT tokens
3. No session is maintained on the server
4. Each request is authenticated independently through the JWT token

# API Request

1. Spring Boot Actuator
    ```shell
    curl --location 'localhost:8080/app/todo/actuator/health'
    ```

2. Application Health Check
    ```shell
    curl --location 'localhost:8080/app/todo/health/check'
    ```
3. Create User
    ```shell
   curl --location 'localhost:8080/app/todo/user/create' \
   --header 'Content-Type: application/json' \
   --data '{
   "requestId": "925f5e0b-b2f5-445c-85ad-edcef5b458aa",
   "userName": "tushar",
   "password": "password"
   }'
    ```

4. Login User - This will generate a JWT token which will be used in subsequent requests
    ```shell
    curl --location 'localhost:8080/app/todo/user/login' \
    --header 'Content-Type: application/json' \
    --data '{
    "requestId": "925f5e0b-b2f5-445c-85ad-edcef5b458aa",
    "userName": "tushar",
    "password": "password"
    }'
    ```

5. Create TODO - replace the token in headers with the one generated in Step 4
    ```shell
    curl --location 'localhost:8080/app/todo/item/create' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dXNoYXIiLCJpYXQiOjE3NDA5OTIwNTksImV4cCI6MTc0MDk5NTY1OX0.Fi0JO2g87h3ZHISTKQe6QrKK8vDnPaEU_kt7Le8_h_k' \
    --header 'Content-Type: application/json' \
    --data '{
        "requestId": "925f5e0b-b2f5-445c-85ad-edcef5b458aa",
        "title": "title",
        "description": "todo",
        "deadline": "2025-03-03T09:20:06.953Z"
    }'
    ```

6. Update TODO - replace the token in headers with the one generated in Step 4 and {todo-id} with id of TODO to update
   ```shell
   curl --location 'localhost:8080/app/todo/item/update/{todo-id}' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dXNoYXIiLCJpYXQiOjE3NDA5OTk1NjAsImV4cCI6MTc0MTAwMzE2MH0.ZgUzoOtXQWkg4wDniBG31gTTf459JJfHR5KFA3aIyz8' \
   --header 'Content-Type: application/json' \
   --data '{
       "requestId": "b5340095-891a-4b07-901e-b752f86e7744",
       "description": "newDescription2"
   }'
   ```

7. Delete TODO - replace the token in headers with the one generated in Step 4 and {todo-id} with id of TODO to delete
   ```shell
   curl --location --globoff --request DELETE 'localhost:8080/app/todo/item/delete/{todo-id}' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dXNoYXIiLCJpYXQiOjE3NDA5OTk1NjAsImV4cCI6MTc0MTAwMzE2MH0.ZgUzoOtXQWkg4wDniBG31gTTf459JJfHR5KFA3aIyz8' \
   --header 'requestId: a017731a-5462-4338-a5fc-0bc8ef41272f'
   ```

8. Get TODO(s) - all params are optional and have default values. Replace the token in headers with the one generated in
   Step 4
    ```shell
   curl --location 'localhost:8080/app/todo/item/get?page=0&limit=10&sortBy=deadline&sortDirection=DESC' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dXNoYXIiLCJpYXQiOjE3NDA5OTk1NjAsImV4cCI6MTc0MTAwMzE2MH0.ZgUzoOtXQWkg4wDniBG31gTTf459JJfHR5KFA3aIyz8' \
   --header 'requestId: 85462e95-a640-4d58-b769-ca6ca5b5f38c'
    ```
   


# Things left to implement:
1. ~~Validation of all request~~
2. ~~Token refresher~~
3. Separate out the models from response dto
4. Proper handling of jwt failures
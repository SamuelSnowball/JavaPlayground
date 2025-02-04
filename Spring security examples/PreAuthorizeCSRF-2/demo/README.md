CSRF token demonstration

CSRF is enabled by default in spring security 4.X versions and later. You must have a valid CSRF token when calling any non-read only HTTP method, IE POST/DELETE etc. This explains why GET tests work without a CSRF token. 

We only need CSRF protection if we have a session-based API, vs something like
JWT which is stateless.

Reading: https://www.baeldung.com/spring-security-method-security  which raises an interesting point, By default, Spring AOP proxying is used to apply method security. If a secured method A is called by another method within the same class, security in A is ignored altogether. This means method A will execute without any security checking. The same applies to private methods.

If we use a custom user object annotating a class with @WithMockUser doesn’t work, when the preAuthorize calls the service to perform the auth check, it will take in a parameter of our custom user object, and will say the user types don’t match, so we must remove @WithMockUser and instead use .with(user(ourUserObject))

I used .with, from here: https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/authentication.html#test-mockmvc-securitycontextholder-rpp 

https://stackoverflow.com/questions/52861849/withmockuser-with-custom-user-implementation 

And the user import comes from: import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

These are the available properties within SPEL, principal / authentication etc... https://docs.spring.io/spring-security/reference/5.8/servlet/authorization/expression-based.html 


Java 8
Spring parent 2.4.7
- Spring boot 2.4.7
- Spring boot starter security 2.4.7
    - Spring security 5.4.6



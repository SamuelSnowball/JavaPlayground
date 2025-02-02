CSRF token demonstration

CSRF is enabled by default in spring security 4.X versions and later. You must have a valid CSRF token when calling any non-read only HTTP method, IE POST/DELETE etc. This explains why GET tests work without a CSRF token. 

We only need CSRF protection if we have a session-based API, vs something like
JWT which is stateless.

If we use a custom user object annotating a class with @WithMockUser doesn’t work, when the preAuthorize calls the service to perform the auth check, it will take in a parameter of our custom user object, and will say the user types don’t match, so we must remove @WithMockUser and instead use .with(user(ourUserObject))

Java 8
Spring parent 2.4.7
- Spring boot 2.4.7
- Spring boot starter security 2.4.7
    - Spring security 5.4.6



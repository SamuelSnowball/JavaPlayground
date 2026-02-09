# Spring security 5 #

Example of creating a custom filter and inserting it into the Spring Boot filter chain.

Spring Boot 2 defaults to Spring Security 5.
Notably, we can also use Spring Security 6 in Spring Boot 2 by overriding the default version in the properties section. https://www.baeldung.com/spring-security-migrate-5-to-6

I added a custom filter by creating 2 new classes for the relevant config, I also needed to include them within @ContextConfiguration so they are picked up within the test suite.

There are 2 ways to register your filter, the first via the HttpSecurity object within the configure method:
```
http.authorizeRequests()...
http.addFilterBefore(customFilter, CustomAuthenticationFilter.class);
```

And the second is to include it in the Filter chain via a bean:
```
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(
                new CustomFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }
```

Resources
- https://www.baeldung.com/spring-security-custom-filter


Current issues
- Why is my CustomFilter being executed at stage 6/15? it should happen after BasicAuthenticationFilter which I cannot see
- It looks like my filter is rejecting the request but the tests are still passing..
- What's happening with my requiredParameter?

Possible solutions
- https://stackoverflow.com/questions/47290024/spring-unit-test-mockmvc-fails-when-using-custom-filter-in-spring-security?rq=3
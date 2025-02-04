Spring security 6
Requires at least Java version 17 and uses the jakarta namespace. We have migrated the javax.servlet imports to jakarta.servlet.
Spring Boot 3 defaults to Spring Security 6.
Notably, we can also use Spring Security 6 in Spring Boot 2 by overriding the default version in the properties section. https://www.baeldung.com/spring-security-migrate-5-to-6

In spring security 6, you annotate with @Configuration and define a SecurityFilterChain bean taking in a HttpSecurity builder object, in this method you say, what you want to protect and how.
One of the major changes is the removal of WebSecurityConfigurerAdapter in favor of component-based security configuration.
Also, the authorizeRequests() is removed and replaced with authorizeHttpRequests() to define authorization rules.

Practice to use is, secure by default. So at the end of the method we say, ourHttp.anyRequest().authenticated(); So if tomorrow we add a new endpoint it would automatically be secured.

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

This sample defines everything within one Bean.

Resources
- https://www.baeldung.com/spring-security-custom-filter
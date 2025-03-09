The commit that deleted everything: https://github.com/SamuelSnowball/JavaPlayground/commit/2e69a7edb3f35f5a9be4dd63aff07fbaa7c587c5#diff-1c35cb41a4a35c95bbb9c9c93710cf31b2c31e2a4012fc15583bb94138e2c51a

Notes
- I've managed to delete my schema file / init directory, if I rebuild DB it won't work

To build
- add tests to current functionality
    - add custom models, so you can use build methods and custom jackson etc
    - seperate between the two in the service layer
    - within, the controller tests, check for the Model/Domain class vs the generated Pojo

- transactions?
- swagger API with example models

- build out the schema
- users
- orders
- full payment service
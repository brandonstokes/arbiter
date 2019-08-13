[![CircleCI](https://circleci.com/gh/brandonstokes/arbiter/tree/master.svg?style=shield)](https://circleci.com/gh/brandonstokes/arbiter/tree/master)
[![codecov](https://codecov.io/gh/brandonstokes/arbiter/branch/master/graph/badge.svg)](https://codecov.io/gh/brandonstokes/arbiter)

## arbiter
api to determine a draft board based off of a list of members with keepers


#### request validation (*on deserialize*)
+ __does not allow duplicate member names__
+ __does not allow duplicate player ids__
+ __does not allow two keepers to be selected in the same round__
+ __reset second keepers round when (first keeper penalty == second keeper round) to be (first keeper penalty - 1)__


#### using the application
  + define application basic auth
    ```shell script
    export ARBITER_USERNAME=user
    export ARBITER_PASSWORD=password
    ```
  + start: `./gradlew bootrun`
    - -> __GET__ `http://localhost:8080/draft/players`
    - -> __POST__ `http://localhost:8080/draft/arbitrate`

  + test: `./gradlew test`

  + lint: `./gradlew ktlintCheck`

  + fix lint: `./gradlew ktlintFormat`

  + everything else: `./gradlew tasks`


#### api documentation
  + start:
    `./gradlew bootrun`
  + navigate:
    `http://localhost:8080/swagger-ui.html`

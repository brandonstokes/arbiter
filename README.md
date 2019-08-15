[![CircleCI](https://circleci.com/gh/brandonstokes/arbiter/tree/master.svg?style=shield)](https://circleci.com/gh/brandonstokes/arbiter/tree/master)
![Heroku](http://heroku-badge.herokuapp.com/?app=axiom-arbiter&style=flat&svg=1)
[![codecov](https://codecov.io/gh/brandonstokes/arbiter/branch/master/graph/badge.svg)](https://codecov.io/gh/brandonstokes/arbiter)

## arbiter

Re-orders list of members based off of when a player was kept and their penalties.

#### request validation (*on deserialize*)
+ __does not allow duplicate member names__
+ __does not allow duplicate player ids__
+ __does not allow two keepers to be selected in the same round__
+ __reset second keepers round when (first keeper penalty == second keeper round) to be (first keeper penalty - 1)__


#### using the application
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

<small>https://axiom-arbiter.herokuapp.com/swagger-ui.html</small>

# tictactoe.clj

## Description

This is an implementation of the tic-tac-toe game (or Noughts and crosses, Xs and Os) in Clojure.

## Features

##### User interface
* CLI

##### Board size
* 3x3

##### Perfect computer player
* Never loses
* Wins as fast as possible

## Dependencies

##### Execution
* Clojure 1.7

##### Testing
* [Speclj][speclj] 3.3.1

[speclj]: https://github.com/slagyr/speclj

##### Others
* [Leiningen][lein] (for managing the project)

[lein]: http://leiningen.org/

## Test

`lein test` will run the tests once.

If you want to make changes and have fast feedback from the tests, run `lein spec -a`. 
It will avoid starting the JVM each time and will run the tests each time a change is made in a file.

## Compile

`lein uberjar` will create a JVM *.jar in the folder `target/uberjar`

## Run

`lein run` will run the game. 

It receives two arguments: the types of the players (human / computer).

For example, to run a human vs computer game would be:

`lein run human computer`

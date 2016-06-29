# Networked Tic-Tac-Toe in Scala using JavaFX
Hello and welcome.

This is a little project I created to help myself learn Scala. I also wanted to build a GUI application using the JavaFX toolkit, since I had only worked with Swing previously. I also kept the dependencies to a minimum because I wanted to keep the learning curve to a minimum, thus no ScalaFX, Scalaz, or Akka. This version of tic-tac-toe is networked via Java Sockets (again, small learning curve for me).

## Building:
You can build a 'fat' jar with all dependencies included by running `sbt assembly` within the project directory.

## Running
You can play the game by running to instances of the program. It is recommended that you build a 'fat' jar of the game instead of using 'sbt run' as I am currently having issues with it. Once you've assembled a jar, you can run it via `java -jar tic-tac-toe-fx-assembly-1.0.jar`.

There is a button in the GUI to "start a local server", and another button to "connect to a server" using the provided IP address.

Once two players have connected, the game will begin and the tic-tac-toe grid will render.

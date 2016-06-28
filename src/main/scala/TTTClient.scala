import java.io.PrintStream
import java.net.{InetAddress, Socket}
import java.util.concurrent.Executors
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

import scala.concurrent.ExecutionContext
import scala.io.{BufferedSource, StdIn}

/**
  * Created by dragonaxe on 6/23/16.
  */
class TTTClient(g: GraphicsContext, prom: RePromise[String]) extends Thread {

  override def run(): Unit = {
    val ip = StdIn.readLine("Enter IP> ")
    val s = new Socket(InetAddress.getByName(ip), 9999)
    lazy val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())
    val thread = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
    var continue = true

    def serverProcessing(): Unit = {

      if (in.hasNext) {
        val command = in.next()
        println("Received: " + command)
        if (command.startsWith("Display=")) {
          renderGrid(g, parseDisplay(command.substring(8)))
        } else if (command.startsWith("Player=")) {

        } else if (command.equals("Play")) {
          println("Waiting for click action.")
          val pos = prom.getValue
          println("Acting: " + pos)
          out.println("Act=" + pos)
        }
        serverProcessing()
      }
    }
    serverProcessing()
    s.close()
  }

  def parseDisplay(text: String): Array[Array[Int]] = {
    val grid = Array.fill(3, 3)(0)

    grid(0)(0) = text.charAt(0).asDigit
    grid(0)(1) = text.charAt(1).asDigit
    grid(0)(2) = text.charAt(2).asDigit

    grid(1)(0) = text.charAt(4).asDigit
    grid(1)(1) = text.charAt(5).asDigit
    grid(1)(2) = text.charAt(6).asDigit

    grid(2)(0) = text.charAt(8).asDigit
    grid(2)(1) = text.charAt(9).asDigit
    grid(2)(2) = text.charAt(10).asDigit

    grid
  }


  def drawEx(g: GraphicsContext, x: Int, y: Int): Unit = {
    val xCenter = x * 100 + 50
    val yCenter = y * 100 + 50
    g.strokeLine(xCenter - 40, yCenter - 40, xCenter + 40, yCenter + 40)
    g.strokeLine(xCenter - 40, yCenter + 40, xCenter + 40, yCenter - 40)
  }

  def drawOval(g: GraphicsContext, x: Int, y: Int): Unit = {
    val xCenter = x * 100 + 50
    val yCenter = y * 100 + 50
    g.strokeOval(xCenter - 40, yCenter - 40, 80, 80)
  }

  def renderGrid(g: GraphicsContext, grid: Array[Array[Int]]): Unit = {
    g.setStroke(Color.GREEN)
    g.setFill(Color.WHITE)
    g.clearRect(0, 0, 300, 300)
    g.strokeLine(0, 100, 300, 100)
    g.strokeLine(0, 200, 300, 200)
    g.strokeLine(100, 0, 100, 300)
    g.strokeLine(200, 0, 200, 300)
    for (y <- grid.indices) {
      for (x <- grid(y).indices) {
        if (grid(y)(x) == 1) {
          drawEx(g, x, y)
        } else if (grid(y)(x) == 2) {
          drawOval(g, x, y)
        }
      }
    }
  }
}

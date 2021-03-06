import java.io.PrintStream
import java.net.{ServerSocket, Socket}

import scala.io.BufferedSource

/**
  * This is the tic-tac-toe server thread. It lets 2 clients connect to it and sends 'draw' and 'request play'
  * commands to them.
  */
object TTTServer extends Thread {

  override def run(): Unit = {
    val server = new ServerSocket(9999)
    while (true) {
      try {
        twerkTwerkTwerkTwerk(server)
      } catch {
        case e: Exception => println("Resetting Game. " + e.getMessage)
      }
    }
  }

  def twerkTwerkTwerkTwerk(server: ServerSocket): Unit = {
    def collect(opens: List[Socket]): List[Socket] = {
      if (opens.length < 2) {
        println("Client " + opens.length + " connected")
        collect(opens :+ server.accept())
      } else {
        opens
      }
    }
    val players = collect(Nil)
    def play(grid: Array[Array[Int]], iterator: Iterator[Socket], playerNum: Int): Unit = {
      players.foreach((player: Socket) => {
        val out = new PrintStream(player.getOutputStream)
        println("Display=" + serializedGrid(grid))
        out.println("Display=" + serializedGrid(grid))
      })
      if (!checkGridIsWin(grid)) {
        if (iterator.hasNext) {
          val player = iterator.next()
          val out = new PrintStream(player.getOutputStream)
          println("Play")
          out.println("Play")
          val in = new BufferedSource(player.getInputStream)
          val text = in.getLines().next()
          println(text)
          val (x, y) = parseCoordinate(text.substring(4))
          grid(y)(x) = playerNum
          play(grid, iterator, playerNum + 1)
        } else {
          play(grid, players.iterator, 1)
        }
      }
    }
    play(Array.fill(3, 3)(0), players.iterator, 1)
  }

  /**
    * Serialize any sized grid. Rows are separated by dots.
    *
    * @param grid The grid to serialize
    * @return A string with dot separated rows.
    */
  def serializedGrid(grid: Array[Array[Int]]): String = {
    val builder = StringBuilder.newBuilder
    grid.iterator.foreach((v: Array[Int]) => {
      builder.append('.')
      v.iterator.foreach((z: Int) => {
        builder.append(z.toString)
      })
    })
    builder.toString().substring(1)
  }

  def checkGridIsWin(grid: Array[Array[Int]]): Boolean = {
    // TODO: implement grid checking for winner
    false
  }

  def parseCoordinate(text: String): (Int, Int) = {
    println("Parsing: " + text)
    val index = text.indexOf(".")
    (text.substring(0, index).toInt, text.substring(index + 1).toInt)
  }

  class clientInputConnection(socket: Socket) extends Thread {
    override def run(): Unit = {
      val in = new BufferedSource(socket.getInputStream).getLines()
      val out = new PrintStream(socket.getOutputStream)
      while (in.hasNext) {
        val text = in.next()
        text match {
          case "Start" => println()
        }
      }
    }
  }

  def main(args: Array[String]) {
    this.start()
  }

}

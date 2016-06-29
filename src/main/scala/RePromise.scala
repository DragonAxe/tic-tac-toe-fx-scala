import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

/**
  * This is my hack using scala's futures and promises to get the TTTClient thread to block while it waits for a click
  * action on the GUI thread. I will redesign this later once I have some time.
  *
  * @tparam T The type of data that will be promised.
  */
class RePromise[T] {

  var isWaiting: Boolean = false
  var prom: Promise[T] = Promise[T]

  def getValue: T = {
    isWaiting = true
    val result = Await.result(prom.future, Duration.Inf)
    prom = Promise[T]
    result
  }

  def succeed(value: T): Unit = {
    if (isWaiting) {
      if (!prom.isCompleted) {
        isWaiting = false
        prom.success(value)
      }
    }
  }
}

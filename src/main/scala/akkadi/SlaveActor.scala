package akkadi


import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, ActorRefFactory, Props}
import javax.inject.{Inject, Singleton}

case class SetCounter(i: Int)

class SlaveActor(initialCounter: Int = 0, service: SomeService) extends Actor {
  println("Init other actor")

  val counter = new AtomicInteger(initialCounter)

  service.someFunction()
  var errorCounter = 0

  var initiated: Boolean = false

  override def receive: Receive = {
    case SetCounter(i) =>
      counter.set(i)
      initiated = true

    case "error" if !initiated =>
      errorCounter += 1
      println(s"error counter=$counter errorCounter=$errorCounter")
      throw new RuntimeException("ERROR")

    case x if !initiated =>
      counter.incrementAndGet()
      println(s"msg = $x counter=$counter errorCounter=$errorCounter")

  }
}


@Singleton
class SlaveActorBuilder @Inject()(s: SomeService) {
  def props(i: Int): () => Props = () => Props(new SlaveActor(i, s))

  def ref(c: ActorRefFactory)(i: Int): ActorRef = c.actorOf(props(i)())
}


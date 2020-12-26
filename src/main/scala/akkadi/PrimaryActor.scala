package akkadi

import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, OneForOneStrategy, SupervisorStrategy}
import akkadi.guiceactor.{ActorConstructor, ActorProducer}
import com.google.inject.Inject
import javax.inject.Singleton


class PrimaryActor(actorConstructor: ActorConstructor[SlaveActor], s: SomeService) extends Actor {

  println("Init main actor")

  override val supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: RuntimeException => Resume
  }

  override def receive: Receive = {
    case "new" =>

      actorConstructor.construct(1, s)(context).ref ! "wellcome"

      context.children.foreach { a =>
        a ! "Greetings new actor"
      }

    case "show all" =>
      context.children.foreach(a => println(a.path))


    case cmd: String if cmd.matches("^kill .*") =>
      val path = cmd.split(" ")(1)
      context.child(path).foreach { ch =>
        context.stop(ch)
      }
      context.children.foreach { a =>
        a ! s"Actor $path leave us"
      }

    case cmd: String if cmd.matches("send \\S+ .*") =>
      val Array(name, command) = cmd.split(" ").tail
      context.child(name).foreach(_ ! command)

    case x => context.children.foreach(_ ! x)
  }
}


@Singleton
class PrimaryActorProducer @Inject()(actorConstructor: ActorConstructor[SlaveActor], s: SomeService)
  extends ActorProducer[PrimaryActor] {
  override def produce(): PrimaryActor = new PrimaryActor(actorConstructor, s)
}


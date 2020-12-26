package akkadi

import akka.actor.{ActorRef, ActorSystem, Props}
import akkadi.guiceactor.{ActorFactory, ActorProducer, TypedActorRef}
import com.google.inject.{AbstractModule, Provides}
import javax.inject.Singleton
import net.codingwell.scalaguice.ScalaModule

/**
 * DI configuration module
 */
class StartupModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {

    // actor system
    val system = ActorSystem("main-system")
    bind[ActorSystem].toInstance(system)
    bind[ActorProducer[PrimaryActor]].to[PrimaryActorProducer]

  }

  /**
   * MainActor provider в контексте DI
   *
   * @param actorFactory
   * @param actorSystem
   * @return
   */
  @Singleton
  @Provides
  def mainActorDep(actorFactory: ActorFactory, actorSystem: ActorSystem): TypedActorRef[PrimaryActor] = {
    new TypedActorRef[PrimaryActor] {
      override val ref: ActorRef = actorSystem.actorOf(Props(classOf[PrimaryActorProducer], actorFactory))
    }
  }

}





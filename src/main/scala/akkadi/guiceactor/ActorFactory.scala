package akkadi.guiceactor

import akka.actor.{Actor, ActorRef, ActorRefFactory, ActorSystem}
import com.google.inject.{Inject, Injector}
import javax.inject.Singleton
import net.codingwell.scalaguice.InjectorExtensions._

/**
 * Actor factory for getting actors by type with DI
 *
 * @param injector - DI injector
 */
@Singleton
class ActorFactory @Inject()(injector: Injector) {

  import scala.reflect.runtime.universe.TypeTag

  val system: ActorSystem = injector.instance[ActorSystem]

  def typedRef[T <: Actor](context: ActorRefFactory)(implicit tag: TypeTag[T]): TypedActorRef[T] =
    injector.instance[TypedActorRefFactory[T]].create(context)

  def typedRef[T <: Actor](context: ActorRefFactory, name: String)(implicit tag: TypeTag[T]): TypedActorRef[T] =
    injector.instance[TypedActorRefFactory[T]].create(context, name)

  def actorRef[T <: Actor](context: ActorRefFactory)(implicit tag: TypeTag[T]): ActorRef =
    typedRef[T](context).ref

  def actorRef[T <: Actor](context: ActorRefFactory, name: String)(implicit tag: TypeTag[T]): ActorRef =
    typedRef[T](context, name).ref

  def injectedRef[T <: Actor](implicit tag: TypeTag[T]): ActorRef = injector.instance[TypedActorRef[T]].ref

}
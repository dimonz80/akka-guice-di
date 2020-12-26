package akkadi.guiceactor

import akka.actor.{Actor,  IndirectActorProducer}

/**
 * Abstract actor producer <br/>
 * Needs to implement produce method
 *
 * {{{
 *
 *   @Singleton
 *   class PrimaryActorProducer @Inject()(actorConstructor: ActorConstructor[SlaveActor], s: SomeService)
 *   extends ActorProducer[PrimaryActor] {
 *     override def produce(): PrimaryActor = new PrimaryActor(actorConstructor, s)
 *   }
 *
 * }}}
 *
 * @tparam T actor type
 */
abstract class ActorProducer[T <: Actor] extends IndirectActorProducer {
  override def actorClass: Class[_ <: Actor] = classOf[Actor]
}


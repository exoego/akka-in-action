package aia.persistence

import scala.concurrent.duration._

import akka.actor._

import com.typesafe.config.Config

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def createExtension(system: ExtendedActorSystem): Settings = new Settings(system)

  override def lookup(): ExtensionId[_ <: Extension] = Settings

  override def get(system: ActorSystem): Settings = super.get(system)
}

class Settings(config: Config) extends Extension {
  def this(system: ExtendedActorSystem) = this(system.settings.config)

  val passivateTimeout = Duration(config.getString("passivate-timeout"))
  object http {
    val host = config.getString("http.host")
    val port = config.getInt("http.port")
  }
}

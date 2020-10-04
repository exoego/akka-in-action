package aia.persistence.rest

import com.typesafe.config.Config

import scala.concurrent.Future
import akka.actor._
import akka.event.Logging
import akka.util.Timeout
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import aia.persistence._

import scala.util.{Failure, Success}

trait ShoppersServiceSupport extends RequestTimeout {
  def startService(shoppers: ActorRef)(implicit system: ActorSystem) = {
    val config = system.settings.config
    val settings = Settings(system)
    val host = settings.http.host
    val port = settings.http.port

    implicit val ec = system.dispatcher  //bindAndHandle requires an implicit ExecutionContext

    val api = new ShoppersService(shoppers, system, requestTimeout(config)).routes // the RestApi provides a Route
 
    val bindingFuture: Future[ServerBinding] =
      Http().newServerAt(host, port).bindFlow(api)
   
    val log =  Logging(system.eventStream, "shoppers")
    bindingFuture.map { serverBinding =>
      log.info(s"Shoppers API bound to ${serverBinding.localAddress} ")
    }.onComplete {
      case Success(_) =>
      case Failure(ex) =>
        log.error(ex, "Failed to bind to {}:{}!", host, port)
        system.terminate()
    }
  }
}

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}

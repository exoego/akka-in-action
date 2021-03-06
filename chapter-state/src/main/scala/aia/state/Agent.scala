package aia.state

import akka.agent.Agent
import akka.actor.ActorSystem
import concurrent.Await
import concurrent.duration._


case class BookStatistics(nameBook: String, nrSold: Int)
case class StateBookStatistics(sequence: Long,
                            books: Map[String, BookStatistics])


class BookStatisticsMgr(system: ActorSystem) {
  implicit val ex = system.dispatcher //todo: change chapter 2.2 =>2.3
  val stateAgent = Agent(StateBookStatistics(0, Map())) //todo: change chapter 2.2 =>2.3

  def addBooksSold(book: String, nrSold: Int): Unit = {
    stateAgent send (oldState => {
      val bookStat = oldState.books.get(book) match {
        case Some(bookState) =>
          bookState.copy(nrSold = bookState.nrSold + nrSold)
        case None => new BookStatistics(book, nrSold)
      }
      oldState.copy(oldState.sequence + 1,
        oldState.books + (book -> bookStat))
    })
  }

  def addBooksSoldAndReturnNewState(book: String,
                                    nrSold: Int): StateBookStatistics = {
    val future = stateAgent alter (oldState => {
      val bookStat = oldState.books.get(book) match {
        case Some(bookState) =>
          bookState.copy(nrSold = bookState.nrSold + nrSold)
        case None => new BookStatistics(book, nrSold)
      }
      oldState.copy(oldState.sequence + 1,
        oldState.books + (book -> bookStat))
    })
    Await.result(future, 1 second)
  }

  def getStateBookStatistics(): StateBookStatistics = {
    stateAgent.get()
  }
}

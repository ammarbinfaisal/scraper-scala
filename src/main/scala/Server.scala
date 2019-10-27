import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{HttpMuxer, Request, Response, Status}
import com.twitter.util.{Await, Future}

object Server {
  val apiHandler = new Service[Request, Response] {
    def apply(request: Request): Future[Response] = {
      val url = request.getParam("url")
      val selector = request.getParam("selector")
      val html = Scraper.scrape(url, selector)
      val response = Response(request.version, Status.Ok)
      response.contentString = Scraper.toJson(html)
      Future.value(response)
    }
  }

  def start(): Unit = {
    HttpMuxer.addHandler("/api", apiHandler)
    val server = Http.serve(":8080", HttpMuxer)
    Await.ready(server)
  }
}
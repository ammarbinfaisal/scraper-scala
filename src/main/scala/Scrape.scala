import org.jsoup.Jsoup
import org.jsoup.nodes._
import java.net.{HttpURLConnection, URL}
import java.util.LinkedHashMap; 
import com.google.gson.{Gson, GsonBuilder, FieldNamingPolicy, JsonObject, JsonArray}

object Scraper{
  def scrape(url: String, selector: String): Element = {
    val doc: Document =  Jsoup.parse(HttpReq.get(url), "utf-8", url)
    val el: Element = doc.selectFirst(selector)
    return el
  }

  def toJson(el: Element): String = {
    if(el == null) return ""
    val gson: Gson = new GsonBuilder()
     .enableComplexMapKeySerialization()
     .serializeNulls()
     .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
     .setPrettyPrinting()
     .setVersion(1.0)
     .create();
    val json = new JsonObject()
    json.addProperty("text", el.text())
    val attrs = new JsonArray()
    el.attributes().asList().forEach(attribute => {
      val attr = new JsonObject()
      attr.addProperty(attribute.getKey(), attribute.getValue())
      attrs.add(attr)
    })
    json.add("attributes", attrs)
    json.toString()
  }
}

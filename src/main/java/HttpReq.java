import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpReq {
    public static InputStream get(String url) throws IOException {
	    URL obj = new URL(url);
	    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
	    conn.setReadTimeout(5000);
	    conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	    conn.addRequestProperty("User-Agent", UserAgent.random());

	    boolean redirect = false;

	    int status = conn.getResponseCode();
	    if (status != HttpURLConnection.HTTP_OK) {
	    	if (status == HttpURLConnection.HTTP_MOVED_TEMP
	    		|| status == HttpURLConnection.HTTP_MOVED_PERM
	    			|| status == HttpURLConnection.HTTP_SEE_OTHER)
	    	redirect = true;
	    }

	    if (redirect) return get(conn.getHeaderField("Location"));
	    return conn.getInputStream();
  }
}
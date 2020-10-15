/**
 * @Author Danping Cai 10456033
 * @Description CS546
 * @Date 10/15/20
 **/

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class UserSearch {

    public static void main(String args[]) throws IOException, URISyntaxException {
        String response = getUsers("TwitterDev,TwitterEng");
        System.out.println(response);
    }

    /*
     * This method calls the v2 Users endpoint with usernames as query parameter
     * */
    private static String getUsers(String usernames) throws IOException, URISyntaxException {
        String bearerToken = "AAAAAAAAAAAAAAAAAAAAAN5wIAEAAAAA4O0tnpXXWxE5ioOJxNpqC96gi40%3Dtq4XCXCp9FR93158A6amoT8KCjvIsTrcezo7HSfPprwB8Okziv";
        String userResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", usernames));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return userResponse;
    }

}
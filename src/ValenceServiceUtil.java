
import com.d2lvalence.idkeyauth.AuthenticationSecurityFactory;
import com.d2lvalence.idkeyauth.ID2LAppContext;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import java.net.URI;
import java.net.URISyntaxException;



/**
 * Created with IntelliJ IDEA.
 * User: rhmiller
 * Date: 3/13/13
 */
public class ValenceServiceUtil {
    private final static String APP_ID = "";         //Key tool provided app id
    private final static String APP_KEY = "";        //Key tool provided app key
    private final static String USER_USERNAME = "";  //Actual LE user/service user account username
    private final static String USER_PASSWORD = "";  //Actual LE user/service user account password
    private final static String HOST = "";           //Host name (eg. valence.desire2learn.com)
    private final static int PORT = 443;

    public ValenceServiceUtil() {
    }


    /**
     * This is a very basic java example of how to call valence authentication page (the page that returns a user_id and user_key for further oauth calls).
     * This method should return a 302 status because it will redirect back to the HOST (your LE home). If you leave the
     * HOST in buildAuthUrl empty it should return a 200, anything other than 302 or 200 status is not a good sign.
     *
     * I feel that this is a pretty good test because it verifies that valence is working and it is giving back user keys
     * which you need in order to make other valence calls.
     *
     * @return status of the call
     * @throws Exception
     */
    public int testValence() throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setAuthenticationPreemptive(true);
        Credentials defaultcreds = new UsernamePasswordCredentials(USER_USERNAME, USER_PASSWORD);
        client.getState().setCredentials(new AuthScope(HOST, 80, AuthScope.ANY_REALM), defaultcreds);
        HttpMethod method = new GetMethod(buildAuthUrl(HOST));   //You can change this here to redirect to where ever.
        return client.executeMethod(method);
    }

    private static String buildAuthUrl(String callbackUrl) throws URISyntaxException {
        AuthenticationSecurityFactory factory = new AuthenticationSecurityFactory();
        ID2LAppContext appContext = factory.createSecurityContext(APP_ID, APP_KEY);
        return appContext.createWebUrlForAuthentication(HOST, PORT, new URI(callbackUrl)).toString();
    }
}

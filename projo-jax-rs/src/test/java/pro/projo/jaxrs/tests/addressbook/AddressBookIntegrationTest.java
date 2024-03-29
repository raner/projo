//                                                                          //
// Copyright 2016 - 2022 Mirko Raner                                        //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
//     http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
//                                                                          //
package pro.projo.jaxrs.tests.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.JsonParser;
import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.response.RestResponse;
import static com.jcabi.http.Request.GET;
import static com.jcabi.http.Request.PUT;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
* {@link AddressBookIntegrationTest} is an embedded-Tomcat-based integration test for verifying the integration between
* Jersey and Projo.
*
* @author Mirko Raner
**/
public class AddressBookIntegrationTest
{
    private static final String SERVLET_NAME = "jersey-container-servlet";

    private static String webPort;

    private static Tomcat server;

    /**
    * Starts a stand-alone Tomcat server with a Jersey container running the {@link AddressBook} service.
    *
    * @param arguments command line arguments (currently ignored)
    * @throws ServletException if there was a problem with the Jersey servlet container
    * @throws LifecycleException if there was a problem with the Tomcat server
    * @throws IOException if the server had problems accessing the local file system
    **/
    public static void main(String[] arguments) throws ServletException, LifecycleException, IOException
    {
        startServer();
        server.getServer().await();
    }

    @BeforeClass
    public static void startServer() throws ServletException, LifecycleException, IOException
    {
        Path webAppDirectory = Files.createTempDirectory(null);
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", String.valueOf(true));
        webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty())
        {
            webPort = "8080";
        }
        boolean started = false;
        while (!started)
        {
            server = new Tomcat();
            server.setPort(Integer.valueOf(webPort));
            Context context = server.addWebapp("", webAppDirectory.toString());
            Tomcat.addServlet(context, SERVLET_NAME, new ServletContainer(new AddressBookConfiguration()));
            context.addServletMappingDecoded("/*", SERVLET_NAME);
            try
            {
                System.err.println("Starting Tomcat server on port " + webPort);
                server.start();
                started = true;
                System.err.println("Server started successfully on port " + webPort);
            }
            catch (LifecycleException exception)
            {
                System.err.println("Caught LifecycleException - trying a different port...");
                int port = Integer.parseInt(webPort)+1;
                webPort = String.valueOf(port);
                server.setPort(port);
            }
        }
    }

    @AfterClass
    public static void stopServer() throws LifecycleException
    {
        server.stop();
    }

    @Test
    public void testProjoRequestAndResponse() throws Exception
    {
        String json = "{\"street\":\"1 Main Street\", \"city\":\"Smalltown\", \"state\":\"CA\", \"zipCode\":\"99999\"}";
        url("addressbook/John_Doe").method(PUT)
            .body().set(json).back()
            .fetch().as(RestResponse.class)
            .assertStatus(HTTP_NO_CONTENT);
        url("addressbook/John_Doe").method(GET)
            .fetch().as(RestResponse.class).assertBody(matches(json))
            .assertStatus(HTTP_OK);
    }

    private Request url(String path)
    {
        return new ApacheRequest("http://localhost:" + webPort + "/" + path).header(CONTENT_TYPE, APPLICATION_JSON);
    }

    private Matcher<String> matches(String expected)
    {
        return new TypeSafeMatcher<String>()
        {
            @Override
            protected boolean matchesSafely(String actual)
            {
                return JsonParser.parseString(expected).equals(JsonParser.parseString(actual));
            }

            @Override
            public void describeTo(Description description)
            {
                description.appendValue(expected);
            }
        };
    }
}

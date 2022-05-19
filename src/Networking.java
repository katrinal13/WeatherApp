import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Networking
{
    private String apiKey;
    private String baseUrl;

    public Networking()
    {
        apiKey = "7b3d6740a6244ad28bd231310221705";
        baseUrl = "http://api.weatherapi.com/v1";
    }

    public DataModel getWeatherDetails(String zip)
    {
        String endPoint = "/current.json";
        String urlWeatherDetails = baseUrl + endPoint + "?q=" + zip + "&key=" + apiKey;

        String response = makeAPICall(urlWeatherDetails);

        DataModel weather = parseWeatherJSON(response);
        return weather;
    }

    private String makeAPICall(String url)
    {
        try
        {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private DataModel parseWeatherJSON(String json)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONObject currentObj = jsonObj.getJSONObject("current");
        double tempC = currentObj.getDouble("temp_c");
        double tempF = currentObj.getDouble("temp_f");
        JSONObject condition = currentObj.getJSONObject("condition");
        String text = condition.getString("text");
        String icon = condition.getString("icon");

        DataModel weather = new DataModel(tempC, tempF, text, icon);
        return weather;
    }

}

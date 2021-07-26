package blue.project.booklisting;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String MESSAGE_MISSING_INFORMATION = "No information available";

    /**
     * Private constructor
     * Unable to create {@link QueryUtils} object
     */
    private QueryUtils() {}

    /**
     * Get volumes data from given url
     * @param stringURL is the string url
     * @return List of volumes
     */
    public static ArrayList<Volume> getData(String stringURL) {
        ArrayList<Volume> data;
        String jsonResponse = "";
        URL url = createURL(stringURL);
        if (url != null) {
            try {
                jsonResponse = fetchData(url);
            } catch (IOException exception) {
                Log.e(LOG_TAG, exception.getMessage());
                return null;
            }
            if (jsonResponse != null) {
                data = extractData(jsonResponse);
                if (data != null) {
                    return data;
                } else {
                    Log.e(LOG_TAG, "Unable to extract data from JSON response");
                    return null;
                }
            } else {
                Log.e(LOG_TAG, "Unable to fetch data from URL");
                return null;
            }
        } else {
            Log.e(LOG_TAG, "Unable to create a URL");
            return null;
        }
    }

    /**
     * Create URL
     * @param stringUrl is the string URL which needs to be converted into URL
     * @return URL
     */
    private static URL createURL(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, exception.getMessage());
            return null;
        }
    }

    /**
     * Fetch data from URL
     * @param url
     * @return JSON response
     * @throws IOException
     */
    private static String fetchData(URL url) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    jsonResponse.append(line);
                    line = bufferedReader.readLine();
                }
            } else {
                Log.e(LOG_TAG, "Response Code: " + urlConnection.getResponseCode());
                return null;
            }
            return jsonResponse.toString();
        } catch (IOException exception) {
            Log.e(LOG_TAG, exception.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    /**
     * Extract volume data from JSON response
     * @param jsonResponse
     * @return List of volumes
     */
    private static ArrayList<Volume> extractData(String jsonResponse) {
        ArrayList<Volume> volumes = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray items = root.getJSONArray("items");
            // Log information
            Log.i(LOG_TAG, "Items received: " + items.length());

            for (int i = 0; i < items.length(); i++) {
                JSONObject currentVolume = items.getJSONObject(i);
                JSONObject volumeInfo = currentVolume.getJSONObject("volumeInfo");

                // Extract title
                String title = extractInformation(volumeInfo, "title");

                // Extract authors
                String authors = extractAuthors(volumeInfo);

                // Extract publisher
                String publisher = extractInformation(volumeInfo, "publisher");

                // Extract published date
                String publishedDate = extractInformation(volumeInfo, "publishedDate");

                // Extract description
                String description = extractInformation(volumeInfo, "description");

                //Extract page count
                int pageCount = extractInformation(volumeInfo, "pageCount", 0);

                // Extract info link
                String canonicalVolumeLink = extractInformation(volumeInfo, "canonicalVolumeLink");

                Volume volume = new Volume(title, authors, publisher, publishedDate, description, pageCount, canonicalVolumeLink);
                volumes.add(volume);
            }
            return volumes;
        } catch (Exception exception) {
            Log.e(LOG_TAG, exception.getMessage());
            return null;
        }
    }

    /**
     * Extract volume authors for JSON object
     * @param jsonObject
     * @return authors
     */
    private static String extractAuthors(@NotNull JSONObject jsonObject) {
        try {
            JSONArray authors = jsonObject.getJSONArray("authors");
            StringBuilder volumeAuthors = new StringBuilder();
            int j = 0;
            while (j < authors.length()) {
                Object author = authors.get(j);
                volumeAuthors.append(author.toString());
                j++;
                if (j != authors.length()) {
                    volumeAuthors.append(", ");
                }
            }
            return volumeAuthors.toString();
        } catch (JSONException jsonException) {
            // Log information about exception
            Log.e(LOG_TAG, "Exception in extracting authors: " + jsonException.getMessage());
            return MESSAGE_MISSING_INFORMATION;
        }
    }

    /**
     * Extract information from JSON object
     * @param jsonObject
     * @param information required
     * @return information
     */
    private static String extractInformation(@NotNull JSONObject jsonObject, @NotNull String information) {
        try {
            return jsonObject.getString(information);
        } catch (JSONException jsonException) {
            // Log information about exception
            Log.e(LOG_TAG, "Exception in extracting information: " + jsonException.getMessage());
            return MESSAGE_MISSING_INFORMATION;
        }
    }
    private static int extractInformation(@NotNull JSONObject jsonObject, @NotNull String information, int defaultValue) {
        try {
            return jsonObject.getInt(information);
        } catch (JSONException jsonException) {
            // Log information about exception
            Log.e(LOG_TAG, "Exception in extracting information: " + jsonException.getMessage());
            return defaultValue;
        }
    }
}

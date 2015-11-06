package net.binarycreations.apod.client;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import net.binarycreations.apod.domain.AstroItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * NASA Astronomy Picture of the Day API client.
 *
 * @author graham.
 */
public class NasaApodClient {

    private static final String TAG = NasaApodClient.class.getSimpleName();

    private static final String APOD_API_URL = "https://api.nasa.gov/planetary/apod";

    /** Formats a {@link Date} to yyyy-MM-dd as required by the API. */
    private static final SimpleDateFormat FORMATTER;

    static {
        FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /** An API key is required for authenitcation. API rate limiting still applies. */
    private final String mApiKey;

    private final OkHttpClient mClient;

    /**
     * Constructor.
     *
     * @param client used for to make HTTP requests.
     * @param apiKey required for authentication purposes.
     */
    public NasaApodClient(OkHttpClient client, String apiKey) {
        mClient = client;
        mApiKey = apiKey;
    }

    /**
     * Get the the astronomy pick of the day for the given date.
     *
     * @param day to retreive the pick for.
     * @return a new {@link AstroItem}.
     * @throws IOException when network connectivity issues occur.
     * @throws ApiError when an unexpected response occurs.
     */
    public AstroItem requestAstronomyPick(Date day) throws IOException, ApiError {
        Request request = new Request.Builder()
                .url(APOD_API_URL + "?api_key=" + mApiKey + "&date=" + FORMATTER.format(day)).build();

        Response response = mClient.newCall(request).execute();
        return parseResponse(response, day);
    }

    private AstroItem parseResponse(Response response, Date day) throws IOException, ApiError {
        AstroItem result = null;

        if (response.isSuccessful()) {
            try {
                result = fromJson(response.body().string(), day);
            } catch (JSONException jsonEx) {
                Log.e(TAG, "Unable to parse response body", jsonEx);
                throw new ApiError("Unable to parse response body");
            }
        } else {
            Log.e(TAG, "Bad response code : " + response.code());
            throw new ApiError("Bad response code : " + response.code());
        }

        return result;
    }

    private AstroItem fromJson(String responseBody, Date day) throws JSONException {
        JSONObject responseJson = new JSONObject(responseBody);
        String title = responseJson.getString("title");
        String explanation = responseJson.getString("explanation");
        String url = responseJson.getString("url");
        AstroItem.MediaType type = AstroItem.MediaType.valueOf(responseJson.getString("media_type").toUpperCase());
        return new AstroItem(title, explanation, url, type, day);
    }
}

package cf.vandit.movie_app.network.search;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cf.vandit.movie_app.utils.Constants;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<SearchResponse> {
    private Context mContext;

    private String mQuery;
    private String mPage;

    public SearchAsyncTaskLoader(Context context, String query, String page) {
        super(context);
        this.mContext = context;
        this.mQuery = query;
        this.mPage = page;
    }

    @Override
    public SearchResponse loadInBackground() {

        try {
            mQuery = mQuery.trim();
            mQuery = mQuery.replace(" ", "+");

            String urlString = "https://api.themoviedb.org/3/" + "search/multi"
                    + "?"
                    + "api_key=" + Constants.API_KEY
                    + "&"
                    + "query=" + mQuery
                    + "&"
                    + "page=" + mPage;
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() != 200) return null;

            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String jsonString = "";
            while (scanner.hasNext()) {
                jsonString += scanner.nextLine();
            }

            // Parse JSON
            JSONObject searchJsonObject = new JSONObject(jsonString);
            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setPage(searchJsonObject.getInt("page"));
            searchResponse.setTotalPages(searchJsonObject.getInt("total_pages"));
            JSONArray resultsJsonArray = searchJsonObject.getJSONArray("results");
            List<SearchResult> searchResults = new ArrayList<>();
            for (int i = 0; i < resultsJsonArray.length(); i++) {
                JSONObject result = (JSONObject) resultsJsonArray.get(i);
                SearchResult searchResult = new SearchResult();
                switch (result.getString("media_type")) {
                    case "movie":
                        searchResult.setId(result.getInt("id"));
                        searchResult.setPosterPath(result.getString("poster_path"));
                        searchResult.setName(result.getString("title"));
                        searchResult.setMediaType("movie");
                        searchResult.setOverview(result.getString("overview"));
                        try {
                            searchResult.setReleaseDate(result.getString("release_date"));
                        } catch (Exception e){
                            searchResult.setReleaseDate("N/A");
                        }
                        break;
                    case "tv":
                        searchResult.setId(result.getInt("id"));
                        searchResult.setPosterPath(result.getString("poster_path"));
                        searchResult.setName(result.getString("name"));
                        searchResult.setMediaType("tv");
                        searchResult.setOverview(result.getString("overview"));
                        try {
                            searchResult.setReleaseDate(result.getString("first_air_date"));
                        } catch (Exception e){
                            searchResult.setReleaseDate("N/A");
                        }
                        break;
                    case "person":
                        searchResult.setId(result.getInt("id"));
                        searchResult.setPosterPath(result.getString("profile_path"));
                        searchResult.setName(result.getString("name"));
                        searchResult.setMediaType("person");
                        searchResult.setOverview(null);
                        searchResult.setReleaseDate(null);
                        break;
                }
                searchResults.add(searchResult);
            }
            searchResponse.setResults(searchResults);

            return searchResponse;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
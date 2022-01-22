package cf.vandit.movie_app.request;

import cf.vandit.movie_app.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    public static ApiInterface getMovieApi() {
        return apiInterface;
    }
}

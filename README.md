# Cineapp

#### A Movie / TV-Show streaming app with elegant UI

|     |     |     |
| --- | --- | --- |
| ![](https://i.imgur.com/8r4B9lS.jpg) | ![](https://i.imgur.com/53HqjnI.jpg) | ![](https://i.imgur.com/bz31yDf.jpg) |

|     |     |
| --- | --- |
| ![](https://i.imgur.com/CyWblFh.jpg) | ![](https://i.imgur.com/sf10Heu.jpg) |

# Implementations
<ul>
<li>The Movie Database (TMDb)</li>
<li>Room Database</li>
<li>Retrofit - for networking</li>
<li>Glide - for images</li>
</ul>

# API Keys
Cineapp uses [The Movie DB](https://www.themoviedb.org/) API in order to fetch all the Movies and TV Shows Data and a Closed source API to stream movies and shows.

Now, open file named `Constants.java` and put your API Key's in there.
For example,

```java
public class Constants {
    public static final String API_KEY = "TMDB-API-KEY";
    public static final String YOUTUBE_API_KEY = "YOUTUBE-API-KEY";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String MOVIE_STREAM_URL = "CANNOT-BE-DISCLOSED";
    public static final String SERIES_STREAM_URL = "CANNOT-BE-DISCLOSED";
    public static final String IMAGE_LOADING_BASE_URL_1280 = "https://image.tmdb.org/t/p/w1280/";
    public static final String IMAGE_LOADING_BASE_URL_342 = "https://image.tmdb.org/t/p/w342/";
    public static final String IMAGE_LOADING_BASE_URL_780 = "https://image.tmdb.org/t/p/w780/";
    public static final String YOUTUBE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_THUMBNAIL_IMAGE_QUALITY = "/hqdefault.jpg";

    public static final String MOVIE_ID = "movie_id";
    public static final String SERIES_ID = "series_id";
    public static final String PERSON_ID = "person_id";

    public static final String VIEW_ALL_MOVIES_TYPE = "type_view_all_movies";
    public static final int POPULAR_MOVIES_TYPE = 2;
    public static final int TOP_RATED_MOVIES_TYPE = 3;

    public static final String VIEW_ALL_TV_SHOWS_TYPE = "type_view_all_tv_shows";
    public static final int ON_THE_AIR_TV_SHOWS_TYPE = 1;
    public static final int POPULAR_TV_SHOWS_TYPE = 2;
    public static final int TOP_RATED_TV_SHOWS_TYPE = 3;

    public static final int TAG_FAV = 0;
    public static final int TAG_NOT_FAV = 1;
}

```

**Download the APK from [releases tab](https://github.com/vendz/Cineapp/releases)**

import com.android.clix.model.response.CityDataResponse
import com.android.clix.model.response.ForeCastDataResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApi {

    @POST("/data/2.5/weather")
    suspend fun search(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): CityDataResponse

    @GET("/data/2.5/forecast")
    suspend fun forecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") cnt: Int,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): ForeCastDataResponse
}

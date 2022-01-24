package icsdiscover.crypto_mobile.data.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CBServiceBuilder {
    private const val COINBASEPRO_URL = "https://api.exchange.coinbase.com/"

    // Create Http Client
    private val okHttp = OkHttpClient.Builder()

    // Retrofit builder with json output
    private val jsonServicebuilder = Retrofit.Builder().baseUrl(COINBASEPRO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    fun <T> buildJsonService(serviceType: Class<T>): T {
        return jsonServicebuilder.build().create(serviceType)
    }
}
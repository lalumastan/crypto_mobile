package icsdiscover.crypto_mobile.data.service

import icsdiscover.crypto_mobile.data.model.CBAccount
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CBCryptoExchangeService {
    @GET("accounts")
    fun accounts(
        @Header("cb-access-key") cbAccessKey: String,
        @Header("cb-access-passphrase") cbAccessPassphrase: String,
        @Header("cb-access-sign") cbAccessSign: String,
        @Header("cb-access-timestamp") cbAccessTimestamp: String
    ): Call<List<CBAccount>>
}
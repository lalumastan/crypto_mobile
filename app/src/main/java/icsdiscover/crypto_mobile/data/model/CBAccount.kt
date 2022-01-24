package icsdiscover.crypto_mobile.data.model

data class CBAccount(
    val available: String,
    val balance: String,
    val currency: String,
    val hold: String,
    val id: String,
    val profile_id: String,
    val trading_enabled: Boolean
)
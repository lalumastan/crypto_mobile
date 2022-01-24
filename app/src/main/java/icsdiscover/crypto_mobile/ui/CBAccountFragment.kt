package icsdiscover.crypto_mobile.ui

import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import icsdiscover.crypto_mobile.MainActivity
import icsdiscover.crypto_mobile.data.model.CBAccount
import icsdiscover.crypto_mobile.data.service.CBCryptoExchangeService
import icsdiscover.crypto_mobile.data.service.CBServiceBuilder
import icsdiscover.crypto_mobile.databinding.FragmentCbAccountListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * A fragment representing a list of Items.
 */
class CBAccountFragment : Fragment() {

    companion object {
        const val cbSecret = "<YOUR_COINBASE_PRO_SECRET>"
        const val cbAccessKey = "<YOUR_COINBASE_PRO_KEY>"
        const val cbAccessPassphrase = "<YOUR_COINBASE_PRO_PASSPHRASE>"

        const val cbAccessType = "GET"
        const val cbAccessPath = "/accounts"
    }

    private var _binding: FragmentCbAccountListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCbAccountListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCBAccountList(requireActivity() as MainActivity, binding.cbAccountRecyclerView)
    }

    private fun setCBAccountList(mainActivity: MainActivity, recyclerView: RecyclerView) {
        val cbAccessTimestamp = "" + System.currentTimeMillis() / 1000L

        val destinationService =
            CBServiceBuilder.buildJsonService(CBCryptoExchangeService::class.java)

        val secretDecoded: ByteArray = Base64.decode(cbSecret, Base64.NO_WRAP)

        val sha256_HMAC: Mac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(secretDecoded, "HmacSHA256")
        sha256_HMAC.init(secretKey)

        var message: String = cbAccessTimestamp + cbAccessType + cbAccessPath
        var cbAccessSign: String =
            Base64.encodeToString(sha256_HMAC.doFinal(message.toByteArray()), Base64.NO_WRAP)

        val requestCall = destinationService.accounts(
            cbAccessKey,
            cbAccessPassphrase,
            cbAccessSign,
            cbAccessTimestamp
        )

        // Make network call asynchronously
        requestCall.enqueue(object : Callback<List<CBAccount>> {

            override fun onResponse(
                call: Call<List<CBAccount>>,
                response: Response<List<CBAccount>>
            ) {

                if (response.isSuccessful) {
                    var CBAccountList: List<CBAccount>? = response.body()
                    if (CBAccountList != null) {
                        recyclerView.layoutManager = GridLayoutManager(context, 2)
                        recyclerView.adapter = CBAccountRecyclerViewAdapter(
                            mainActivity,
                            CBAccountList.filter { s -> !"0.0000000000000000".equals(s.balance) })
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<CBAccount>>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
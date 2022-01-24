package icsdiscover.crypto_mobile.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import icsdiscover.crypto_mobile.MainActivity
import icsdiscover.crypto_mobile.R
import icsdiscover.crypto_mobile.data.model.CBAccount
import icsdiscover.crypto_mobile.databinding.FragmentCbAccountBinding

/**
 * [RecyclerView.Adapter] that can display a [CBAccount].
 */
class CBAccountRecyclerViewAdapter(
    private val mainActivity: MainActivity,
    private val values: List<CBAccount>
) : RecyclerView.Adapter<CBAccountRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCbAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cbAccount = values[position]
        holder.bind(cbAccount)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCbAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val currencyView: TextView = binding.cbAccountCurrency
        val balanceView: TextView = binding.cbAccountBalance
        val tradingEnabledView: TextView = binding.cbTradingEnabled

        fun bind(cbAccount: CBAccount) {
            currencyView.text = cbAccount.currency
            balanceView.text = cbAccount.balance
            balanceView.setTextColor(
                mainActivity.resources.getColor(
                    android.R.color.holo_orange_light,
                    mainActivity.theme
                )
            )
            tradingEnabledView.text =
                mainActivity.resources.getString(if (cbAccount.trading_enabled) R.string.tradable else R.string.not_tradable)
        }

        override fun toString(): String {
            return "ViewHolder(currencyView=${currencyView.text}, balanceView=${balanceView.text}, holdView=${tradingEnabledView.text})"
        }
    }

}
package com.inkubasi.hirehub.presentation.company.dashboard.history.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryCompany
import com.inkubasi.hirehub.coreapp.utils.Utils

class HistoryCompanyAdapter(
    private val history: (GetOfferHistoryCompany.Offer) -> Unit
) : RecyclerView.Adapter<HistoryCompanyAdapter.HistoryCompanyViewHolder>() {
    private var offerList: List<GetOfferHistoryCompany.Offer> = emptyList()

    fun updateData(newList: List<GetOfferHistoryCompany.Offer>) {
        offerList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCompanyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false)
        return HistoryCompanyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    override fun onBindViewHolder(holder: HistoryCompanyViewHolder, position: Int) {
        val currentItem = offerList[position]
        holder.bind(currentItem)
    }

    inner class HistoryCompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleName = itemView.findViewById<TextView>(R.id.title_name)
        private val dateTime = itemView.findViewById<TextView>(R.id.date)
        private val image = itemView.findViewById<ImageView>(R.id.photo)

        fun bind(offer: GetOfferHistoryCompany.Offer) {
            titleName.text = offer.name
            dateTime.text = offer.updatedAt?.let { Utils.formatDate(it) }
            Glide.with(itemView)
                .load(offer.imageUrl)
                .into(image)
            itemView.setOnClickListener {
                history.invoke(offer)
            }
        }
    }
}

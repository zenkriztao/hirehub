package com.inkubasi.hirehub.presentation.applicant.dashboard.history.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.GetOfferHistoryApplicant
import com.inkubasi.hirehub.coreapp.utils.Utils

class HistoryAdapter(private val onClick: (GetOfferHistoryApplicant.Offer) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryApplicantViewHolder>() {

    private var offerList: List<GetOfferHistoryApplicant.Offer> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryApplicantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_view, parent, false)
        return HistoryApplicantViewHolder(itemView)
    }

    inner class HistoryApplicantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(offer: GetOfferHistoryApplicant.Offer){
            val titleName = itemView.findViewById<TextView>(R.id.title_name)
            val dateTime = itemView.findViewById<TextView>(R.id.date)
            val image = itemView.findViewById<ImageView>(R.id.photo)

            titleName.text = offer.name
            dateTime.text = offer.updatedAt.let { it?.let { it1 -> Utils.formatDate(it1) } }

            Glide.with(itemView)
                .load(offer.imageUrl)
                .into(image)

            itemView.setOnClickListener {
                onClick.invoke(offer)
            }

        }
    }

    fun updateData(newList: List<GetOfferHistoryApplicant.Offer>){
        offerList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    override fun onBindViewHolder(holder: HistoryApplicantViewHolder, position: Int) {
        val currentItem = offerList[position]
        holder.bind(currentItem)
    }
}
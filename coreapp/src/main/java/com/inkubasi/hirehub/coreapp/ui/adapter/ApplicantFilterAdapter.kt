package com.inkubasi.hirehub.coreapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inkubasi.hirehub.coreapp.R
import com.inkubasi.hirehub.coreapp.data.source.remote.newresponse.ApplicantListResponse
import com.inkubasi.hirehub.coreapp.databinding.ItemApplicantFilterBinding

class ApplicantFilterAdapter : RecyclerView.Adapter<ApplicantFilterAdapter.ViewHolder>() {

    private var listData = ArrayList<ApplicantListResponse.Applicant>()
    var onItemClick: ((ApplicantListResponse.Applicant) -> Unit)? = null

    fun setData(newListData: List<ApplicantListResponse.Applicant>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_applicant_filter, parent, false))
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemApplicantFilterBinding.bind(itemView)

        fun bind(data: ApplicantListResponse.Applicant) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.imageUrl ?: R.drawable.ic_profile_prev)
                    .into(ivApplicant)
                tvApplicantName.text = data.name
                tvApplicantField.text = data.field
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

    class MyDiffUtil(
        private val oldList: List<ApplicantListResponse.Applicant>,
        private val newList: List<ApplicantListResponse.Applicant>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].username == newList[newItemPosition].username
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
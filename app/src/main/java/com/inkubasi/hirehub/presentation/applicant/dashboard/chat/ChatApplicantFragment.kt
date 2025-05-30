package com.inkubasi.hirehub.presentation.applicant.dashboard.chat

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.inkubasi.hirehub.R
import com.inkubasi.hirehub.coreapp.utils.Utils
import com.inkubasi.hirehub.databinding.FragmentChatApplicantBinding
import com.inkubasi.hirehub.presentation.chat.ChannelActivity
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.models.Filters
import io.getstream.chat.android.models.User
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModel
import io.getstream.chat.android.ui.viewmodel.channels.ChannelListViewModelFactory
import io.getstream.chat.android.ui.viewmodel.channels.bindView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatApplicantFragment : Fragment() {

    private lateinit var binding: FragmentChatApplicantBinding
    private val viewModel: ChatApplicantViewModel by viewModel()
    private lateinit var user: User
    private lateinit var userId: String
    private lateinit var userToken: String
    private val client: ChatClient by inject()
    private lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChatApplicantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = Dialog(requireContext())
        setupUser()
        bindViewModels()
        bindViewEvents()
    }

    private fun bindViewEvents() {
        with(binding) {
            channelListView.setChannelItemClickListener {
                val intent = Intent(requireContext(), ChannelActivity::class.java)
                intent.putExtra(ChannelActivity.CHANNEL_ID, it.cid)
                startActivity(intent)
            }
        }
    }

    private fun setupChannel() {
        context?.let {
            val filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("members", listOf(client.getCurrentUser()!!.id))
            )

            val viewModelFactory = ChannelListViewModelFactory(
                filter,
                ChannelListViewModel.DEFAULT_SORT
            )
            val listViewModel: ChannelListViewModel by viewModels { viewModelFactory }
            val listHeaderView: ChannelListHeaderViewModel by viewModels()


            initViewChat()
            listHeaderView.bindView(binding.channelListHeaderView, viewLifecycleOwner)
            listViewModel.bindView(binding.channelListView, viewLifecycleOwner)
        }
    }

    private fun initViewChat() {
        binding.channelListHeaderView.setOnlineTitle(getString(R.string.messages))
    }

    private fun bindViewModels() {
        viewModel.stateProfile.observe(this) {
            if (client.getCurrentUser() == null) {
                user = User(
                    userId,
                    extraData = mutableMapOf(
                        "name" to it.name.toString(),
                        "image" to it.imageUrl.toString(),
                        "field" to it.field.toString()
                    )
                )
            }
            client.connectUser(
                user,
                userToken
            ).enqueue { result ->
                if (result.isSuccess) {
                    setupChannel()
                } else {
                }
            }
        }
        viewModel.stateLoading.observe(this) {
            if (it) Utils.showLoading(dialog, "Loading")
            else Utils.hideLoading(dialog)
        }
    }

    override fun onDestroyView() {
        client.disconnect(false).enqueue()
        super.onDestroyView()
    }


    private fun setupUser() {
        viewModel.getUser().observe(this) {
            userId = it.id
            userToken = it.tokenStream
            viewModel.getProfileApplicantSelf(it.token)
        }
    }

}
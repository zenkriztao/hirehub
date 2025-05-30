package com.inkubasi.hirehub.presentation.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.inkubasi.hirehub.databinding.ActivityChannelBinding
import io.getstream.chat.android.ui.viewmodel.messages.*

class ChannelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChannelBinding
    private lateinit var channelId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        channelId = intent.getStringExtra(CHANNEL_ID)!!

        initView()
        bindViewEvents()
    }

    private fun bindViewEvents() {
        with(binding) {
            messageListHeaderView.setBackButtonClickListener {
                finish()
            }
        }
    }

    private fun initView() {
        val factory = MessageListViewModelFactory(this, channelId)

        val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
        val messageListViewModel: MessageListViewModel by viewModels { factory }
        val messageComposerViewModel: MessageComposerViewModel by viewModels { factory }

        messageListHeaderViewModel.bindView(binding.messageListHeaderView, this)
        messageListViewModel.bindView(binding.messageListView, this)
        messageComposerViewModel.bindView(binding.messageComposerView, this)
    }

    companion object {
        const val CHANNEL_ID = "CHANNEL_ID"
    }
}
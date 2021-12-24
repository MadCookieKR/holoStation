package com.madcookie.holostation

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.madcookie.holostation.data.Channel
import com.madcookie.holostation.data.Repository
import com.madcookie.holostation.databinding.DialogAddChannelBinding
import java.io.Serializable

interface AddChannelDialogListener : Serializable {
    fun onConfirm(added: List<Channel>)
}

class AddChannelDialog(
    private val addChannelDialogListener: AddChannelDialogListener,
    private val activeChannelList: List<Channel>
) : DialogFragment() {

    private lateinit var binding: DialogAddChannelBinding

    private val addChannelRvAdapter = AddChannelRvAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return DialogAddChannelBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.disabledChannelList.adapter = addChannelRvAdapter
        val activeChannelIdList = activeChannelList.map { it.id }
        Repository.channelList.filter { !activeChannelIdList.contains(it.id) }.also {
            addChannelRvAdapter.submitList(it)
        }

        dialog?.window?.also { window ->
            window.attributes = WindowManager.LayoutParams().apply {
                copyFrom(window.attributes)
                width = WindowManager.LayoutParams.MATCH_PARENT
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        addChannelDialogListener.onConfirm(addChannelRvAdapter.selected.toList())
    }
}
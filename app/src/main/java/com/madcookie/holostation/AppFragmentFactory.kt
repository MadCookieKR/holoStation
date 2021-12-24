package com.madcookie.holostation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.madcookie.holostation.data.Channel
import java.util.*

class AppFragmentFactory(
    private val addChannelDialogListener: AddChannelDialogListener,
    private val activeChannelList : ()->List<Channel>
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AddChannelDialog::class.java.name -> AddChannelDialog(addChannelDialogListener, activeChannelList())
            else -> super.instantiate(classLoader, className)
        }
    }
}
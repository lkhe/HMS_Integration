package com.eric.huawei.push

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eric.huawei.R


/**
 * A simple [Fragment] subclass.
 * Use the [PushFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PushFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eric, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = PushFragment()
    }
}

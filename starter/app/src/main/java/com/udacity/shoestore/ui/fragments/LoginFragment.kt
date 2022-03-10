package com.udacity.shoestore.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.ui.activities.MainActivity
import java.lang.Exception
import java.lang.ref.WeakReference

class LoginFragment : Fragment() {

    private var activityWeakReference: WeakReference<MainActivity?>? = null
    private var mBinding: FragmentLoginBinding? = null
    private var navController: NavController? = null

    private fun getActivityWeakReference(): MainActivity? {
        return try {
            activityWeakReference = if (activityWeakReference == null)
                WeakReference<MainActivity?>(requireActivity() as MainActivity?)
            else activityWeakReference
            activityWeakReference!!.get()
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_login,
            container,
            false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        mBinding?.btnLogin?.setOnClickListener {
            getActivityWeakReference()?.openFragment(navController!!,
                R.id.action_loginFragment_to_welcomeFragment, null)
        }

        mBinding?.btnSignup?.setOnClickListener {
            getActivityWeakReference()?.openFragment(navController!!,
                R.id.action_loginFragment_to_welcomeFragment, null)
        }
    }
}
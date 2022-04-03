package com.udacity.shoestore.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.activities.MainActivity
import com.udacity.shoestore.view_models.ShoeListViewModel
import java.lang.ref.WeakReference

class ShoeDetailFragment : Fragment() {
    private var mBinding: FragmentShoeDetailBinding? = null
    private var activityWeakReference: WeakReference<MainActivity?>? = null
    private var navController: NavController? = null
    private val viewModel: ShoeListViewModel by activityViewModels()

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
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_detail,
            container,
            false
        )
        mBinding?.shoe = Shoe()
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shoeItem.observe(viewLifecycleOwner) { shoe ->
            mBinding?.shoe = shoe
        }
        navController = Navigation.findNavController(view)
        mBinding?.btnCancel?.setOnClickListener {
            navController?.navigate(R.id.shoeListFragment)
        }
        mBinding?.btnSave?.setOnClickListener {
            if (mBinding?.shoeNameEt?.text!!.isNotEmpty()){
                if (mBinding?.companyEt?.text!!.isNotEmpty()){
                    if (mBinding?.sizeEt?.text!!.isNotEmpty()){
                        val shoeModel = Shoe(mBinding?.shoeNameEt?.text.toString(),
                            mBinding?.sizeEt?.text.toString().toDouble(),
                            mBinding?.companyEt?.text.toString(),
                            mBinding?.descriptionEt?.text.toString())

                        navController?.previousBackStackEntry?.savedStateHandle?.set("key", shoeModel)
                        navController?.popBackStack()

                    } else {
                        Toast.makeText(getActivityWeakReference(),
                            "Please enter shoe size!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(getActivityWeakReference(),
                        "Please enter company name!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(getActivityWeakReference(),
                    "Please enter shoe name!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
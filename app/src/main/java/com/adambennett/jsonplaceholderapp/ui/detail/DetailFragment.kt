package com.adambennett.jsonplaceholderapp.ui.detail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.list.ListFragment
import com.adambennett.jsonplaceholderapp.ui.utils.inflate
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_detail.text_view_body as textViewBody
import kotlinx.android.synthetic.main.fragment_detail.text_view_number_of_comments as textViewComments
import kotlinx.android.synthetic.main.fragment_detail.text_view_title as textViewTitle
import kotlinx.android.synthetic.main.fragment_detail.text_view_username as textViewUsername

class DetailFragment : Fragment() {

    private val displayModel by lazy(LazyThreadSafetyMode.NONE) {
        arguments!!.getParcelable(BUNDLE_DISPLAY_MODEL) as DetailDisplayModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = container?.inflate(R.layout.fragment_detail)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(displayModel) {
            textViewTitle.text = title
            textViewBody.text = body
            textViewComments.text = getString(R.string.detail_comments, commentCount)
            textViewUsername.text = getString(R.string.detail_username, username)
        }
    }

    companion object {

        private const val BUNDLE_DISPLAY_MODEL =
            "com.adambennett.jsonplaceholderapp.BUNDLE_DISPLAY_MODEL"

        fun newInstance(displayModel: DetailDisplayModel) = ListFragment().apply {
            arguments = Bundle().apply { putParcelable(BUNDLE_DISPLAY_MODEL, displayModel) }
        }
    }
}

@Parcelize
data class DetailDisplayModel(
    val title: String,
    val body: String,
    val commentCount: Int,
    val username: String
) : Parcelable
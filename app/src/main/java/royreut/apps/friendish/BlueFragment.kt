package royreut.apps.friendish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class BlueFragment : Fragment() {

    var textView:TextView? = null
    var title:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_blue, container, false)
        textView = view.findViewById(R.id.textView2)
        textView?.text = title ?: "BOOP"
        return view
    }

    companion object {
        const val TITLE = "MAMI"
        fun newInstance(title:String) =
            BlueFragment().apply {
                arguments = Bundle().apply {
                    putString(BlueFragment.TITLE, title)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE)
        }
    }

}
//
//companion object {
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BlueFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    @JvmStatic
//    fun newInstance(param1: String, param2: String) =
//        BlueFragment().apply {
//            arguments = Bundle().apply {
//                putString(ARG_PARAM1, param1)
//                putString(ARG_PARAM2, param2)
//            }
//        }
//}

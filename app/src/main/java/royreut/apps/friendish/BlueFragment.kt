package royreut.apps.friendish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation

class BlueFragment : Fragment() {

    private var textView:TextView? = null
    private var title:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_blue, container, false)

        val blueTitle = arguments?.let {
            BlueFragmentArgs.fromBundle(it).title
        }

        textView = view.findViewById(R.id.textView2)
        textView?.text = blueTitle ?: "BOOP"
        val button: Button = view.findViewById(R.id.blueFragmentBtn)
        button.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        return view
    }
}
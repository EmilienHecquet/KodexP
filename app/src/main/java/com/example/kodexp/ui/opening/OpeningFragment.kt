import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kodexp.databinding.FragmentOpeningBinding

class OpeningFragment : Fragment() {

    private lateinit var binding: FragmentOpeningBinding
    private lateinit var viewModel: OpeningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpeningBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OpeningViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}

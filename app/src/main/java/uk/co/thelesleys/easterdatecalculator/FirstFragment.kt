package uk.co.thelesleys.easterdatecalculator

//import androidx.navigation.fragment.findNavController
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uk.co.thelesleys.easterdatecalculator.databinding.FragmentFirstBinding
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Calendar.YEAR
import java.util.GregorianCalendar
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun calculateEasterDate(year: Int): Calendar {
        /*
    	 * Anonymous Gregorian Algorithm
    	 * http://en.wikipedia.org/wiki/Computus#Anonymous_Gregorian_algorithm
    	 */
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val L = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * L) / 451
        val month = (h + L - 7 * m + 114) / 31
        val day = (h + L - 7 * m + 114) % 31 + 1
        return GregorianCalendar(year, month - 1, day, 0, 0, 0)
    }

    private fun recalcDates(view: View, year: Int) {
        val dateEaster = calculateEasterDate(year)
//        Snackbar.make(view, "Easter = ", date.toString(), Snackbar.LENGTH_LONG)
//            .setAnchorView(R.id.plus_button_first)
//            .setAction("Action", null).show()
        val formatter = SimpleDateFormat("d MMM")
        val textEaster = formatter.format(dateEaster.time)
        binding.eastervalue.text = textEaster // .setText(textEaster)
        val dateShroveTuesday = dateEaster.clone() as Calendar
        dateShroveTuesday.add(Calendar.DATE, -47)
        val textShroveTuesday = formatter.format(dateShroveTuesday.time)
        binding.shrovevalue.text = textShroveTuesday // .setText(textShroveTuesday)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val today = Calendar.getInstance()
        val year = today.get(YEAR)
        val txt = year.toString()
        binding.edittext.setText(txt)
        recalcDates(view, year)

        binding.buttonFirst.setOnClickListener {view ->
            var year = binding.edittext.text.toString().toInt()
            year -= 1
            binding.edittext.setText(year.toString())
            recalcDates(view, year)
        }
//        binding.plusButtonFirst.setOnClickListener { view ->
//            Snackbar.make(view, "Plus Button Pressed", Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.plus_button_first)
//                .setAction("Action", null).show()
//        }
        binding.plusButtonFirst.setOnClickListener {view ->
            var year = binding.edittext.text.toString().toInt()
            year += 1
            binding.edittext.setText(year.toString())
            recalcDates(view, year)
        }
        binding.edittext.setOnKeyListener { view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                var year = binding.edittext.text.toString().toInt()
                recalcDates(view, year)
                //return@OnKeyListener true
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
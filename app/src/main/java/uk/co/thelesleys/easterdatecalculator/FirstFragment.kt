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

    private fun recalcDates(year: Int) {
        val dateEaster = calculateEasterDate(year)
        val formatter = SimpleDateFormat("d MMM")
        val textEaster = formatter.format(dateEaster.time)
        binding.eastervalue.text = textEaster // .setText(textEaster)
        val dateShroveTuesday = dateEaster.clone() as Calendar
        dateShroveTuesday.add(Calendar.DATE, -47)
        val textShroveTuesday = formatter.format(dateShroveTuesday.time)
        binding.shrovevalue.text = textShroveTuesday
    }

    override fun onViewCreated(vwFragment: View, savedInstanceState: Bundle?) {
        super.onViewCreated(vwFragment, savedInstanceState)

        val today = Calendar.getInstance()
        val current_year = today.get(YEAR)
        val txt = current_year.toString()
        binding.edittext.setText(txt)
        recalcDates(current_year)

        binding.buttonFirst.setOnClickListener { _ ->
            var year = binding.edittext.text.toString().toInt()
            year -= 1
            binding.edittext.setText(year.toString())
            recalcDates(year)
        }
        binding.plusButtonFirst.setOnClickListener { _ ->
            var year = binding.edittext.text.toString().toInt()
            year += 1
            binding.edittext.setText(year.toString())
            recalcDates(year)
        }
        binding.edittext.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                val year = binding.edittext.text.toString().toInt()
                recalcDates(year)
                true // handled
            }
            else {
                false // not handled
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
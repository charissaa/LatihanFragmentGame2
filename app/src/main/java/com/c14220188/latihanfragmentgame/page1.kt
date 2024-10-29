package com.c14220188.latihanfragmentgame

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [page1.newInstance] factory method to
 * create an instance of this fragment.
 */
class page1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inisialisasi
        val startNumber = arguments?.getString("DATA")?.toInt()
        val _myScore =view.findViewById<TextView>(R.id.myScore)
        val buttons = listOf(view.findViewById<TextView>(R.id.button1), view.findViewById<TextView>(R.id.button2),view.findViewById<TextView>(R.id.button3),view.findViewById<TextView>(R.id.button4),view.findViewById<TextView>(R.id.button5),view.findViewById<TextView>(R.id.button6),view.findViewById<TextView>(R.id.button7),view.findViewById<TextView>(R.id.button8),view.findViewById<TextView>(R.id.button9),view.findViewById<TextView>(R.id.button10))

        //Random angka
        val rndm = if (startNumber == null) {
            listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5).shuffled()
        } else {
            val range = (startNumber .. (startNumber +4)).toList()
            (range + range).shuffled()
        }
        for (i in buttons.indices) {
            buttons[i].text = "??"
            buttons[i].tag = rndm[i]

            buttons[i].setOnClickListener {
                onClickButton(buttons[i], _myScore)
            }
        }

        //Ke page 2 dari button Give Up
        val _btnGiveUp = view.findViewById<Button>(R.id.btnGiveUp)
        _btnGiveUp.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString("DATA", _myScore.text.toString())

            val mpage2 = page2()
            mpage2.arguments = mBundle

            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mpage2, page2::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        //Ke page 3 dengan button Set Number
        val _btnKePage3 = view.findViewById<Button>(R.id.btnPage3)
        _btnKePage3.setOnClickListener {
            val mpage3 = page3()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mpage3, page3::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        //
    }

    private val selectedCards = mutableListOf<TextView>()
    private fun onClickButton(card: TextView, myScore: TextView) {
        card.text = card.tag.toString()
        selectedCards.add(card)

        if (selectedCards.size == 2) {
            val card1 = selectedCards[0]
            val card2 = selectedCards[1]

            if (card1.tag == card2.tag) {
                myScore.text = (myScore.text.toString().toInt() + 10).toString()

                card1.isClickable = false
                card2.isClickable = false
            } else {
                myScore.text = (myScore.text.toString().toInt() - 5).toString()

                Handler(Looper.getMainLooper()).postDelayed({
                    card1.text = "??"
                    card2.text = "??"
                }, 1000)
            }

            selectedCards.clear()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment page1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            page1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
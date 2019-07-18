package work.kcs_labo.pinninglistview

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

interface PinningHeaderTouchListener {
  fun onTouchHeader(rv: RecyclerView, e: MotionEvent): Boolean
}
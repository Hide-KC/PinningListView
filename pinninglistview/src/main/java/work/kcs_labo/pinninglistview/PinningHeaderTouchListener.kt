package work.kcs_labo.pinninglistview

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Listener on Header taught.
 */
interface PinningHeaderTouchListener {
  fun onTouchHeader(rv: RecyclerView, e: MotionEvent): Boolean
}
package work.kcs_labo.pinninglistview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class PinningListView : RecyclerView {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

  private var itemTouchListener: OnItemTouchListener = object: OnItemTouchListener {
    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean { return false }
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

  }

  fun setOnHeaderTouchListener(listener: PinningHeaderTouchListener) {
    val itemTouchListener = this.itemTouchListener

    removeOnItemTouchListener(itemTouchListener)
    val headerTouchListener = object: OnItemTouchListener {
      override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

      override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return listener.onTouchHeader(rv, e)
      }

      override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    }
    addOnItemTouchListener(headerTouchListener)
    this.itemTouchListener = headerTouchListener
  }
}
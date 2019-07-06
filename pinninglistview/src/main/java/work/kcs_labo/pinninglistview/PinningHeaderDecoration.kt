package work.kcs_labo.pinninglistview

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class PinningHeaderDecoration(private val listener: PinningHeaderListener) : RecyclerView.ItemDecoration() {

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)

    val topView = parent.getChildAt(0) ?: return
    val adapterPosition = parent.getChildAdapterPosition(topView)
    if (adapterPosition == RecyclerView.NO_POSITION) return
    val currentHeaderPosition = listener.getCurrentHeaderPosition(adapterPosition) ?: return

    val layoutResId = listener.getHeaderLayout() ?: return

    val inflater = LayoutInflater.from(parent.context)
    val view = inflater.inflate(layoutResId, parent, false)

    if (view != null) {
      fixLayoutSize(parent, view)
      listener.bindHeaderData(view, currentHeaderPosition)
      drawHeader(c, view)
    }
  }

  private fun drawHeader(c: Canvas, view: View) {
    c.save()
    c.translate(0F, 0F)
    view.draw(c)
    c.restore()
  }

  private fun fixLayoutSize(parent: ViewGroup, view: View) {
    // Specs for parent (RecyclerView)
    val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

    // Specs for children (headers)
    val childWidthSpec =
      ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
    val childHeightSpec =
      ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

    view.measure(childWidthSpec, childHeightSpec)

    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
  }

  interface PinningHeaderListener {
    fun getCurrentHeaderPosition(adapterPosition: Int): Int?
    fun getHeaderLayout() : Int?
    fun bindHeaderData(header: View, adapterPosition: Int)
  }
}
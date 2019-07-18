package work.kcs_labo.pinninglistview

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PinningListDecoration(private val listener: PinningListListener) : RecyclerView.ItemDecoration() {

  private var header = Pair<Int?, View?>(null, null)

  fun getHeaderWidth(): Int? = header.second?.measuredWidth

  fun getHeaderHeight(): Int? = header.second?.measuredHeight

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)

    val topView = parent.getChildAt(0) ?: return
    val adapterPosition = parent.getChildAdapterPosition(topView)
    if (adapterPosition == RecyclerView.NO_POSITION) return
    val currentHeaderPosition = listener.getCurrentHeaderPosition(adapterPosition) ?: return

    val currentHeader = when (val layoutResId = listener.headerLayout ?: return) {
      header.first -> {
        header.second
      }
      else -> {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layoutResId, parent, false)
        header = Pair(layoutResId, view)
        view
      }
    } ?: return

    fixLayoutSize(parent, currentHeader)
    listener.bindHeaderData(currentHeader, currentHeaderPosition)

    val contactPoint = currentHeader.bottom
    val nextHeaderTop = getNextHeaderPoint(parent)

    if (nextHeaderTop != null
      && nextHeaderTop <= contactPoint
      && nextHeaderTop > 0 ) {
      moveHeader(c, currentHeader, nextHeaderTop)
    } else {
      drawHeader(c, currentHeader)
    }
  }

  private fun getNextHeaderPoint(parent: RecyclerView): Int? {
    for (childPosition in 0 until parent.childCount) {
      val child = parent.getChildAt(childPosition)
      val adapterPosition = parent.getChildAdapterPosition(child)
      if (listener.isHeader(adapterPosition)) return child.top
    }
    return null
  }

  private fun drawHeader(c: Canvas, view: View) {
    c.save()
    c.translate(0F, 0F)
    view.draw(c)
    c.restore()
  }

  private fun moveHeader(c: Canvas, currentView: View, nextViewTop: Int) {
    c.save()
    c.translate(0F, (nextViewTop - currentView.height).toFloat())
    currentView.draw(c)
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

  interface PinningListListener {
    val headerLayout: Int?
    fun isHeader(adapterPosition: Int): Boolean
    fun getCurrentHeaderPosition(adapterPosition: Int): Int?
    fun bindHeaderData(header: View, adapterPosition: Int)
  }
}
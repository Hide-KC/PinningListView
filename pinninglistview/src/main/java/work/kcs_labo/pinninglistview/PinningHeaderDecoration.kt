package work.kcs_labo.pinninglistview

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PinningHeaderDecoration(private val recyclerView: RecyclerView, private val listener: PinningHeaderListener) : RecyclerView.ItemDecoration() {
  init {

  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)

    val topChild = parent.getChildAt(0) ?: return
    val topChildPosition = parent.getChildAdapterPosition(topChild)
    if (topChildPosition == RecyclerView.NO_POSITION) return

    val currentHeader = getHeaderViewForItem(parent, topChildPosition)
    // TODO fixLayoutSize(parent, currentHeader)
    val contactPoint = currentHeader.bottom
    val childInContact = getChildInContact(parent, contactPoint) ?: return

    if (listener.isHeader(parent.getChildAdapterPosition(childInContact))) {
      moveHeader(c, currentHeader, childInContact)
    } else {
      drawHeader(c, currentHeader)
    }
  }

  private fun getHeaderViewForItem(parent: RecyclerView, itemPosition: Int): View {
    val headerPosition = listener.getHeaderPositionForItem(itemPosition)
    val layoutResId = listener.getHeaderLayout(headerPosition)
    val headerView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    listener.bindHeaderData(headerView, headerPosition)
    return headerView
  }

  private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
    for (position in 0 until parent.childCount) {
      if (parent.getChildAt(position).bottom <= contactPoint) {
        return parent.getChildAt(position)
      }
    }
    return null
  }

  private fun drawHeader(c: Canvas, header: View) {
    c.save()
    c.translate(0F, 0F)
    header.draw(c)
    c.restore()
  }

  private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
    c.save()
    c.translate(0F, (nextHeader.top - currentHeader.height).toFloat())
    currentHeader.draw(c)
    c.restore()
  }

  interface PinningHeaderListener {
    fun getHeaderPositionForItem(itemPosition: Int): Int
    fun getHeaderLayout(headerPosition: Int): Int
    fun bindHeaderData(header: View, headerPosition: Int)
    fun isHeader(itemPosition: Int): Boolean
  }
}
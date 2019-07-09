package work.kcs_labo.pinninglistview

import androidx.recyclerview.widget.RecyclerView

abstract class PinningListAdapter<E, VH : RecyclerView.ViewHolder>(private val list: List<E>) :
  RecyclerView.Adapter<VH>(),
  PinningListDecoration.PinningListListener {

  companion object Constants {
    const val HEADER = 200
  }

  override fun isHeader(adapterPosition: Int): Boolean {
    return getItemViewType(adapterPosition) == HEADER
  }

  override fun getCurrentHeaderPosition(adapterPosition: Int): Int? {
    var index = adapterPosition
    while (index > -1) {
      if (getItemViewType(index) == HEADER) return index
      index--
    }
    return null
  }

  override fun getItemCount(): Int {
    return list.size
  }
}
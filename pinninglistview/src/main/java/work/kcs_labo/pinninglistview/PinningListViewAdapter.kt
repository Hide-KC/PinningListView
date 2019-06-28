package work.kcs_labo.pinninglistview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PinningListViewAdapter<VH: RecyclerView.ViewHolder, E: PinningListViewType> : RecyclerView.Adapter<VH>() {
  val itemList = mutableListOf<E>()
  private lateinit var mergedList: MutableList<E>
  private lateinit var layoutMap: MutableMap<Int, Int>

  init {
    val viewTypes = itemList.distinctBy { item -> item.viewType }
    //TODO ItemとHeaderを混ぜたリスト
  }

  fun createView(parent: ViewGroup, viewType: Int): View {
    val resId = layoutMap[viewType] ?: throw NoSuchViewTypeException("There are no layout files: \"$viewType\"")
    return LayoutInflater.from(parent.context).inflate(resId, parent)
  }

  abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

  abstract override fun onBindViewHolder(holder: VH, position: Int)

  final override fun getItemCount(): Int {
    return mergedList.size
  }

  final override fun getItemViewType(position: Int): Int {
    return mergedList[position].viewType
  }

  class NoSuchViewTypeException(message: String) : Throwable(message)
}
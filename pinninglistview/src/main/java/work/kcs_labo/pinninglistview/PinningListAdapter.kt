package work.kcs_labo.pinninglistview

import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for PinningListView.
 * You can also use to orig RecyclerView adapter.
 * @param <E> Item object type parameter.
 * @param <T> Used for confirm Header section (Please set the type parameter matched by E::Property).
 * @param <H> Header object type parameter.
 * @param <VH> Extend RecyclerView.ViewHolder type parameter.
 */
abstract class PinningListAdapter<E, T, H, VH : RecyclerView.ViewHolder>(private val list: List<E>) :
  RecyclerView.Adapter<VH>(),
  PinningListListener<E, T, H> {

  /**
   * Entity of displayed list.
   * For mix Item object and Header object, type parameter is Any.
   */
  protected val innerList: MutableList<Any> = mutableListOf()
  private var extractor: PinningListHeaderExtractor<E, T, H>? = null

  companion object Constants {
    /**
     * Used in getItemViewType().
     */
    const val SECTION_HEADER = 200
  }

  init {
    innerList.addAll(list.map { item -> item as Any })
  }

  fun setExtractor(extractor: PinningListHeaderExtractor<E, T, H>?) {
    this.extractor = extractor
  }

  fun extractHeader() {
    extractor?.let {
      for (i in list.lastIndex downTo 0 step 1) {
        val nowItem = it.referenceHeaderProperty.get(list[i])
        when {
          i > 0 -> {
            val oneBeforeItem = it.referenceHeaderProperty.get(list[i - 1])
            if (nowItem != null && oneBeforeItem != null && nowItem != oneBeforeItem) {
              createHeaderElement(i, list[i])
            }
          }
          i == 0 -> {
            if (nowItem != null) {
              createHeaderElement(i, list[i])
            }
          }
        }
      }
    }
  }

  private fun createHeaderElement(index: Int, item: E) {
    val header = extractor?.createElement(item)
    header?.let {
      innerList.add(index, it as Any)
    }
  }

  override fun isHeader(adapterPosition: Int): Boolean {
    return getItemViewType(adapterPosition) == SECTION_HEADER
  }

  override fun getCurrentHeaderPosition(adapterPosition: Int): Int? {
    var index = adapterPosition
    while (index > -1) {
      if (isHeader(index)) return index
      index--
    }
    return null
  }

  override fun getItemCount(): Int {
    return innerList.size
  }
}
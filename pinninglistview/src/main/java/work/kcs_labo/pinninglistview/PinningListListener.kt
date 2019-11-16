package work.kcs_labo.pinninglistview

import android.view.View

/**
 * @param <E> Item object type parameter.
 * @param <T> Used for confirm Header section (Set the type parameter matched by E::Property<T>).
 * @param <H> Header object type parameter.
 */
interface PinningListListener<E, T, H> {
  val headerLayout: Int?
  fun isHeader(adapterPosition: Int): Boolean
  fun getCurrentHeaderPosition(adapterPosition: Int): Int?
  fun bindHeaderData(header: View, adapterPosition: Int)
}
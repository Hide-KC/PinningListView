package work.kcs_labo.pinninglistview

import kotlin.reflect.KProperty1

/**
 * @param <E> Item object type parameter.
 * @param <T> Used for confirm Header section (Please set the type parameter matched by E::Property).
 * @param <H> Header object type parameter.
 */
interface PinningListHeaderExtractor<E, T, out H> {
  val referenceHeaderProperty: KProperty1<E, T>
  fun createElement(sectionTopElement: E): H
}
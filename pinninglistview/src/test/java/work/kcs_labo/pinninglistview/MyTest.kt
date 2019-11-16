package work.kcs_labo.pinninglistview

import org.junit.Test
import kotlin.reflect.KProperty1

class MyTest {
  @Test
  fun adapterTest() {
    val list = listOf(
      Task(0, "A", "hoge"),
      Task(1, "A", "fuga"),
      Task(2, "B", "foo"),
      Task(3, "C", "bar")
    )

    val adapter = MyPinningListAdapter(list, R.layout.header_layout)
    adapter.setExtractor(object : PinningListHeaderExtractor<Task, String, HeaderObj>{
      //ヘッダーとして参照するプロパティを設定
      override val referenceHeaderProperty: KProperty1<Task, String>
        get() = Task::section

      override fun createElement(topElement: Task): HeaderObj {
        return HeaderObj(section = topElement.section)
      }
    })
  }
}
package work.kcs_labo.pinninglistview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyPinningListAdapter(private val list: List<Task>,
                           override val headerLayout: Int?
) : PinningListAdapter<Task, String, HeaderObj, RecyclerView.ViewHolder>(list) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun bindHeaderData(header: View, adapterPosition: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
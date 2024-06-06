import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.odal.wooco.Coach_Adapter
import com.odal.wooco.R
import com.odal.wooco.datamodels.CoachDataModel

class Menti_mypage_favorite_coachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menti_mypage_favorite_coach)

        val recyclerView: RecyclerView = findViewById(R.id.favorite_coach_recycler_view)

        val items = listOf(
            CoachDataModel("차우코", "강원대 00학과", "자격증 - 기사/기능사, 진로", "4.9"),
            CoachDataModel("별명 2", "학교/회사 2", "관심분야 2", "4.5"),
            CoachDataModel("별명 2", "학교/회사 2", "관심분야 2", "4.5")
        )

        val adapter = Coach_Adapter(items, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}

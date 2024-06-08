data class Request_Menti(
    val menti_request_name: String = "",  // 파이어베이스에서 데이터를 가져올 때 기본값이 필요합니다.
    val coach_receiverName: String = "",  // 파이어베이스에서 데이터를 가져올 때 기본값이 필요합니다.
    val message: String = "",  // 필요한 다른 필드 추가
    val userName: String = ""  // 사용자 이름 필드 추가
)

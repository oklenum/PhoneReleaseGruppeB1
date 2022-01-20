import android.content.Context
import android.util.Log
import com.groupb1.phonefreedom.receivers.PhoneCallReceiver
import java.util.*

class CallReceiver : PhoneCallReceiver() {

    protected override fun onIncomingCallReceived(ctx: Context?, number: String?, start: Date?) {
        Log.d("TAG", "CALL RECEIVED")
    }

    override fun onIncomingCallAnswered(ctx: Context?, number: String?, start: Date?) {
        Log.d("TAG", "CALL ANSWERED")
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Log.d("TAG", "CALL ENDED")
    }

    protected override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Log.d("TAG", "CALL STARTED")
    }

    protected override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Log.d("TAG", "CALL ENDED")
    }

    protected override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        Log.d("TAG", "CALL MISSED")
    }
}
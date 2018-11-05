package smartlock.josefferleite.smartlock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Broadcast1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context,TelaPergunta.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}

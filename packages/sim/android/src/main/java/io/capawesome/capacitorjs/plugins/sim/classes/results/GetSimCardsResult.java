package io.capawesome.capacitorjs.plugins.sim.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.sim.interfaces.Result;
import java.util.List;

public class GetSimCardsResult implements Result {

    @NonNull
    private final List<SimCard> simCards;

    public GetSimCardsResult(@NonNull List<SimCard> simCards) {
        this.simCards = simCards;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray simCardsArray = new JSArray();
        for (SimCard simCard : simCards) {
            simCardsArray.put(simCard.toJSObject());
        }
        JSObject result = new JSObject();
        result.put("simCards", simCardsArray);
        return result;
    }
}

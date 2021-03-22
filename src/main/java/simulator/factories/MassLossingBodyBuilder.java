package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Vector2D;
import simulator.model.bodies.MassLossingBody;

import java.util.Objects;

public class MassLossingBodyBuilder extends Builder<MassLossingBody> {

    private static final String DESCRIPTION = "A body that losses mass each time its moves!";

    @Override
    public MassLossingBody createTheInstance(JSONObject data) {
        Objects.requireNonNull(data);
        MassLossingBody body;
        if(super.type == TypeTag.MLB) {
            JSONArray jVelocity = data.getJSONArray("v");
            JSONArray jPosition = data.getJSONArray("p");
            String id = data.getString("id");
            Vector2D velocity = new Vector2D((double) jVelocity.get(0), (double) jVelocity.get(1));
            Vector2D position = new Vector2D((double) jPosition.get(0), (double) jPosition.get(1));
            double mass = data.getDouble("m");
            double lossFrequency = (double) data.get("freq");
            double lossFactor = (double) data.get("factor");
            body = new MassLossingBody.Builder().id(id).velocity(velocity).position(position).mass(mass)
                    .lossFactor(lossFactor).lossFrequency(lossFrequency)
                    .build();
            return body;
        } else
            throw new IllegalArgumentException("Typetag doesn't match with the builder constructor!");
    }

    @Override
    public JSONObject createData() {
        JSONObject template = new JSONObject();
        template.put("type", super.type.toString().toLowerCase());
        template.put("desc", DESCRIPTION);
        return template;
    }
}
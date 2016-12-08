package io.soulsdk.model.dto.event;

import log.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import io.soulsdk.model.dto.Chat;
import io.soulsdk.model.dto.Relation;
import io.soulsdk.model.dto.User;

/**
 * Created by admin on 21/07/16.
 */

public class EventTypeAdapter implements JsonSerializer<Event>, JsonDeserializer<Event> {
    private static final String TAG = "EventTypeAdapter";
    public static final String RECORD_ID_KEY = "recordId";
    public static final String TIME_KEY = "time";
    public static final String TYPE_KEY = "type";
    public static final String ACTION_KEY = "action";
    public static final String OBJECT_KEY = "object";

    @Override
    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Event event = new Event();
        int recordId = 0;
        double time = 0;
        EventType.Action action = EventType.Action.UNKNOWN;
        EventType.Type type = EventType.Type.UNKNOWN;
        EventObject object = new EventObject();

        if (json.isJsonObject()) {
            JsonObject content = json.getAsJsonObject();
            if (content.has(RECORD_ID_KEY))
                recordId = content.get(RECORD_ID_KEY).getAsInt();

            if (content.has(TIME_KEY))
                time = content.get(TIME_KEY).getAsDouble();

            if (content.has(TYPE_KEY)) {
                JsonObject jsonType = content.getAsJsonObject(TYPE_KEY);
                if (jsonType.has(ACTION_KEY)) {
                    String act = jsonType.get(ACTION_KEY).getAsString();
                    action = EventType.Action.valueOf(act.toUpperCase());
                }
                if (jsonType.has(OBJECT_KEY)) {
                    String t = jsonType.get(OBJECT_KEY).getAsString();
                    type = EventType.Type.valueOf(t.toUpperCase());
                }

                object.setEventType(type);
                JsonObject element = content.getAsJsonObject(OBJECT_KEY);

                switch (type) {
                    case CHAT:
                        Chat chat = context.deserialize(element, Chat.class);
                        object.setChatObject(chat);
                        break;

                    case ME:
                    case USER:
                        User user = context.deserialize(element, User.class);
                        object.setUserObject(user);
                        break;

                    case REACTION:
                        EventReaction react = new EventReaction();
                        String userId = "";
                        if (element.has("user") && element.get("user").getAsJsonObject().has("id"))
                            userId = element.get("user").getAsJsonObject().get("id").getAsString();
                        Relation relation = context.deserialize(element.get("receivedFromUser"), Relation.class);

                        react.setUserId(userId);
                        react.setRelation(relation);

                        object.setReactionObject(react);
                        break;

                    case ENDPOINT:
                        // {"recordId": 1475589467, "object": {"type": "newItems", "uri": "/users/recommendations"}, "type": {"action": "addition", "object": "endpoint"}, "time": 1475589467.097194}
                        EventEndpoint endpoint = context.deserialize(element, EventEndpoint.class);
                        object.setEndpointObject(endpoint);
                        break;

                    case UNKNOWN:
                        Log.e(TAG, "deserialize: Omitting serialization of type " + type);
                }
            }

            event.setTime(time);
            event.setRecordId(recordId);
            event.setAction(action);
            event.setType(type);
            event.setObject(object);
        }

        return event;
    }

    @Override
    public JsonElement serialize(Event src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add(TIME_KEY, new JsonPrimitive(src.getTime()));
        object.add(RECORD_ID_KEY, new JsonPrimitive(src.getRecordId()));

        JsonObject type = new JsonObject();
        type.add(ACTION_KEY, new JsonPrimitive(src.getAction().toString().toLowerCase()));
        type.add(OBJECT_KEY, new JsonPrimitive(src.getType().toString().toLowerCase()));

        object.add(TYPE_KEY, type);
        object.add(OBJECT_KEY, context.serialize(src.getObject()));

        return object;
    }
}

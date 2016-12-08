package io.soulsdk.network.managers;

import java.util.List;

import io.soulsdk.model.dto.event.Event;
import io.soulsdk.model.responses.EventsRESP;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 16/04/16
 */
public class EventManager {

    public static void saveLastEvent(EventsRESP eventsRESP) {
        List<Event> list = eventsRESP.getEvents();
        if (list != null && list.size() != 0) {
            Event last = list.get(list.size() - 1);
            // SoulStorage.saveInteger(SoulStorage.LAST_EVENT_RECORD_ID, last.getRecordId());
        }
    }

    public static int getLastEventRecordId() {
        // return SoulStorage.getInt(SoulStorage.LAST_EVENT_RECORD_ID, 0);
        return 0;
    }
}

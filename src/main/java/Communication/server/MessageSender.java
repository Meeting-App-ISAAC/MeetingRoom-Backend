package Communication.server;

import Communication.ReservationProvider;
import Communication.SessionProvider;
import Communication.server.models.FrontendRoom;
import Communication.server.models.ReservationJavaScript;
import Reservation.Room;
import shared.EncapsulatingMessageGenerator;
import shared.IEncapsulatingMessageGenerator;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageSender implements  IMessageSender{
    private IEncapsulatingMessageGenerator messageGenerator;

    public MessageSender() {
        messageGenerator = new EncapsulatingMessageGenerator();
    }


    public void sendTo(String sessionId, Object object)
    {
        String msg = messageGenerator.generateMessageString(object);
        sendToClient(getSessionFromId(sessionId), msg);
    }

    private Session getSessionFromId(String sessionId)
    {
        for(Session s : SessionProvider.getInstance().getSessions())
        {
            if(s.getId().equals(sessionId))
                return s;
        }
        return null;
    }

    private void sendToClient(Session session, String message)
    {
        try {
            session.getBasicRemote().sendText(message);
            System.out.println("Sent message to " + session.getId());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendReservationDump(){

        broadcast(ReservationDumpToday());

    }

    public void sendReservationDump(Session s ){

        sendTo(s.getId(), ReservationDumpToday());

    }


    public void broadcast(Object object)
    {
        for(Session s : SessionProvider.getInstance().getSessions()) {
            sendTo(s.getId(), object);
        }
    }

    private ArrayList<FrontendRoom> ReservationDumpToday() {
        // Get rooms from ReservationProvider
        List<Room> rooms = ReservationProvider.getInstance().getCollection().getAllRooms();

        ArrayList<FrontendRoom> frontendRooms = new ArrayList<>();
        // Convert rooms to frontendRooms
        for(Room r : rooms) {
            frontendRooms.add(FrontendRoom.Convert(r));
        }
        // Return list of frontendRooms (after reformatting negative start hours to 0)
        return reformatNegativeStart(frontendRooms);
    }

    private ArrayList<FrontendRoom> reformatNegativeStart(ArrayList<FrontendRoom> input) {
        // Change reservations that have a negative start hour to start at 0
        for(FrontendRoom r : input) {
            for(ReservationJavaScript rjs : r.reservations) {
                if(rjs.startHour < 0) {
                    rjs.setStartHour(0);
                }
                if(rjs.startHour + rjs.length > 24) {
                    rjs.setLength(24.0 - rjs.startHour);
                }
                System.out.println("[info] Reservation reformatted");
                System.out.println("[info] StartHour: " + rjs.startHour + " Length: " + rjs.length);
            }
        }
        return input;
    }
}

package CalendarResource;

import Reservation.Reservation;
import Reservation.Room;
import Reservation.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyCalender implements Calender {

    @Override
    public void createNewEvent(Reservation reservation) {

    }

    @Override
    public void updateEvent(Reservation reservation){
        System.out.println(reservation.getId());
    }

    @Override
    public List<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<Room>();
        Room room = new Room();
        room.setId(1);
        User user = new User();
        user.setName("Alex");
        Reservation reservation = new Reservation(user, LocalDateTime.now(), LocalDateTime.of(1999,9, 15,16,0));
        reservation.setId(1);
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        reservations.add(reservation);
        room.setReservations(reservations);
        rooms.add(room);
        return rooms;
    }
}

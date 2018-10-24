package Communication.server.models;

import Reservation.Reservation;

import java.time.ZoneId;
import java.util.ArrayList;

public class ReservationJavaScript {
    public int id;
    public String title;
    public double startHour;
    public double length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static ArrayList<ReservationJavaScript> Convert(ArrayList<Reservation> reservations) {
        ArrayList<ReservationJavaScript> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationJavaScript temp = new ReservationJavaScript();
            temp.id = reservation.getId();
            temp.title = reservation.getUser().getName();
            temp.length = (double) Math.round((double) (reservation.getEnd().atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli() - reservation.getStart().atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli()) / 3600) / 1000;
            temp.startHour = (double) reservation.getStart().getHour() + (double) reservation.getStart().getMinute() / 60 + (double) reservation.getStart().getSecond() / 3600;
            result.add(temp);

        }
        return result;
    }
}

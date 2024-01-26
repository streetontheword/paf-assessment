package vttp2023.batch4.paf.assessment.repositories;

public class Queries {



    public static final String SQL_ADD_BOOKINGS="""

        insert into bookings 
        (booking_id, listing_id, duration, email)

        values (?,?,?,?)
        """;
    
}

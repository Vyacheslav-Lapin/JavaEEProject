package functions;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class Functions {
    public static int yearsGoneFrom(Date from) {
        return Period.between(from.toLocalDate(), LocalDate.now())
                .getYears();
    }
}

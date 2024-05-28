package app.domain.contact;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Override
    public String toString() {
        return String.format("{\"id\" : %d, " +
                        "\"firstName\" : %s, " +
                        "\"lastName\" : %s, " +
                        "\"phoneNumber\" : %s}",
                id, firstName, lastName, phoneNumber);
    }
}

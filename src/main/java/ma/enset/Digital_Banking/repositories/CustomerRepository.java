package ma.enset.Digital_Banking.repositories;

import ma.enset.Digital_Banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> searchCustomer(String keyword);
}
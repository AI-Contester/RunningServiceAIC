package runningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    public List<Customer> response()
    {
        return repository.findAll();
    }

    @RequestMapping(value = "/customers/create")
    public Customer createCustomer(@RequestParam(value = "firstName") String firstName,
                               @RequestParam(value = "secondName") String secondName)
    {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(firstName,secondName));
        repository.insert(customers);
        return customers.get(0);
    }
}
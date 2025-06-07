package ma.enset.Digital_Banking;

import ma.enset.Digital_Banking.entities.AccountOperation;
import ma.enset.Digital_Banking.entities.CurrentAccount;
import ma.enset.Digital_Banking.entities.Customer;
import ma.enset.Digital_Banking.entities.SavingAccount;
import ma.enset.Digital_Banking.enums.AccountStatus;
import ma.enset.Digital_Banking.enums.OperationType;
import ma.enset.Digital_Banking.repositories.AccountOperationRepository;
import ma.enset.Digital_Banking.repositories.BankAccountRepository;
import ma.enset.Digital_Banking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankingApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository) {
		return args -> {
			// Create customers
			Stream.of("salima", "joudia", "lili").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name.toLowerCase() + "@gmail.com");
				customerRepository.save(customer);
			});

			// Create a Current Account for each customer
			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 10000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);
			});

			// Create a Saving Account for each customer
			customerRepository.findAll().forEach(customer -> {
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 10000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(acc -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 12000);
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}
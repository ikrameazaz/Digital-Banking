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
	CommandLineRunner run(CustomerRepository customerRepository,
						  BankAccountRepository bankAccountRepository,
						  AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Ikrame", "fatima", "karima").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				// Compte courant
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.ACTIVATED);
				currentAccount.setOverDraft(10000);
				currentAccount.setCustomer(customer);
				bankAccountRepository.save(currentAccount);

				// Compte épargne
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.ACTIVATED);
				savingAccount.setInterestRate(1.2);
				savingAccount.setCustomer(customer);
				bankAccountRepository.save(savingAccount);
			});

			// Ajout d'opérations bancaires à tous les comptes
			bankAccountRepository.findAll().forEach(bankAccount -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 1000);
					accountOperation.setDescription("Operation description");
					accountOperation.setBankAccount(bankAccount);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}

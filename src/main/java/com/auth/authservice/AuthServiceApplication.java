package com.auth.authservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class AuthServiceApplication {

	@Bean
	CommandLineRunner demo(AccountRepository accountRepository) {
		return args -> {
			Stream.of("jlong.spring", "dsyer.cloud", "pwebb.boot", "rwinch.security")
					.map( tpl -> tpl.split(","))
					.forEach(tpl -> accountRepository.save(new Account()));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}

@Service
class AccountUserDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	public AccountUserDetailsService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
}

interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByUsername(String username);
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Account {
	public Account() {
	}

	public Account(String username, String password, boolean active) {
		this.username = username;
		this.password = password;
		this.active = active;
	}

	@Id
	@GeneratedValue
	private Long id;
	private String username, password;
	private boolean active;
}
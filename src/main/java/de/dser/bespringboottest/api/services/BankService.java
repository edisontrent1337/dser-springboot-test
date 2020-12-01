package de.dser.bespringboottest.api.services;

import com.google.common.collect.Lists;
import de.dser.bespringboottest.entities.Bank;
import de.dser.bespringboottest.api.repositories.BankRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/biz")
public class BankService {

	private final BankRepository bankRepository;

	public BankService(BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@GetMapping("/banks")
	public List<Bank> loadAllBanks() {
		return Lists.newArrayList(bankRepository.findAll().iterator());
	}

	public Optional<Bank> loadBank(long bankId) {
		return bankRepository.findById(bankId);
	}
}

package com.portal.exchangerate;

import com.portal.exchangerate.model.Currency;
import com.portal.exchangerate.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/currency")
public class ExchangerateApplication {

	@Autowired
	private final CurrencyRepository currencyRepository = null;

	public static void main(String[] args) {
		SpringApplication.run(ExchangerateApplication.class, args);
	}

	@GetMapping
	public List<Currency> getCurrency(){
		return currencyRepository.findAll();
	}

	record NewCurrencyRequest(String name, double amount){}

	@PostMapping
	public void processCurrencyReqest(@RequestBody NewCurrencyRequest request){
		Currency currency = new Currency();
		currency.setName(request.name);
		currencyRepository.save(currency);
	}

	@DeleteMapping("{currencyRequestId}")
	public void deleteCurrencyRequest(@PathVariable("currencyRequestId") int id){
		currencyRepository.deleteById(id);
	}

}

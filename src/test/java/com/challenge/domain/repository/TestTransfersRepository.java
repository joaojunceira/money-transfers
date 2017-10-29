package com.challenge.domain.repository;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.config.RepositoryModule;
import com.challenge.domain.model.Account;
import com.challenge.domain.model.Transfer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.Clock;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestTransfersRepository {

  @Inject
  private TransferRepository repository;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    this.repository = injector.getInstance(TransferRepository.class);
  }

  private Long createTransfer() {
    Transfer transfer = new Transfer();
    CurrencyUnit usd = Monetary.getCurrency("USD");
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency(usd).setNumber(100L).create();
    transfer.setAmount(amount);
    transfer.setDestinationAccountId(1L);
    transfer.setSourceAccountId(2L);
    transfer.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
    return repository.create(transfer);
  }

  @Test
  public void testCreationOfTransfer() {
    Long id = createTransfer();
    Transfer byId = repository.findById(id);
    Assert.assertNotNull(byId);
  }

  @Test
  public void testDeleteTransfer() {
    Long id = createTransfer();
    Integer delete = repository.delete(id);
    Assert.assertThat(delete, is(greaterThanOrEqualTo(1)));
    Transfer byId = repository.findById(id);
    Assert.assertNull(byId);
  }

  @Test
  public void testUpdateTransfer() {
    Long id = createTransfer();
    Transfer byId = repository.findById(id);
    Assert.assertThat("Is updated", repository.update(byId), greaterThanOrEqualTo(1));
    byId = repository.findById(id);
  }
}

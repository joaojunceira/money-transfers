package com.challenge.domain.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.config.RepositoryModule;
import com.challenge.domain.model.Transfer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestTransfersRepository {

  private TransferRepository repository;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    this.repository = injector.getInstance(TransferRepository.class);
  }

  private Transfer createTransferEntity() {
    Transfer transfer = new Transfer();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(100L).create();
    transfer.setAmount(amount);
    transfer.setDestinationAccountId(1L);
    transfer.setSourceAccountId(2L);
    transfer.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
    return transfer;
  }

  @Test
  public void testCreationOfTransfer() {
    //Arrange
    Transfer transfer = createTransferEntity();
    //Act
    Long id = repository.create(transfer);
    Optional<Transfer> byId = repository.findById(id);
    //Assert
    Assert.assertTrue(byId.isPresent());
  }

  @Test
  public void testDeleteTransfer() {
    //Arrange
    Transfer transfer = createTransferEntity();
    Long id = repository.create(transfer);
    //Act
    Integer delete = repository.delete(id);
    Optional<Transfer> byId = repository.findById(id);
    //Assert
    Assert.assertThat(delete, is(greaterThanOrEqualTo(1)));
    Assert.assertFalse(byId.isPresent());
  }

  @Test
  public void testUpdateTransfer() {
    //Arrange
    Transfer transfer = createTransferEntity();
    Long id = repository.create(transfer);
    Optional<Transfer> byId = repository.findById(id);
    Transfer transferToUpdate = byId.get();
    MonetaryAmount amount = transferToUpdate.getAmount();
    MonetaryAmount amountToUpdate = amount.multiply(2L);
    transferToUpdate.setAmount(amountToUpdate);
    //Act
    Integer updateResult = repository.update(byId.get());
    Transfer transferToCompare = repository.findById(id).get();
    //Assert
    Assert.assertThat("Is update successful", updateResult, is(1));
    Assert.assertThat("Is Amount updated", transferToCompare.getAmount(), equalTo(amountToUpdate));
  }
}

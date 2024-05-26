package rafalotto.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafalotto.picpay.entity.Transfer;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

}

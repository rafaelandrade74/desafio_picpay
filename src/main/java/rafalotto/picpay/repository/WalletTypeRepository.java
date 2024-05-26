package rafalotto.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rafalotto.picpay.entity.WalletType;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {

}

package platform;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface CodeRepository extends CrudRepository<Code, UUID> {

    List<Code> findFirst10ByIsTimeRestrictedAndIsViewsRestrictedOrderByDateDesc(boolean isTime, boolean isViews);

    Code findTopByOrderByDateDesc();
}


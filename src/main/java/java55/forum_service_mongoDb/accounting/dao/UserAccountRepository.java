package java55.forum_service_mongoDb.accounting.dao;

import java55.forum_service_mongoDb.accounting.model.UserAccount;
import java55.forum_service_mongoDb.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
}

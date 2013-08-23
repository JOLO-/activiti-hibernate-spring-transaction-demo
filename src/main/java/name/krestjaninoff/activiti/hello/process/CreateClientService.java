package name.krestjaninoff.activiti.hello.process;

import name.krestjaninoff.activiti.hello.core.Engine;
import name.krestjaninoff.activiti.hello.db.Client;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.hibernate.Session;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CreateClientService implements JavaDelegate {

    private Engine engine;

//    @Transactional(value="transactionManager", propagation = Propagation.REQUIRED)
	public void execute(DelegateExecution execution) throws Exception {
		//create client
        Client client = new Client();
        client.setFirstName("Miroslav");
        client.setLastName("Mironov");
        client.setMale(true);

        //save client into app and Activiti databases
        this.engine = new Engine();

        //manual transactioning
        TransactionStatus transactionStatus = startTransaction();
        engine.getHibernateSession().save(client);
		  execution.setVariable("client", client);
        execution.setVariable("clientStatus", "FREE");
        rollback(transactionStatus);

//        TODO: uncomment this section and @Transactional annotation to use container driven transactions
        //auto-transactioning
//        Session session = engine.getHibernateSession();
//        if (session == null)
//            throw new IllegalStateException("Session shouldn't be NULL!");
//
//        session.save(client);
//        execution.setVariable("clientStatus", "FREE");
//        throw new RuntimeException("Chelabinskiy rollback");
	}

    //============
    //=== example
    //============
    protected TransactionStatus startTransaction() {
        PlatformTransactionManager manager = getTransactionManager();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus transaction = manager.getTransaction(def);

        return transaction;
    }

    protected void commit(TransactionStatus transaction) {
        PlatformTransactionManager manager = getTransactionManager();
        manager.commit(transaction);
    }

    protected void rollback(TransactionStatus transaction) {
        PlatformTransactionManager manager = getTransactionManager();
        manager.rollback(transaction);
    }

    private PlatformTransactionManager getTransactionManager() {
        return (PlatformTransactionManager) engine.getApplicationContext().getBean("transactionManager");
    }
}

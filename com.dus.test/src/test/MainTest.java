package test;

import com.dus.Dus;
import com.dus.DusConfig;
import com.dus.ISchemaRepository;
import com.dus.ISession;
import com.dus.base.schema.SEntity;
import com.dus.base.schema.SProperty;
import com.dus.provider.dummy.router.MyQueryRouter;
import com.dus.provider.dummy.router.MyTransactionRouter;
import com.dus.spi.router.IQueryRouter;
import com.dus.spi.router.ITransactionRouter;

import test.domain.Group;
import test.domain.Person;
import test.domain.User;

public class MainTest {

	public static void main(String[] args) {
		
		//Configure DUS service...
		DusConfig configs = new DusConfig();
		configs.setInstanceOf(ITransactionRouter.class, new MyTransactionRouter());
		configs.setInstanceOf(IQueryRouter.class, new MyQueryRouter());
		
		Dus.setConfig(configs);
		
		//register entities...
		ISchemaRepository repo = Dus.getSchemaRepository();
		repo.register(Person.class);
		repo.register(User.class);
		repo.register(Group.class);
		
		
		//---------------------------------------------------------------------------------------
		ISession session = Dus.createSession();
		
			Group group1 = session.create(Group.class, new Group.Builder("Admin"));
	
			Group group2 = session.create(Group.class, new Group.Builder("Researcher"));
			
			Person user = session.create(Person.class, new Person.Builder("shumy"));
			user.setName("Micael Pedrosa")
				.setAddress("Aveiro")
				.setPassword("password");
			
			user.getGroups().add(group1);
			user.getGroups().add(group2);
		
		session.commit();
		
		//reflection usage:
		SEntity schema = user.getId().schema;
		SProperty property = schema.getPropertyByName("login");
		String login = user.rGet(property);
		System.out.println("LOGIN: " + login);
		System.out.println(user);
				
		//finder usage:
		/*User user1 = session.find(User.Find.class).byId("id");
		
		System.out.println("USER GROUPS: ");
		for(Group group: user1.getGroups()) {
			System.out.println(group);
		}*/
	}

}

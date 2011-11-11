package com.dus;

public class Dus {
	public interface IBridge {
		ISchemaRepository createRepository();
		ISession createSession(ISchemaRepository repo, DusConfig configs);
	}
	
	private static final IBridge bridge;
	private static final ISchemaRepository repository;
	
	private static DusConfig configs;
	
	@SuppressWarnings("unchecked")
	private static IBridge createBridge(String clazz) {
		try {
			Class<IBridge> bClass = (Class<IBridge>) Class.forName(clazz);
			return bClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static {
		bridge = createBridge("com.dus.impl.Bridge");
		repository = bridge.createRepository();
	}
	
	
	//--------------------------------------------------------------------------------------------
	public static ISchemaRepository getSchemaRepository() {return repository;}
	
	public static DusConfig getConfig() {return configs;}
	public static void setConfig(DusConfig newConfigs) {configs = newConfigs;}
	
	public static ISession createSession() {
		return bridge.createSession(repository, configs);
	}
	

}

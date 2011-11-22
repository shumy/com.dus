package com.dus.context;

import com.dus.ISession;

public class Context {
	private static final ThreadLocal<ContextData> contextData = new ThreadLocal<ContextData>() {
		@Override
		protected ContextData initialValue() {return new ContextData();}
	};
	
	//used on context data transference...
	public static ContextData getData() {return contextData.get();}
	public static void setData(ContextData cd) {contextData.set(cd);}
	
	public static ISession getSession() {
		return contextData.get().getSession();
	}
}

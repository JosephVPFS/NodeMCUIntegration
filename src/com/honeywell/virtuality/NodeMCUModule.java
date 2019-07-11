package com.honeywell.virtuality;

public interface NodeMCUModule {
	public String doRead();
	public void doCommand(String command);
	public void doReadOrCommand(SocketReadWriteResolver resolver);
}

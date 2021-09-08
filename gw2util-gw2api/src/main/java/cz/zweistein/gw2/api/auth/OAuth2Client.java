package cz.zweistein.gw2.api.auth;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.UUID;

import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import cz.zweistein.gw2.api.util.ParametersMap;
import cz.zweistein.gw2.api.util.Utilities;

public class OAuth2Client {

	public static final String ENDPOINT = "http://asdfraergf.com";

	/**
	 *  Process is like this:
	 *  We launch http server on localhost as a reciever of auth response
	 *  Then we launch web browser with auth request URL
	 *  
	 *  Then, we wait for user to authorize and respond
	 *  
	 *  There is much that can go wrong.
	 * @throws IOException 
	 */
	public String getOAuth2Token() throws IOException {
		
		String token = null;
		String state = UUID.randomUUID().toString();
		int port = findFreePort();

		StringBuffer sb = new StringBuffer();
		sb.append(ENDPOINT);
		ParametersMap params = new ParametersMap();
		params.put("response_type", "code");
		params.put("redirect_uri", "http://localhost:" + port + "/");
		// params.put("scope", "authorization"); // FIXME
		params.put("state", state);
		Utilities.buildQuerryParams(sb, params);

		OAuth2ResponseContainer container = new OAuth2ResponseContainer(state, sb.toString());
		Server server = launchServer(container, port);
		launchBrowser(sb.toString());

		while (token == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			token = container.getToken();
		}

		server.stop();
		
		return token;
	}

	private Server launchServer(final Container responseListener, final int port) throws IOException {

		Server server = new ContainerServer(responseListener);
		Connection connection = new SocketConnection(server);
		SocketAddress address = new InetSocketAddress(port);
		connection.connect(address);

		return server;
	}

	private void launchBrowser(String url) {
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
			throw new UnsupportedOperationException("Desktop doesn't support the browse action");
		}

		try {
			java.net.URI uri = new java.net.URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e.getMessage());
		}

	}
	
	private int findFreePort() throws IOException {
		ServerSocket server = new ServerSocket(0);
		int port = server.getLocalPort();
		server.close();
		return port;
	}

}

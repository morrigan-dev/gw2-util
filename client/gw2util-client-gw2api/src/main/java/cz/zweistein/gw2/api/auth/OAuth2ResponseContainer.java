package cz.zweistein.gw2.api.auth;

import java.io.IOException;
import java.io.PrintStream;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

public class OAuth2ResponseContainer implements Container {

	private String okayState;
	private String token;
	private String retryUrl;

	public OAuth2ResponseContainer(String okayState, String retryUrl) {
		this.okayState = okayState;
		this.retryUrl = retryUrl;
	}

	@Override
	public void handle(Request req, Response res) {

		if (okayState.equals(req.getParameter("state")) && "authorization_code".equals(req.getParameter("grant_type"))) {
			token = req.getParameter("code");

			res.setCode(200);
			PrintStream body;
			try {
				body = res.getPrintStream();
				body.append("<html><head><title>GW2API</title></head><body>");
				body.append("GW2API Authorized with token: ").append("<b>").append(token).append("</b>");
				body.append("</body></html>");
				body.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			
			res.setCode(400);
			PrintStream body;
			try {
				body = res.getPrintStream();
				body.append("<html><head><title>GW2API</title></head><body>");
				body.append("GW2API Was not authorized, invalid request.");
				body.append(" <a href=\"").append(retryUrl).append("\">Retry</a>.");
				body.append("<form method=\"get\">");
				body.append("Or enter key manually: ");
				body.append("<input type=\"hidden\" name=\"").append("state").append("\" value=\"").append(okayState).append("\">");
				body.append("<input type=\"hidden\" name=\"").append("grant_type").append("\" value=\"").append("authorization_code").append("\">");
				body.append("<input type=\"text\" name=\"").append("code").append("\" value=\"").append("").append("\">");
				body.append("<input type=\"submit\" value=\"").append("enter").append("\">");
				body.append("</form>");
				body.append("</body></html>");
				body.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public String getToken() {
		return token;
	}

}

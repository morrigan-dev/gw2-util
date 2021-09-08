package cz.zweistein.gw2.api.util;

//The MIT License (MIT)
//
//Copyright (c) <2013> <Petr Prokop>
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.zweistein.gw2.api.dao.OnlineJsonDao;

public class Utilities {
	private static final Logger LOGGER = Logger.getLogger(Utilities.class.getName());

	public static void buildQuerryParams(StringBuffer sb, Map<String, Object> params) {
		if (params != null) {
			if (!params.isEmpty()) {
				sb.append("?");
			}
			boolean first = true;
			for (Entry<String, Object> param : params.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append("&");
				}
				sb.append(param.getKey());
				if (param.getValue() != null) {
					sb.append("=");
					try {
						sb.append(URLEncoder.encode(param.getValue().toString(), "UTF8"));
					} catch (UnsupportedEncodingException e) {
						LOGGER.info(e.toString());
						sb.append(param.getValue().toString());
					}
				}
			}
		}
	}

	/**
	 * Build query string for API
	 * 
	 * @param action
	 *            json action
	 * @param params
	 *            map of parameters
	 * @return string describing URL of request
	 */
	static String buildQuerryString(String action, Map<String, Object> params) {
		if (action == null) {
			throw new NullPointerException("action");
		}

		StringBuffer querry = new StringBuffer();

		LOGGER.log(Level.FINE, "Building querry " + action + " with params " + params);

		querry.append(OnlineJsonDao.STANDARD_URL).append(OnlineJsonDao.API_VERSION).append("/").append(action);

		buildQuerryParams(querry, params);

		return querry.toString();
	}

	/**
	 * Build query string for API
	 * 
	 * @param action
	 *            json action
	 * @param params
	 *            map of parameters
	 * @return string describing URL of request
	 * @throws RemoteException
	 */
	public static URL buildQuerryURL(String action, Map<String, Object> params) throws RemoteException {
		String querry = buildQuerryString(action, params);
		LOGGER.log(Level.FINE, "Built querry " + querry);
		try {
			return new URL(querry);
		} catch (MalformedURLException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

}

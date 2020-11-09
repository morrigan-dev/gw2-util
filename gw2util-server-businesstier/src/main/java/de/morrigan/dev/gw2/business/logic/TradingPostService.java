package de.morrigan.dev.gw2.business.logic;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import de.morrigan.dev.gw2.business.local.interfaces.ITradingPostService;

@Stateless
@Local(ITradingPostService.class)
public class TradingPostService implements ITradingPostService {

	/** Logger für Debug/Fehlerausgaben */
	private static final Logger LOG = Logger.getLogger(TradingPostService.class);

}

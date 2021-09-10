package de.morrigan.dev.gw2.business.logic;

import javax.ejb.Local;
import javax.ejb.Stateless;

import de.morrigan.dev.gw2.business.local.interfaces.ITradingPostService;

@Stateless
@Local(ITradingPostService.class)
public class TradingPostService implements ITradingPostService {

}

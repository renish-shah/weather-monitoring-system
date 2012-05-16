package com.reachout.ws.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.reachout.dao.CharityDao;
import com.reachout.ws.domain.Charity;
import com.reachout.ws.domain.DistributeServiceParam;
import com.reachout.ws.domain.Person;

@Service("distributedService")
public class DistributedService {

	protected static Logger logger = Logger.getLogger("service");

	public List<Charity> getAll(int noOfHopes) {
		logger.debug("Retrieving all Charities");

		if (noOfHopes != -1) {

			if (noOfHopes == 0) {
				CharityDao charityDao = new CharityDao();
				List<Charity> charities = new ArrayList<Charity>();

				try {
					charities.addAll(charityDao.listLocalCharities());
					// charities.addAll(charityDao.listRemoteCharities());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return charities;

			} else if (noOfHopes > 0) {
				CharityDao charityDao = new CharityDao();
				List<Charity> charities = new ArrayList<Charity>();

				try {
					charities.addAll(charityDao.listLocalCharities());
					noOfHopes=noOfHopes-1;
					charities.addAll(charityDao
							.listRemoteCharities(noOfHopes));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return charities;

			}

		} else if (noOfHopes == -1) {
			CharityDao charityDao = new CharityDao();
			List<Charity> charities = new ArrayList<Charity>();

			try {
				charities.addAll(charityDao.listLocalCharities());
				charities.addAll(charityDao.listRemoteCharities(-1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return charities;

		}
		return null;

	}

}

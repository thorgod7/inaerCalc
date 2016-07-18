package inaer.server;

import inaer.client.CalcService;
import inaer.server.EMF;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CalcServiceImpl extends RemoteServiceServlet implements CalcService {

	protected void storeRequest(double input, String output) {		
		CalcData data = new CalcData();
		data.setTimestamp(new Date());
		data.setInput(input);
		data.setOutput(output);		
		
		
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {			
			tx.begin();
			em.persist(data);		
			tx.commit();
		}
		finally {
			if (tx.isActive())
				tx.rollback();
			em.close();
		}
	}	
	protected void printDatastore() {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query q = em.createQuery("SELECT t FROM CalcData t ORDER BY timestamp");
		//List<CalcData> resList = q.getResultList();
		@SuppressWarnings("unchecked")
		List<CalcData> resList = q.getResultList();
	    for (CalcData dataObj : resList) {
	      System.out.println(dataObj.toString());
	    }
	    System.out.println("Size: " + resList.size());
	    tx.commit();
		em.close();		
	}
	public String toBin(long value) throws IllegalArgumentException {		
		String res;
		try {
			res = Long.toBinaryString(value);
			storeRequest(value, res);
			printDatastore();
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Error in binary conversion");
		}
		return res;
	}
}

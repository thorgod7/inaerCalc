package inaer.server;

import com.google.appengine.api.datastore.Key;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-07-18T13:46:12.423+0200")
@StaticMetamodel(CalcData.class)
public class CalcData_ {
	public static volatile SingularAttribute<CalcData, Key> key;
	public static volatile SingularAttribute<CalcData, Date> timestamp;
	public static volatile SingularAttribute<CalcData, Double> input;
	public static volatile SingularAttribute<CalcData, String> output;
}

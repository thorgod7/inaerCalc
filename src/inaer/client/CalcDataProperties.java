package inaer.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import inaer.shared.CalcDataSimple;

public interface CalcDataProperties extends PropertyAccess<CalcDataSimple> {
  @Path("timestamp")
  ModelKeyProvider<CalcDataSimple> key();

  ValueProvider<CalcDataSimple, String> timestamp();

  ValueProvider<CalcDataSimple, Double> input();

  ValueProvider<CalcDataSimple, String> output();
}

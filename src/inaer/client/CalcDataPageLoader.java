package inaer.client;

import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;

import inaer.shared.CalcDataSimple;

public class CalcDataPageLoader extends PagingLoader<PagingLoadConfig, PagingLoadResult<CalcDataSimple>> {
  public CalcDataPageLoader(DataProxy<PagingLoadConfig, PagingLoadResult<CalcDataSimple>> proxy) {
    super(proxy);
  }

  @Override
  public void loadData(PagingLoadConfig config) {
    super.loadData(config);
  }
}
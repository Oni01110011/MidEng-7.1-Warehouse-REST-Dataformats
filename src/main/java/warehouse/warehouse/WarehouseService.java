package warehouse.warehouse;

import org.springframework.stereotype.Service;
import warehouse.model.WarehouseData;

@Service
public class WarehouseService {
    public WarehouseData getWarehouseData(int wID) {
        WarehouseSimulator whs = new WarehouseSimulator();
        return whs.getData(wID);
    }
}

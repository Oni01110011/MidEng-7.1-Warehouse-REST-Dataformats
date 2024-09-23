package warehouse.warehouse;

import warehouse.model.ProductData;
import warehouse.model.WarehouseData;

import java.util.ArrayList;

public class WarehouseSimulator {
    public WarehouseData getData(int wID) {
        WarehouseData data = new WarehouseData();

        data.setWarehouseID(wID);
        data.setWarehouseName("Baichingers Lager");
        data.setWarehouseAddress("Teststreet 19-23");
        data.setWarehouseCity("Vienna");
        data.setWarehouseCountry("Austria");

        ProductData product1 = new ProductData("101", "ESP32 Devkit", "Electronics", 12, "Pieces");
        ProductData product2 = new ProductData("102", "Tablet", "Electronics", 2, "Pieces");

        ArrayList<ProductData> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        data.setProductData(products);
        return data;
    }
}

package warehouse.warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController {

	@Autowired
	private WarehouseService whs = new WarehouseService();

	@RequestMapping(value="/warehouse/{wID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public String warehouseDataJSON( @PathVariable int wID ) {
		return whs.getWarehouseData(wID).toJSON();
	}

	@RequestMapping(value="/warehouse/{wID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public String warehouseDataXML( @PathVariable int wID ) {
		return whs.getWarehouseData(wID).toXML();
	}
}
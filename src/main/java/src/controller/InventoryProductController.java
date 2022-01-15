package src.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import src.pojo.InventoryProduct;
import src.service.InventoryProductRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryProductController.class);

	@Autowired
	InventoryProductRepository repository;

	@GetMapping("/getAll")
	public ResponseEntity getAllObjects(){
		try {
			return ResponseEntity.ok(repository.getAll());
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		return ResponseEntity.internalServerError().body("Error");
	}

	@PostMapping("/createNew")
	public ResponseEntity<String> makeNew(@RequestBody MultiValueMap<String,String> params) {
		System.out.println(params);

		InventoryProduct obj = new InventoryProduct(-1,params.get("productName").get(0),Integer.valueOf(params.get("quantity").get(0)));
		try {
			boolean status = repository.createNewInventoryProduct(obj);
			if(status)
				return ResponseEntity.ok("Record Inserted");
		} catch (Exception e) {
			logger.error("Error: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
		return ResponseEntity.ok("Record Not Inserted");
	}

	@PutMapping(value="/update/{productId}")
	public ResponseEntity<String> updateExisting(@PathVariable Integer productId,@RequestBody String params){
		try {
			ObjectMapper ma = new ObjectMapper();
			InventoryProduct obj = ma.readValue(params, InventoryProduct.class);
			obj.setProductId(productId);
			int ans = repository.updateExistingById(obj);
			if(ans==0)
				return ResponseEntity.ok("Record With Id "+productId+" doesnt exist");
			else
				return ResponseEntity.ok("Record Updated");
		}
		catch (Exception e) {
			logger.error("Error: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
	}
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> deleteProd(@PathVariable Integer productId){
		try {
			boolean status = repository.deleteById(productId);
			if(status)
				return ResponseEntity.ok("Record Deleted");
			else
				return ResponseEntity.ok("Record Doesn't Exist");
		}
		catch (Exception e) {
			logger.error("Error: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
		}
		
	}
}

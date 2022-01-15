package src.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import src.pojo.InventoryProduct;

@Service
public class ExporterService {

	@Autowired
	InventoryProductRepository repo = new InventoryProductRepository();

	public ExporterService() {
	}
	
	public String csv() {
		List<InventoryProduct> list = repo.getAll();
		try {
			CsvMapper mapper = new CsvMapper();

			CsvSchema schema = CsvSchema.builder().setUseHeader(true)
					.addColumn("productId").addColumn("productName").addColumn("quantity")
					.build();

			ObjectWriter writer = mapper.writerFor(InventoryProduct.class).with(schema);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			writer.writeValues(bos).writeAll(list);
			String csv = new String(bos.toByteArray());


			return csv;
			
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}
}

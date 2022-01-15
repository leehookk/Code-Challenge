package src.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import src.service.ExporterService;

@Controller
public class ExportController {

	@Autowired
	ExporterService exporterService;
	
	SimpleDateFormat  sdf = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");

	@RequestMapping("/export")
	public ResponseEntity<byte[]> exportCSV(HttpServletResponse response) throws IOException {
		String fileName = "DataExport-"+sdf.format(new Date())+".csv";

		String data = exporterService.csv();
		//		System.out.println(csv);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/vnd.ms-excel");
		responseHeaders.add("Content-Disposition", "attachment; filename=" + fileName);
		
		return new ResponseEntity<>(data.getBytes("ISO8859-15"), responseHeaders, HttpStatus.OK);

	}

}

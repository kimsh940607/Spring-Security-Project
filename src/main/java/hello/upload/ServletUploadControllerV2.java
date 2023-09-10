package hello.upload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/servlet/v1")
public class ServletUploadControllerV2 {
	
	@Value("${file.dir}")
	String fileDir;
	
	@GetMapping("/upload")
	public String newFile() {
		return "upload-form";
	}
	
	@PostMapping("/upload")
	public String saveFileV1(HttpServletRequest request) throws IOException, ServletException {

		
		log.info("request = {}", request);
		
		log.info("item name = {}", request.getParameter("itmeName"));
		
		Collection<Part> parts = request.getParts();

		log.info("parts = {}", parts);
		
		for(Part part : parts) {
			log.info("======Part Start======");
			log.info("Part Name = {}", part.getName());
			Collection<String> partNames = part.getHeaderNames();
			for(String headerName : partNames) {
				log.info("header = {}, {}", headerName, part.getHeader(headerName));
			}

			InputStream inputStream = part.getInputStream();
			String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			
			log.info("body = {}", body);
			
			//
		}
		
		
		return "upload-form";
	}
}

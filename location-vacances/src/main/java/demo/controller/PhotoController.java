package demo.controller;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.LocationVacancesApplication;
import demo.model.File;
import demo.model.Logement;
import demo.model.Photo;
import demo.model.Utilisateur;
import demo.repository.LogementRepository;
import demo.repository.PhotoRepository;

@Controller 
public class PhotoController {
	
	@Autowired
	private PhotoRepository photoRepository;
	
	@Autowired
	private LogementRepository logementRepository;
	
	List<Photo> photoList = new ArrayList();
	Map<Integer, Photo> photoMap = new HashMap();
	Logement currentLog;
	private int idLogement;
	
	@RequestMapping("/addPhoto/{id}")
	public String requestNewPhoto(@PathVariable("id") Integer idLog,Model model, RedirectAttributes redirectAttribute) 
	{
		
		photoMap.clear();
		idLogement = idLog;
		return "redirect:/addPhoto";
	}
	
	@RequestMapping("/addPhoto")
	public String requestAddPhoto(Model model) 
	{		
		File fileModel = new File();
		currentLog = logementRepository.findOne(idLogement);
		photoList = currentLog.getPhotoList();
				
		if (photoList.size() !=0)
		{
			for(Photo p : photoList)
			{
				photoMap.put(p.getId(), p);
			}
		}
		model.addAttribute("photoList", photoMap);
		model.addAttribute("file", fileModel);
		
		return "/addPhoto";
	}
	
	@RequestMapping(value="/addPhoto" , method=RequestMethod.POST)
	public String requestPhoto(Model model,@Validated File file,BindingResult result) throws IllegalStateException, IOException 
	{
		try
		{
			if (result.hasErrors())
			{
				System.out.println("Error");
			}
			else
			{
				 MultipartFile multipartFile = file.getFile();
				 String folderPath = "src/main/resources/static/logement/" + currentLog.getId();
				 java.io.File folder = new java.io.File(folderPath);
				 if (!folder.exists())
					 if(folder.mkdir())
						 System.out.println("dossier crée");
				 String filePath = "src/main/resources/static/logement/" + currentLog.getId() +"/"+ multipartFile.getOriginalFilename();
				 java.io.File newFile = new java.io.File(filePath);
				 byte[] bytes = multipartFile.getBytes();
		            BufferedOutputStream stream =
		                    new BufferedOutputStream(new FileOutputStream(newFile));
		            stream.write(bytes);
		            stream.close();
				String pathImage = "../logement/" + currentLog.getId() +"/"+ multipartFile.getOriginalFilename();
				System.out.println("Image : " + pathImage);
				Photo photo = new Photo(newFile.getAbsolutePath());
				photo.setName(multipartFile.getOriginalFilename());
				photo.setPathImage(pathImage);
				currentLog.setPhotoList(photoList);
				photoRepository.save(photo);	
				photoMap.put(photo.getId(),photo);				
			}
		}
		catch(Exception e)
		{
			System.out.println("Erreur !!!");
		}	
		model.addAttribute("photoList", photoMap);
		return "/addPhoto";
	}
	
	@RequestMapping("/deletePhoto/{id}")
	public String requestDeletePhoto(@PathVariable("id") Integer photoId,Model model)
	{	
		photoMap.remove(photoId);		
		Photo photo = photoRepository.findOne(photoId);
		java.io.File fileDel = new java.io.File(photo.getPath());
		fileDel.delete();
		photoRepository.delete(photo);		
		model.addAttribute("photoList", photoMap);
		return "/addPhoto";
	}
	
	@RequestMapping(value="/retour")
	public String requestSavingProduct(RedirectAttributes redirectAttributes)  throws IOException
	{
		List<Photo> listPhoto = new ArrayList<Photo>();
		 for (Map.Entry mapentry : photoMap.entrySet()) {
			 listPhoto.add((Photo) mapentry.getValue());
	        }	
		 currentLog.setPhotoList(listPhoto);
		 logementRepository.save(currentLog);
		return "redirect:/adminHousing";
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.horas.web;

import com.horas.dto.Album;
import com.horas.dto.Moment;
import com.horas.dto.ResponseMessage;
import com.horas.service.AlbumService;
import com.horas.service.MomentService;
import com.horas.util.ApplicationContextUtils;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import com.horas.util.CommonUtils;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author jhon
 */
@Controller
public class MomentController extends ApplicationContextUtils{
 
    @Inject
    private MomentService momentService;
    @Inject 
    private AlbumService albumService;
    
    private String fileName;
    private List<Album> listPhoto= new ArrayList<Album>();
    
     @RequestMapping(value="/moments",method=RequestMethod.GET)
     @ResponseBody
     public List<Moment> getMoments(HttpServletRequest req){
        
         List<Moment> mom= momentService.getMoment();
         
         for(Moment moment:mom){
             if (moment.getUsername().equals(getUsername(req))){
                 moment.setYours(true);
             }
         }
         
         return mom;
     }
     
     
    /**
     *
     * @param moment
     * @return
     */
    @RequestMapping(value="/moments",method=RequestMethod.POST)
    @ResponseBody
    public ResponseMessage addMoments(@RequestBody Moment moment, HttpServletRequest req){
        HttpSession sess=req.getSession();
        List <Album> album= (List<Album>) sess.getAttribute("album");
        ResponseMessage rsp;
        Date today = Calendar.getInstance().getTime();  
        try{
            
            moment.setIdMoment(sys_guid());
            moment.setIdAlbum(null);
            moment.setUsername(getUsername(req));
            moment.setIpCreate("127.0.1.1");
            moment.setCreateDate(CommonUtils.dateFormat(today, "MM/dd/yyyy HH:mm:ss")); 
            
            momentService.insertMoment(moment); 
            for(Album alb: album){
                    alb.setIdMoment(moment.getIdMoment().toString());
                } 
            albumService.insertAllPhoto(album) ; 
        }catch(Throwable th){
            th.printStackTrace();
        }   
        sess.removeAttribute("album");
        listPhoto=new ArrayList<Album>();
        return new ResponseMessage(ResponseMessage.Type.success, album.get(0).getPhoto());
        
    }
    

    
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public void UploadFile(MultipartHttpServletRequest request,HttpServletResponse response) throws IOException {
        String extension,newPhoto;
        Album album=new Album();
        HttpSession sess=request.getSession();   
        
        try{         
            Iterator<String> itr=request.getFileNames();
            MultipartFile file=request.getFile(itr.next());
            fileName=file.getOriginalFilename();
            File serverFile = null;
            extension=fileName.substring(fileName.length()-3, fileName.length()); 
            
            album.setId(sys_guid().toString());
            album.setIdMoment(sys_guid().toString());
            album.setPhoto(fileName);
            album.setExtension(extension);
            album.setUsername(getUsername(request));
            
            newPhoto=album.getId().toString()+"."+extension;
            listPhoto.add(album);
            sess.setAttribute("album", listPhoto);
            
           // File dir= new File("D:/jhon/windows/apache-tomcat-7.0.57/webapps/images/");
             File dir = new File("/Users/jhon/server/apache-tomcat-7.0.57/webapps/images/");
     
             if (dir.isDirectory())
             {
                serverFile = new File(dir,fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes()); 
                File serverFileNew=new File(dir,newPhoto);
                FileUtils.copyFile(serverFile ,  serverFileNew);  
              
             }else {
                System.out.println("not");
             }
              
              serverFile.delete();
        }catch(Throwable th){
            th.printStackTrace();
        }
        
    }  
    
    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public List<Album> getFileAfterUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List <Album> album= new ArrayList<Album>();
        try{
             HttpSession sess=request.getSession();
             album= (List<Album>) sess.getAttribute("album");
        }catch(Throwable th){
            th.printStackTrace();
        }
        return album;
    }
}

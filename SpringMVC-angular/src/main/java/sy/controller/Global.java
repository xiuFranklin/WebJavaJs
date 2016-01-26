package sy.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@Controller
@RequestMapping("/global")
public class Global {
//	@Autowired CookieLocaleResolver resolver;
    @Autowired 
    SessionLocaleResolver resolver;
    /**
     * 语言切换
     * @param request
     * @param response
     * @param language
     * @return
     */
    @RequestMapping(value={"language.do"})
    public ModelAndView language(HttpServletRequest request,HttpServletResponse response,String language){
    	
    	language=language.toLowerCase();
    	if(language==null||language.equals("")){
    	return new ModelAndView("redirect:/");
    	}else{
    	if(language.equals("zh_cn")){
    	resolver.setLocale(request, response, Locale.CHINA );
    	}else if(language.equals("en")){
    	resolver.setLocale(request, response, Locale.ENGLISH );
    	}else{
    	resolver.setLocale(request, response, Locale.CHINA );
    	}
    	}
    	return new ModelAndView("/index");
    }
}

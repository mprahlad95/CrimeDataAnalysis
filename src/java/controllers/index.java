/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * @author Vigneet Sompura
 */
@Controller
public class index {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @RequestMapping("/index")
    public ModelAndView indexPage(Model model, HttpServletRequest request){
        if(request.getAttribute("error")!=null){
            model.addAttribute("error", request.getAttribute("error"));
        }
        return new ModelAndView("index");  
    }
    
    @RequestMapping("/Dashboard")
    public String login(Model model, HttpServletRequest request){
        String user = request.getParameter("user").toString();
        String pass = request.getParameter("pass").toString();
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  
        DAO dao=(DAO)ctx.getBean("udao"); 
        User u = dao.getUser(user, pass);
        String district = dao.getDistrict(u.getLatitude()+"", u.getLongitude()+"");
        int rowCount = dao.getNumberofTuples();
        List<StringPerc> normal = dao.getNormalDistribution();
        String cfp = dao.getCrimeFreeTime(district);
        String mcm = dao.getMaxCrimeMonth(district);
        String mcw = dao.getMaxCrimeWeek(district);
        String mcd = dao.getMaxCrimeDay(district);
        String mch = dao.getMaxCrimeHour(district);
        String mcp = dao.getMaxCrimePremise(district);
        String mc6m = dao.getCrimeLast6Months(district);
        List<StringPerc> hw = dao.getTop5Weapons(district);
        List<StringPerc> hc = dao.getTop5Crimes(district);
        StringPerc dcp = dao.getCrimesInDistrict(u.getLatitude()+"", u.getLongitude()+"");
        if(u !=null){
            model.addAttribute("user", u);
            model.addAttribute("tuples", rowCount);
            model.addAttribute("normal",normal);
            model.addAttribute("dcp",dcp);
            model.addAttribute("cfp", cfp);
            model.addAttribute("mcm", mcm);
            model.addAttribute("mcw", mcw);
            model.addAttribute("mcd", mcd);
            model.addAttribute("mch", mch);
            model.addAttribute("mcp", mcp);
            model.addAttribute("mc6m", mc6m);
            model.addAttribute("hw", hw);
            model.addAttribute("hc", hc);   
            return "home";
        }else{
            model.addAttribute("error", "Incorrect Username or Password");
            return "index";
        }

    }
    
    @RequestMapping("/insights")
    public String insights(Model model, HttpServletRequest request){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  
        DAO dao=(DAO)ctx.getBean("udao"); 
        String cfp = dao.getCrimeFreeTime();
        String mcm = dao.getMaxCrimeMonth();
        String mcw = dao.getMaxCrimeWeek();
        String mcd = dao.getMaxCrimeDay();
        String mch = dao.getMaxCrimeHour();
        String mcp = dao.getMaxCrimePremise();
        List<VictimProfile> vp = dao.getVictimProfile();
        List<String> hva = dao.getTop5Areas("VIOLENT", "desc");
        List<String> lva = dao.getTop5Areas("VIOLENT", "asc");
        List<String> hpa = dao.getTop5Areas("PROPERTY", "desc");
        List<String> lpa = dao.getTop5Areas("PROPERTY", "asc");
        
        List<StringPerc> hw = dao.getTop5Weapons();
        List<StringPerc> hc = dao.getTop5Crimes();
        
        model.addAttribute("cfp", cfp);
        model.addAttribute("mcm", mcm);
        model.addAttribute("mcw", mcw);
        model.addAttribute("mcd", mcd);
        model.addAttribute("mch", mch);
        model.addAttribute("mcp", mcp);
        model.addAttribute("vp", vp);
        model.addAttribute("hva", hva);
        model.addAttribute("lva", lva);
        model.addAttribute("hpa", hpa);
        model.addAttribute("lpa", lpa);
        model.addAttribute("hw", hw);
        model.addAttribute("hc", hc);
        return "insights";
    }
    
    @RequestMapping("trends")
    public String trends(Model model, HttpServletRequest request){
      ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  
        DAO dao=(DAO)ctx.getBean("udao"); 
        List<CountPerYear> cpy = dao.getCountPerYearbyCrime();
        model.addAttribute("cpy",cpy);
        List<CountPerYear> cpya = dao.getCountPerYearbyArea();
        model.addAttribute("cpya",cpya);
        
        List<StringPerc> dc = dao.getCrimesPerc();
        model.addAttribute("dc",dc);
        List<StringPerc> dct = dao.getCrimeTypePerc();
        model.addAttribute("dct",dct);
        List<StringPerc> dg = dao.getGenderPerc();
        model.addAttribute("dg",dg);
        List<StringPerc> dd = dao.getDescentPerc();
        model.addAttribute("dd",dd);
        List<StringPerc> ddow = dao.getWeekPerc();
        model.addAttribute("ddow",ddow);
        List<StringPerc> dm = dao.getMonthPerc();
        model.addAttribute("dm",dm);
        List<StringPerc> dw = dao.getWeaponPerc();
        model.addAttribute("dw",dw);
        List<StringPerc> dp = dao.getPremiseTypePerc();
        model.addAttribute("dp",dp);
      return "trends";  
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    
    @RequestMapping("/registration")
    public String registration(HttpServletRequest request, Model model){
        String user = request.getParameter("user").toString();
        String pass = request.getParameter("pass").toString();
        String repass = request.getParameter("repass").toString();
        String address = request.getParameter("address").toString();
        String DOB = request.getParameter("DOB").toString();
        String Gender = request.getParameter("gender").toString();
        String Ethnicity = request.getParameter("ethnicity").toString();
        String Lat = request.getParameter("Lat").toString();
        String Long = request.getParameter("Long").toString();
        
        jdbcTemplate.execute("INSERT INTO USERS VALUES('"+user+"','"+pass+"',TO_DATE('"+DOB+"','YYYY-MM-DD'),'"+Gender+"','"+Ethnicity+"','"+address+"',"+Long+","+Lat+")");
        model.addAttribute("user", user);
        return "login";
    }
    
    @RequestMapping("/calcresult")
    public String calcResult(HttpServletRequest request, Model model){
        String lat = request.getParameter("lat").toString();
        String lng = request.getParameter("long").toString();
        String time = request.getParameter("time").toString().split(":")[0];
        String age = request.getParameter("age").toString();
        String gender = request.getParameter("gender").toString();
        String ethnicity = request.getParameter("ethnicity").toString();
        String ageq;
        if(!age.matches("")){
            ageq = "((victim_age - "+age+") between -2 and 2)";
        }else{
            ageq = "victim_age>=0";
        }
        
        if(gender.matches("Any")){
            gender = "victim_sex like '%'";
        }else{
            gender = "victim_sex like '"+gender+"'";
        }
        
        if(ethnicity.matches("Any")){
            ethnicity = "victim_descent like '%'";
        }else{
            ethnicity = "victim_descent like '"+ethnicity+"'";
        }
            
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  
        DAO dao=(DAO)ctx.getBean("udao");
        float rf = Float.parseFloat(dao.getRiskFactor(lat, lng, ageq, gender, ethnicity));
        String Risk;
        if(rf>70)
            Risk = "HIGH";
        else if(rf>30)
            Risk = "MODERATE";
        else
            Risk = "LOW";
        
        float prf = Float.parseFloat(dao.getParkingRiskFactor(lat, lng, time));
        String PRisk;
        if(prf>70)
            PRisk = "HIGH";
        else if(prf>30)
            PRisk = "MODERATE";
        else
            PRisk = "LOW";
        model.addAttribute("rf", Risk);
        model.addAttribute("prf", PRisk);
        return "riskFactors";                    
    }
    
    @RequestMapping("heatmap")
    public String heatmap(Model model){
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");  
        DAO dao=(DAO)ctx.getBean("udao"); 
        List<Coordinates> coord = dao.getAllCoordinates();
        model.addAttribute("coord", coord);
        return "Heatmap";
    }
}

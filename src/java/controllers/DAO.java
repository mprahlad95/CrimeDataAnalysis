/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Vigneet Sompura
 */
public class DAO {
    @Autowired
    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {  
        this.template = template;  
    }  
    
    public User getUser(String Username, String Pass){  
    return template.query("select * from users where upper(username) like upper('"+Username+"') and pass='"+Pass+"'",new ResultSetExtractor<User>(){  
    @Override  
     public User extractData(ResultSet rs) throws SQLException,  
            DataAccessException {  
            User u =null;
            if(rs.next()){
                u=new User(rs.getString(1),rs.getString(2), rs.getString(4), rs.getString(3), rs.getString(5), rs.getString(6),rs.getFloat(7),rs.getFloat(8));  
            }
        return u;  
        }  
    });  
  }  
    
  public List<Coordinates> getAllCoordinates(){
      return template.query("select * from (select latitude,longitude,count(*) as total from incident group by latitude,longitude) where total between 40 and 900", new ResultSetExtractor<List<Coordinates>>(){
      @Override
      public List<Coordinates> extractData(ResultSet rs) throws SQLException, DataAccessException{
          List<Coordinates> coords;
          coords = new ArrayList<Coordinates>();
          while(rs.next()){
              coords.add(new Coordinates(rs.getFloat("longitude"), rs.getFloat("latitude"), rs.getInt(3)));
          }
          return coords;
      }
      });
  }
    
  public int getNumberofTuples(){
      return template.query("select sum(total) from (select count(*) as total from CRIME_TYPE "
              + "union select count(*) as total from WEAPON union select count(*) as total from DISTRICT_AREA "
              + "union select count(*) as total from INCIDENT union select count(*) as total from INCIDENT_CRIME "
              + "union select count(*) as total from INCIDENT_MO union select count(*) as total from MODUS_OPERANDI "
              + "union select count(*) as total from PREMISE union select count(*) as total from REPORTING_AREA)", new ResultSetExtractor<Integer>(){
            
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException{
                  
                  rs.next();
                  return rs.getInt(1);
              }
     });
  }
  
  public List<CountPerYear> getCountPerYearbyCrime(){
      return template.query("select crime, extract(year from time_occurred), count(*)"
              + " from incident join crime_type on incident.crime_code"
              + " = crime_type.crime_code group by extract(year from time_occurred), crime", new ResultSetExtractor<List<CountPerYear>>(){
              @Override
              public List<CountPerYear> extractData(ResultSet rs) throws SQLException, DataAccessException{
                  HashMap<String, int[]> hm = new HashMap<String, int[]>();
                  while(rs.next()){
                      if (hm.get(rs.getString(1))!=null){
                          int[] a = hm.get(rs.getString(1));
                          a[rs.getInt(2)%10] = rs.getInt(3);
                          hm.put(rs.getString(1), a);
                      }else{
                          int[] a = new int[9];
                          a[rs.getInt(2)%10] = rs.getInt(3);
                          hm.put(rs.getString(1), a);
                      }
                  }
                  
                  List<CountPerYear> cpy = new ArrayList<CountPerYear>();
                  for(String key: hm.keySet()){
                      String label = key;
                      int[] a = hm.get(label);
                      String data = a[0]+"";
                      for(int i=1; i<9;i++){
                          data = data+","+a[i];
                      }
                      CountPerYear c = new CountPerYear(label, data);
                      cpy.add(c);
                  }
                  return cpy;
              }
              
              });
  }
  
  public List<CountPerYear> getCountPerYearbyArea(){
      return template.query("select area_name, extract(year from time_occurred), count(*)"
              + " from incident natural join district_area natural join reporting_area group by extract(year from time_occurred), area_name", new ResultSetExtractor<List<CountPerYear>>(){
              @Override
              public List<CountPerYear> extractData(ResultSet rs) throws SQLException, DataAccessException{
                  HashMap<String, int[]> hm = new HashMap<String, int[]>();
                  while(rs.next()){
                      if (hm.get(rs.getString(1))!=null){
                          int[] a = hm.get(rs.getString(1));
                          a[rs.getInt(2)%10] = rs.getInt(3);
                          hm.put(rs.getString(1), a);
                      }else{
                          int[] a = new int[9];
                          a[rs.getInt(2)%10] = rs.getInt(3);
                          hm.put(rs.getString(1), a);
                      }
                  }
                  
                  List<CountPerYear> cpy = new ArrayList<CountPerYear>();
                  for(String key: hm.keySet()){
                      String label = key;
                      int[] a = hm.get(label);
                      String data = a[0]+"";
                      for(int i=1; i<9;i++){
                          data = data+","+a[i];
                      }
                      CountPerYear c = new CountPerYear(label, data);
                      cpy.add(c);
                  }
                  return cpy;
              }
              
              });
  }
  
  
  
  
  
  public String getCrimeFreeTime(){
    return template.query("with F as (select time_occurred, rownum as rank from (select * from (select time_occurred"
            + ", rownum as rank from(select distinct time_occurred from incident order by time_occurred desc)) where "
            + "rank>1 order by time_occurred)), T as (select time_occurred, rownum as rank from (select time_occurred, "
            + "rownum as rank from (SELECT distinct incident.TIME_OCCURRED FROM incident order BY time_occurred)) where "
            + "rank>1) select * from (select cast(F.time_occurred as DATE) as fromdate , cast(T.time_occurred as DATE) "
            + "as todate , FLOOR (CAST(T.time_occurred AS DATE) - CAST(F.time_occurred AS DATE)) as diff from F join T "
            + "on F.rank=T.rank order by diff desc) where rownum=1", new ResultSetExtractor<String>(){
                
                public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                    SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    rs.next();
                    return "No crime incidents occured from "+DateFormat.format(rs.getTimestamp(1))+" to "+ DateFormat.format(rs.getTimestamp(2))+", that is the highest crimefree period  ("+rs.getString(3)+" days) for Los Angeles.!";
                }
            });
  };
  
  public String getCrimeFreeTime(String district){
    return template.query("with F as (select time_occurred, rownum as rank from (select * from (select time_occurred"
            + ", rownum as rank from(select distinct time_occurred from incident where reporting_district="+district+" order by time_occurred desc)) where "
            + "rank>1 order by time_occurred)), T as (select time_occurred, rownum as rank from (select time_occurred, "
            + "rownum as rank from (SELECT distinct incident.TIME_OCCURRED FROM incident where reporting_district="+district+" order BY time_occurred)) where "
            + "rank>1) select * from (select cast(F.time_occurred as DATE) as fromdate , cast(T.time_occurred as DATE) "
            + "as todate , FLOOR (CAST(T.time_occurred AS DATE) - CAST(F.time_occurred AS DATE)) as diff from F join T "
            + "on F.rank=T.rank order by diff desc) where rownum=1", new ResultSetExtractor<String>(){
                
                public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                    SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    rs.next();
                    return "No crime incidents occured from "+DateFormat.format(rs.getTimestamp(1))+" to "+ DateFormat.format(rs.getTimestamp(2))+", that is the highest crimefree period  ("+rs.getString(3)+" days) for your area.!";
                }
            });
  };
  
  public String getMaxCrimeMonth(){
      return template.query("select * from (select month, round(cnt*100/total) from (select to_char(time_occurred, 'Month') "
              + "as month,count(*) as CNT from incident group by to_char(time_occurred, 'Month')) cross join (select count(*)"
              + " as total from incident) order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur in the month of "+rs.getString(1)+", which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeMonth(String district){
      return template.query("select * from (select month, round(cnt*100/total) from (select to_char(time_occurred, 'Month') "
              + "as month,count(*) as CNT from incident where reporting_district = "+district+" group by to_char(time_occurred, 'Month')) cross join (select count(*)"
              + " as total from incident where reporting_district = "+district+") order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur in the month of "+rs.getString(1)+", which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeDay(){
      return template.query("select * from (select day, round(cnt*100/total) from (select to_char(time_occurred, 'dd') "
              + "as day,count(*) as CNT from incident group by to_char(time_occurred, 'dd')) cross join (select count(*)"
              + " as total from incident) order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on date "+rs.getString(1)+" of a month, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeWeek(){
      return template.query("select * from (select day, round(cnt*100/total) from (select to_char(time_occurred, 'DAY') "
              + "as day,count(*) as CNT from incident group by to_char(time_occurred, 'DAY')) cross join (select count(*)"
              + " as total from incident) order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on "+rs.getString(1).trim()+"s, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeHour(){
      return template.query("select * from (select hour, round(cnt*100/total) from (select to_char(time_occurred, 'hh AM') "
              + "as hour,count(*) as CNT from incident group by to_char(time_occurred, 'hh AM')) cross join (select count(*)"
              + " as total from incident) order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur at "+rs.getString(1)+", which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeDay(String district){
      return template.query("select * from (select day, round(cnt*100/total) from (select to_char(time_occurred, 'dd') "
              + "as day,count(*) as CNT from incident where reporting_district = "+district+" group by to_char(time_occurred, 'dd')) cross join (select count(*)"
              + " as total from incident where reporting_district = "+district+") order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on date "+rs.getString(1)+" of a month, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimeWeek(String district){
      return template.query("select * from (select day, round(cnt*100/total) from (select to_char(time_occurred, 'DAY') "
              + "as day,count(*) as CNT from incident where reporting_district = "+district+" group by to_char(time_occurred, 'DAY')) cross join (select count(*)"
              + " as total from incident where reporting_district = "+district+") order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on "+rs.getString(1).trim()+"s, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimePremise(){
      return template.query("select * from (select premise, round(cnt*100/total) from (select description as premise"
              + " ,count(*) as CNT from incident natural join premise group by description) cross join (select count(*)"
              + " as total from incident ) order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on "+rs.getString(1).trim()+"s, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getMaxCrimePremise(String district){
      return template.query("select * from (select premise, round(cnt*100/total) from (select description as premise"
              + " ,count(*) as CNT from incident natural join premise where reporting_district = "+district+" group by description) cross join (select count(*)"
              + " as total from incident where reporting_district = "+district+") order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur on "+rs.getString(1)+"s, which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getCrimeLast6Months(String district){
      
              return template.query("select count(*) from incident where reporting_district="+district+" and time_occurred >= add_months(sysdate, -6)", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return rs.getString(1)+" crime incidents occurred in your district in last 6 months. ";
                  }
                  
              });
  }
  
  public String getMaxCrimeHour(String district){
      return template.query("select * from (select hour, round(cnt*100/total) from (select to_char(time_occurred, 'hh AM') "
              + "as hour,count(*) as CNT from incident where reporting_district = "+district+" group by to_char(time_occurred, 'hh AM')) cross join (select count(*)"
              + " as total from incident where reporting_district = "+district+") order by cnt desc) where rownum=1", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      rs.next();
                      return "Highest number of crime incidents occur at "+rs.getString(1)+", which is "+rs.getString(2)+"% of total crimes!";
                  }
                  
              });
  };
  
  public String getRiskFactor(String lat, String lng, String ageq, String gender, String ethnicity){
      return template.query("select reporting_district, round(((rank*100)/total),2) as risk_factor from (select reporting_district"
              + ", rownum as rank, total from (select reporting_district, ((A*10)+(G)+(D*9)+(AG*20)+(AD*180)+(GD*18)+(AGD*360)) as W_F "
              + "from ((select reporting_district, count(*) as AGD from incident where "+ageq+" and "+ethnicity+" and "+gender+" group by "
              + "reporting_district) natural join (select reporting_district, count(*) as AD from incident where "+ageq+" and "+ethnicity+" "
              + "group by reporting_district) natural join (select reporting_district, count(*) as AG from incident where "+ageq+" and "
              + gender+" group by reporting_district) natural join (select reporting_district, count(*) as GD from incident where "
              + gender+" and "+ethnicity+" group by reporting_district) natural join (select reporting_district, count(*) as A from incident"
              + " where "+ageq+" group by reporting_district) natural join (select reporting_district, count(*) as G from incident where "+gender+" "
              + "group by reporting_district) natural join (select reporting_district, count(*) as D from incident where "+ethnicity+" group by "
              + "reporting_district)) order by W_F) cross join (select count(distinct reporting_district) as total from incident)) where "
              + "reporting_district in (select reporting_district from (select reporting_district,count(*) from (select * from incident where "
              + "(latitude - ("+lat+") between -0.01447 and 0.01447) and (longitude-("+lng+") between -0.01447 and 0.01447)) group by "
              + "reporting_district order by count(*) desc) where rownum=1)", new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      if(rs.next()){
                      return rs.getString(2);
                      }else
                      {
                          return "0";
                      }
                  }
                  
              });
  }
  
  public String getParkingRiskFactor(String lat, String lng, String time){
      return template.query("select * from (select hour, round(rownum*100/24, 2) as riskfactor from (select to_char(time_occurred,"
              + " 'HH24') as hour, count(*) from incident where crime_code>479 and crime_code<521 and reporting_district"
              + " in (select reporting_district from (select reporting_district,count(*) from (select * from incident where (latitude - ("+lat+")"
              + " between -0.01447 and 0.01447) and (longitude-("+lng+") between -0.01447 and 0.01447)) group by reporting_district order by count(*)"
              + " desc) where rownum=1) group by to_char(time_occurred, 'HH24') order by count(*))) where hour ="+ time, new ResultSetExtractor<String>(){
              
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                      if(rs.next()){
                      return rs.getString(2);
                      }else
                      {
                          return "0";
                      }
                  }
                  
              });
  }
  
  public List<VictimProfile> getVictimProfile(){
      return template.query("select lage, lage+5 as rage, victim_sex, victim_descent, round(number_of_crimes*100/total,2 )"
              + "as prec from (select floor(victim_age/5)*5 as LAge, victim_sex, victim_descent, count(*) as Number_of_crimes "
              + "from incident where victim_sex <> 'Unknown' and victim_descent is not null and victim_age is not null group by"
              + " floor(victim_age/5)*5, victim_sex, victim_descent order by count(*) desc) cross join (select count(*) as total from incident)", new ResultSetExtractor<List<VictimProfile>>(){
                  public List<VictimProfile> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<VictimProfile> v = new ArrayList<VictimProfile>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              VictimProfile vp = new VictimProfile(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5));
                              v.add(vp);
                          }else {
                              return v;
                          }
                      }
                      return v;
                  }
              });
  }
  
  
  public List<String> getTop5Areas(String condition, String Order){
      return template.query("select area_name, count(*) from incident natural join crime_type natural join district_area "
              + "natural join reporting_area where type = '"+condition+"' group by area_name order by count(*) "+Order, new ResultSetExtractor<List<String>>(){
                  public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<String> areas = new ArrayList<String>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              areas.add(rs.getString(1));
                          }else {
                              return areas;
                          }
                      }
                      return areas;
                  }
              });
  }
  
  
  public List<StringPerc> getTop5Weapons(){
      return template.query("select description, round(crimes*100/total, 2) from (select description, count(*) as crimes from "
              + "incident natural join weapon where description not like '%UNKNOWN%' group by description order by count(*) desc)"
              + "cross join (select count(*) as total from incident)", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }else {
                              return ws;
                          }
                      }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getTop5Crimes(){
      return template.query("select crime, round(crimes*100/total, 2) as perc from (select crime, count(*) as crimes from"
              + " incident natural join crime_type where crime not like 'OTHER' group by crime order by count(*) desc) "
              + "cross join (select count(*) as total from incident)", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }else {
                              return ws;
                          }
                      }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getTop5Weapons(String district){
      return template.query("select description, round(crimes*100/total, 2) from (select description, count(*) as crimes from "
              + "incident natural join weapon where reporting_district = "+district+" and description not like '%UNKNOWN%' group by description order by count(*) desc)"
              + "cross join (select count(*) as total from incident where reporting_district = "+district+")", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }else {
                              return ws;
                          }
                      }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getTop5Crimes(String district){
      return template.query("select crime, round(crimes*100/total, 2) as perc from (select crime, count(*) as crimes from"
              + " incident natural join crime_type where reporting_district = "+district+" and crime not like 'OTHER' group by crime order by count(*) desc) "
              + "cross join (select count(*) as total from incident where reporting_district = "+district+")", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      for(int i=0; i<5; i++){
                          if(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }else {
                              return ws;
                          }
                      }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getNormalDistribution(){
      return template.query("select floor(crimes/200)*200, count(*) from (select reporting_district, count(*) as "
              + "crimes from incident group by reporting_district) group by floor(crimes/200)*200 "
              + "order by floor(crimes/200)*200", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                      }
                      return ws;
                  }
              });
  }
  
  public StringPerc getCrimesInDistrict(String lat, String lng){
      return template.query("select floor(cnt/200)*200, (100 - round(rank*100/total, 2)) from (select reporting_district, "
              + "cnt, rownum as rank from (select reporting_district, count(*) as cnt from incident group by "
              + "reporting_district order by count(*)))cross join (select count(distinct reporting_district) "
              + "as total from incident) where reporting_district in (select reporting_district from (select "
              + "reporting_district,count(*) from (select * from incident where (latitude - ("+lat+") between "
              + "-0.01447 and 0.01447) and (longitude-("+lng+") between -0.01447 and 0.01447)) group by "
              + "reporting_district order by count(*) desc) where rownum=1)", new ResultSetExtractor<StringPerc>(){
                  public StringPerc extractData(ResultSet rs) throws SQLException, DataAccessException{
                    rs.next();
                    return new StringPerc(rs.getString(1),rs.getString(2));      
                  }
              });
  }
  
  
  public String getDistrict(String lat, String lng){
      return template.query("select reporting_district from (select reporting_district,count(*) from (select * "
              + "from incident where (latitude - ("+lat+") between -0.01447 and 0.01447) and (longitude-("+lng+")"
              + " between -0.01447 and 0.01447)) group by reporting_district order by count(*) desc) where rownum=1", new ResultSetExtractor<String>(){
                  public String extractData(ResultSet rs) throws SQLException, DataAccessException{
                    rs.next();
                    return rs.getString(1);      
                  }
              });
  }
  
  
  public List<StringPerc> getCrimesPerc(){
      return template.query("select crime, round(crimes*100/total, 2) as perc from (select crime, count(*) as "
              + "crimes from incident natural join crime_type group by crime order by count(*) desc) cross join "
              + "(select count(*) as total from incident) where round(crimes*100/total, 2)<>0", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getCrimeTypePerc(){
      return template.query("select type, round(crimes*100/total, 2) as perc from (select type, count(*) as "
              + "crimes from incident natural join crime_type group by type order by count(*) desc) cross join "
              + "(select count(*) as total from incident) where round(crimes*100/total, 2)<>0", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getGenderPerc(){
      return template.query("select victim_sex, round(cnt*100/total ,2) from (select victim_sex, count(*) as cnt "
              + "from incident where victim_sex not like 'Unknown' group by victim_sex) cross join (select count(*) "
              + "as total from incident where victim_sex not like 'Unknown')", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getDescentPerc(){
      return template.query("select victim_descent, round(cnt*100/total ,2) from (select victim_descent, count(*) as cnt from "
              + "incident where victim_descent is not null and victim_descent not like 'Unknown' group by victim_descent) cross "
              + "join (select count(*) as total from incident where victim_descent is not null and victim_descent not like "
              + "'Unknown') where round(cnt*100/total,2)>0", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getWeaponPerc(){
      return template.query("select type, round(cnt*100/total,2) from (select type, count(*) as cnt from incident "
              + "natural join weapon where type not like 'OTHER' group by type) cross join (select count(*) as total "
              + "from incident natural join weapon where WEAPON_CODE is not null and type not like 'OTHER')", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getWeekPerc(){
      return template.query("select day, round(cnt*100/total, 2) from (select to_char(time_occurred, 'DAY') as day, "
              + "count(*) as cnt from incident group by to_char(time_occurred, 'DAY')) cross join "
              + "(select count(*) as total from incident)", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getMonthPerc(){
      return template.query("select day, round(cnt*100/total, 2) from (select to_char(time_occurred, 'Month') as day"
              + ", count(*) as cnt from incident group by to_char(time_occurred, 'Month')) cross join "
              + "(select count(*) as total from incident)", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
  public List<StringPerc> getPremiseTypePerc(){
      return template.query("select description, round(cnt*100/total, 2) from (select description, count(*) "
              + "as cnt from incident natural join premise group by description) cross join (select count(*) as "
              + "total from incident natural join premise) where round(cnt*100/total, 2)>1", new ResultSetExtractor<List<StringPerc>>(){
                  public List<StringPerc> extractData(ResultSet rs) throws SQLException, DataAccessException{
                      List<StringPerc> ws = new ArrayList<StringPerc>();
                      while(rs.next()){
                              StringPerc w = new StringPerc(rs.getString(1),rs.getString(2));
                              ws.add(w);
                          }
                      return ws;
                  }
              });
  }
  
}

package platform;


import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class CodeDataModel {
    public Map<String, Object> root = new HashMap<>();

    public CodeDataModel(){

    }
    public CodeDataModel(Code code) {
        root.put("code", code.getCode());
        root.put("date", code.getDate().toString());
        root.put("time", code.getTime());
        root.put("views", code.getViews());
    }
    public CodeDataModel(String code){
        root.put("code", code);
        root.put("date", LocalDateTime.now());
    }

    public CodeDataModel(String code,String date){
        root.put("code", code);
        root.put("date",LocalDateTime.parse(date));
    }
    public CodeDataModel addRestricts(Code code){
        root.put("trestrict", code.isTimeRestricted);
        root.put("vrestrict", code.isViewsRestricted);
        return this;
    }
    public  void setCode(String code){
        root.put("code", code);
    }
    public String getCode(){
        return (String)root.get("code");
    }
    public String getDate(){
        return (String) root.get("date");
    }
    public Integer getTime() {
        return (Integer) root.get("time");
    }
    public Integer getViews() {
        return (Integer) root.get("views");
    }
    public Boolean getTrestrict() {
        return (Boolean) root.get("trestrict");
    }
    public Boolean getVrestrict() {
        return (Boolean) root.get("vrestrict");
    }
}

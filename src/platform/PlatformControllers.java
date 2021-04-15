package platform;

import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;


@RestController
public class PlatformControllers {
    @Autowired
    private CRUDoperations crud;
    private Code recent;
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
    Template display;
    Template create;

    public PlatformControllers() throws IOException {
        configureTemplates();
        display = cfg.getTemplate("display.ftlh");
        create = cfg.getTemplate("create.ftlh");
    }

    private void configureTemplates() throws IOException{
        cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    @GetMapping(path = "/code/{N}")
    public ResponseEntity<String> requestHTMLcode(@PathVariable UUID N) throws TemplateException, IOException {
        Writer string = new StringWriter();
        try {
            recent = crud.getEntry(N);
            display.process(new CodeDataModel(recent).addRestricts(recent),string);
            return ResponseEntity.ok().header("content-type", "text/html")
                    .body(string.toString());
        }
        catch (NoSuchElementException e){
            Template error = cfg.getTemplate("NotFound.ftlh");
            string.write(error.toString());
        }
        return ResponseEntity.notFound().header("content-type","text/html")
                    .build();
    }

    @GetMapping(path = "api/code/{N}", produces = "application/json")
    public ResponseEntity<Map> requestJSONcode(@PathVariable UUID N){
        try {
            recent = crud.getEntry(N);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header("content-type","application/json")
                .body(new CodeDataModel(recent).root);
    }

    @GetMapping(path = "/code/new")
    public String requestBlankHTMLcode() throws IOException {
        Writer string = new StringWriter();
        string.write(create.toString());
        return string.toString();
    }

    @GetMapping(path = "/code/latest")
    public String getRecentHTMLcodes() throws IOException{

        Writer string = new StringWriter();
        Template latest = cfg.getTemplate("latestP1.ftlh");
        List<Code> codes = crud.getLatest();
        string.write(latest.toString());
        Template finalLatest = cfg.getTemplate("latestP2.ftlh");
        codes.forEach(code -> {
            try {
                finalLatest.process(code,string);
            } catch (TemplateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return string + "</body>\n" + "</html>";

    }

    @GetMapping(path = "api/code/latest", produces = "application/json")
    public @ResponseBody List<Map<String,Object>> getRecentJSONcodes(){
        List<Map<String,Object>> out = new ArrayList<>();
        crud.getLatest().forEach(code -> out.add(new CodeDataModel(code).root));
        return out;
    }

    @Autowired
    @PostMapping(path = "/api/code/new", consumes = "application/json")
    public ResponseEntity<String> responseJSONcode(@RequestBody Code newCode){
        newCode.setDate();
        if(!newCode.getCode().isEmpty()) {
            crud.createNewEntry(newCode.getCode(),newCode.getTimeInSeconds(), newCode.getViews());
        }
        else
            return ResponseEntity.ok().body("{}");
        return ResponseEntity.ok().header("Content-Type",
                "application/json").body("{\n" +
                                                     "  \"id\" : \""+ crud.getRecentID().toString() +"\"\n"+
                                                     "}");
    }

    @GetMapping(path = "/api/code/all")
    public String printall(){
        List<Code> codes = crud.getLatest();
        return codes.toString();
    }
}

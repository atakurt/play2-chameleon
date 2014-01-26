package controllers;

import play.*;
import play.mvc.*;
import com.degiske.play2.chameleon.Chameleon;

import java.util.Map;
import java.util.Set;

public class Application extends Controller {

    public static Result index() {

        String theme = request().getQueryString("theme");

        if(null == theme)
        {
            theme = "theme1";
        }

        return ok(Chameleon.render("views.html."+ theme + ".index", new Object[]{"Your theme is " + theme}));
    }

}

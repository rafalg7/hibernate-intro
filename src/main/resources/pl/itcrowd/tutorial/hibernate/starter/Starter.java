package pl.itcrowd.tutorial.hibernate.starter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Starter {

    @PostConstruct
    private void onCreate(){

    }
}

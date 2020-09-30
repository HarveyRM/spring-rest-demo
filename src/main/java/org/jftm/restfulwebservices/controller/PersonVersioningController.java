package org.jftm.restfulwebservices.controller;

import org.jftm.restfulwebservices.model.Name;
import org.jftm.restfulwebservices.model.PersonV1;
import org.jftm.restfulwebservices.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    /* THE FOLLOWING ARE 4 OPTIONS FOR VERSIONING AN API
    *       URI
    *       PARAMETER
    *       HEADER
    *       MEDIA TYPE (MIME TYPE)
    *
    * Each type has pros and cons and are used by different organizations
    *   GitHub uses Media Type versioning
    *   Microsoft uses Header versioning
    *   Twitter uses URI versioning
    *   Amazon uses Parameter versioning
    * */


    // URI VERSIONING
    @GetMapping("v1/person")
    public PersonV1 getPersonV1(){
        return new PersonV1("Charlie Brown");
    }

    // URI VERSIONING
    @GetMapping("v2/person")
    public PersonV2 getPersonV2(){
        return new PersonV2(new Name("Charlie", "Brown"));
    }

    // PARAMETER VERSIONING
    @GetMapping(value="/person/param", params="version=1")
    public PersonV1 getPersonParamV1(){
        return new PersonV1("Charlie Brown");
    }

    // PARAMETER VERSIONING
    @GetMapping(value="/person/param", params="version=2")
    public PersonV2 getPersonParamV2(){
        return new PersonV2(new Name("Charlie", "Brown"));
    }

    /* HEADER VERSIONING
        executed when a GET request comes in with a HEADER KEY set as X-API-VERSION with a value of 1 */
    @GetMapping(value="/person", headers = "X-API-VERSION=1")
    public PersonV1 getPersonV1Header(){
        return new PersonV1("Charlie Brown");
    }

    /* HEADER VERSIONING
        executed when a GET request comes in with a HEADER KEY set as X-API-VERSION with a value of 2 */
    @GetMapping(value="/person", headers = "X-API-VERSION=2")
    public PersonV2 getPersonV2Header(){
        return new PersonV2(new Name("Charlie", "Brown"));
    }


    /* PRODUCES VERSIONING -- ALSO CALLED ACCEPT HEADER VERSIONING OR MIME TYPE VERSIONING
        executed when a GET request comes in with a HEADER KEY is set as Accept with a value of application/vnd.company.app-v1+json */
    @GetMapping(value="/person", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getPersonV1Produces(){
        return new PersonV1("Charlie Brown");
    }

    /* PRODUCES VERSIONING -- ALSO CALLED ACCEPT HEADER VERSIONING OR MIME TYPE VERSIONING
        executed when a GET request comes in with a HEADER KEY is set as Accept with a value of application/vnd.company.app-v2+json */
    @GetMapping(value="/person", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getPersonV2Produces(){
        return new PersonV2(new Name("Charlie", "Brown"));
    }

}

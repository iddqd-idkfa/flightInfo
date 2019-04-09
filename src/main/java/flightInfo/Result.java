package flightInfo;

import java.util.List;

/**
 * REST endpoint Result class, wraps list of Flight objects
 */
public class Result {
    private List<Flight> res;

    private Integer numpages;

    /**
     * @param res List<Flight>
     * @param numpages Integer
     */
    Result(List<Flight> res, Integer numpages) {
        this.res = res;
        this.numpages = numpages;
    }

    /* getters and setters - start */
    public List<Flight> getRes() {
        return res;
    }

    public void setRes(List<Flight> res) {
        this.res = res;
    }

    public Integer getNumpages() {
        return numpages;
    }

    public void setNumpages(Integer numpages) {
        this.numpages = numpages;
    }


    /* getters and setters - end */

}

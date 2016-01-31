package com.vairon.baymax;

/**
 * Created by vaironl on 1/31/16.
 */
public class Bill {

    private String title, description;
    private boolean passed = true;

    public Bill(String _title, String _description, boolean _passed) {
        title = _title;
        description = _description;
        passed = _passed;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getPassed() {
        return passed;
    }
}

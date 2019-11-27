package cardproperties;

import renderers.Displayable;

public enum Edition implements Displayable {
    
    FIRST("1st Edition", "1st Edition"),
    UNLIMITED("Unlimited Edition", ""),
    LIMITED("Limited Edition", "LIMITED EDITION"),
    DUEL_TERMINAL("DUEL TERMINAL", "DUEL TERMINAL");
    
    private String edition;
    private String display;

    Edition(String edition, String display) {
       this.edition = edition; 
       this.display = display;
    }

    public String getEdition(){
        return edition;
    }
    
    @Override
    public String toString() {
        return edition;
    }

    @Override
    public String getDisplayString() {
        return display;
    }
}

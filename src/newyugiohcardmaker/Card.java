package newyugiohcardmaker;

public class Card {
    
    private int level;
    private int pendulumScale;
    private String frame;
    private String name;
    private String attributeType;
    private String imagePath;
    private String cardID;
    private String description;
    private String pendulumDescription;
    private String serialNumber;
    private String rarity;
    private String type;
    private String monsterType;
    private String spellTrapProperty;
    private String ability;
    private String atk;
    private String def;
    private String circulation;
    private String creator;
    private String year;
    private String edition;
    private boolean hasEffect;
    
    public Card() {}

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getPendulumScale(){
        return pendulumScale;
    }

    public void setPendulumScale(int pendulumScale){
        this.pendulumScale = pendulumScale;
    }

    public String getFrame(){
        return frame;
    }

    public void setFrame(String frame){
        this.frame = frame;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAttributeType(){
        return attributeType;
    }

    public void setAttributeType(String attributeType){
        this.attributeType = attributeType;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getCardID(){
        return cardID;
    }

    public void setCardID(String cardID){
        this.cardID = cardID;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPendulumDescription(){
        return pendulumDescription;
    }

    public void setPendulumDescription(String pendulumDescription){
        this.pendulumDescription = pendulumDescription;
    }

    public String getSerialNumber(){
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public String getRarity(){
        return rarity;
    }

    public void setRarity(String rarity){
        this.rarity = rarity;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getMonsterType(){
        return monsterType;
    }

    public void setMonsterType(String monsterType){
        this.monsterType = monsterType;
    }

    public String getSpellTrapProperty(){
        return spellTrapProperty;
    }

    public void setSpellTrapProperty(String spellTrapProperty){
        this.spellTrapProperty = spellTrapProperty;
    }

    public String getAbility(){
        return ability;
    }

    public void setAbility(String ability){
        this.ability = ability;
    }

    public String getAtk(){
        return atk;
    }

    public void setAtk(String atk){
        this.atk = atk;
    }

    public String getDef(){
        return def;
    }

    public void setDef(String def){
        this.def = def;
    }

    public String getCirculation(){
        return circulation;
    }

    public void setCirculation(String circulation){
        this.circulation = circulation;
    }

    public boolean hasEffect(){
        return hasEffect;
    }

    public void setHasEffect(boolean hasEffect){
        this.hasEffect = hasEffect;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

}

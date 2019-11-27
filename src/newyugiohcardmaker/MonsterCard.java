
package newyugiohcardmaker;


public class MonsterCard extends Card{
    
    int level;
    String monsterType;
    String ability;
    String atk;
    String def;
    boolean hasEffect;

    public MonsterCard(){}

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public String getMonsterType(){
        return monsterType;
    }

    public void setMonsterType(String monsterType){
        this.monsterType = monsterType;
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

    public boolean hasEffect(){
        return hasEffect;
    }

    public void setHasEffect(boolean hasEffect){
        this.hasEffect = hasEffect;
    }

}

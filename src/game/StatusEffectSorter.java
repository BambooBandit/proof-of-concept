package game;

import java.util.Comparator;

public class StatusEffectSorter implements Comparator<StatusEffect> {
    @Override
    public int compare(StatusEffect effect1, StatusEffect effect2) {
        return(effect1.ordinal() - effect2.ordinal());
    }
}

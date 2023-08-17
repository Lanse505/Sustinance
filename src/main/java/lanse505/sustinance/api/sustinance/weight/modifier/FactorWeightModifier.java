package lanse505.sustinance.api.sustinance.weight.modifier;

public class FactorWeightModifier implements WeightModifier {
    private final float modifier;

    public FactorWeightModifier(float modifier) {
        this.modifier = modifier;
    }

    @Override
    public int getModifiedWeight(int maxWeight) {
        return Math.round(maxWeight * modifier);
    }
}

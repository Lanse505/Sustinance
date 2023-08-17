package lanse505.sustinance.api.sustinance.weight.modifier;

public class FlatWeightModifier implements WeightModifier {
    private final int modifier;

    public FlatWeightModifier(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public int getModifiedWeight(int maxWeight) {
        return maxWeight + modifier;
    }
}

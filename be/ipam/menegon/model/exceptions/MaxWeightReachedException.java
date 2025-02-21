package be.ipam.menegon.model.exceptions;

import be.ipam.menegon.model.truck.Chargeable;

public class MaxWeightReachedException extends Exception {
    private final Chargeable item;

    // Constructeur avec message et objet Chargeable
    public MaxWeightReachedException(String message, Chargeable item) {
        super(message);
        this.item = item;
    }

    public Chargeable getItem() {
        return item;
    }
}

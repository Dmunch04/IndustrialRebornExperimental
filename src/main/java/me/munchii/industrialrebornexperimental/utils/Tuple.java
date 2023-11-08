package me.munchii.industrialrebornexperimental.utils;

public record Tuple<A, B>(A a, B b) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple<?, ?> tuple) {
            return a == tuple.a() && b == tuple.b();
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}

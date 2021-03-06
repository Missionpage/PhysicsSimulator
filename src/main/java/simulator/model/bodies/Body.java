package simulator.model.bodies;

public class Body extends simulator.model.bodies.FluentBuilder.Body {

    private Body(Builder builder) {
        super(builder);
    }

    public static class Builder extends simulator.model.bodies.FluentBuilder.Body.Builder<Builder> {
        public Builder() {
            super();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Body build() {
            return new Body(this);
        }
    }
}
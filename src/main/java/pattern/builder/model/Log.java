package pattern.builder.model;

public class Log {
    private int productId;
    private enum productType {
        LEGACY, DEAL, PRODUCT, TOUR, TICKET
    }
    private int clickCount;
    private int salesCount;

    public Log(int productId, int clickCount, int salesCount) {
        this.productId = productId;
        this.clickCount = clickCount;
        this.salesCount = salesCount;
    }

    @Override
    public String toString() {
        return "Log{" +
                "productId=" + productId +
                ", clickCount=" + clickCount +
                ", salesCount=" + salesCount +
                '}';
    }

    public static class Builder {
        private int productId;
        private enum productType {
            LEGACY, DEAL, PRODUCT, TOUR, TICKET
        }
        private int clickCount;
        private int salesCount;

        public Builder() {
        }

        public Builder setProductId(int productId) {
            this.productId = productId;
            return this;
        }

        public Builder setClickCount(int clickCount) {
            this.clickCount = clickCount;
            return this;
        }

        public Builder setSalesCount(int salesCount) {
            this.salesCount = salesCount;
            return this;
        }

        public Log build() {
            return new Log(productId, clickCount, salesCount);
        }
    }
}
